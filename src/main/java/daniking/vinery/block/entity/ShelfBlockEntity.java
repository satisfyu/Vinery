package daniking.vinery.block.entity;

import daniking.vinery.Vinery;
import daniking.vinery.registry.VineryBlockEntityTypes;
import daniking.vinery.util.networking.VineryMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ShelfBlockEntity extends BlockEntity {

    public static int SHELF_SIZE = 9;

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(SHELF_SIZE, ItemStack.EMPTY);

    public ShelfBlockEntity(BlockPos pos, BlockState state) {
        super(VineryBlockEntityTypes.SHELF_ENTITY, pos, state);
    }

    public void addItemStack(ItemStack stack) {
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemStack = this.inventory.get(i);
            if (itemStack.isEmpty()) {
                this.inventory.set(i, stack);
                markDirty();
                return;
            }
        }
    }

    public int getNonEmptySlotCount() {
        int count = 0;
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public ItemStack getFirstNonEmptyStack() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty()) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
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

    public void removeFirstNonEmptyStack() {
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemStack = this.inventory.get(i);
            if (!itemStack.isEmpty()) {
                this.inventory.set(i, ItemStack.EMPTY);
                markDirty();
                return;
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
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
