package daniking.vinery.block.entity;

import daniking.vinery.registry.VineryBlockEntityTypes;
import daniking.vinery.util.networking.VineryMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ShelfBlockEntity extends BlockEntity {

    private int size;

    private DefaultedList<ItemStack> inventory;

    public ShelfBlockEntity(BlockPos pos, BlockState state) {
        super(VineryBlockEntityTypes.STORAGE_ENTITY, pos, state);
    }

    public ShelfBlockEntity(BlockPos pos, BlockState state, int size) {
        super(VineryBlockEntityTypes.STORAGE_ENTITY, pos, state);
        this.size = size;
        this.inventory = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
    }

    public ItemStack removeStack(int slot){
        ItemStack stack = inventory.set(slot, ItemStack.EMPTY);
        markDirty();
        return stack;
    }

    public void setStack(int slot, ItemStack stack){
        inventory.set(slot, stack);
        markDirty();
    }

    @Override
    public void markDirty() {
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for (ItemStack itemStack : inventory) {
                data.writeItemStack(itemStack);
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, VineryMessages.ITEM_SYNC, data);
            }
        }
        super.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.size = nbt.getInt("size");
        this.inventory = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }



    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("size", this.size);
        super.writeNbt(nbt);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }
}
