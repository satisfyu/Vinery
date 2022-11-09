package daniking.vinery.block.entity;

import daniking.vinery.block.WineBoxBlock;
import daniking.vinery.block.WineRackBlock;
import daniking.vinery.registry.VineryBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GeckoStorageBlockEntity extends BlockEntity implements Inventory, IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private DefaultedList<ItemStack> inventory;

    public GeckoStorageBlockEntity(BlockPos pos, BlockState state) {
        super(VineryBlockEntityTypes.WINE_RACK_GECKO_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(18, ItemStack.EMPTY);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.getInvStackList().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.getInvStackList().get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.getInvStackList(), slot, amount);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }

        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.getInvStackList(), slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.getInvStackList().set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }

        this.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
        }
    }

    @Override
    public void clear() {
        this.getInvStackList().clear();
    }

    public DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    public ItemStack getFirstNonEmptyStack() {
        for (ItemStack itemStack : this.getInvStackList()) {
            if (!itemStack.isEmpty()) {
                return itemStack;
            }
        }

        return ItemStack.EMPTY;
    }

    public void removeFirstNonEmptyStack() {
        for (int i = 0; i < this.getInvStackList().size(); ++i) {
            ItemStack itemStack = this.getInvStackList().get(i);
            if (!itemStack.isEmpty()) {
                this.getInvStackList().set(i, ItemStack.EMPTY);
                return;
            }
        }
    }


    @Override
    public void registerControllers(AnimationData data) {
        // NO-ANIMATION
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public String getModelName() {
        if (this.getCachedState().getBlock() instanceof WineBoxBlock) {
            return this.getCachedState().get(WineBoxBlock.OPEN) ? "wine_box_filled_open" : "wine_box_filled_closed";
        }
        return "wine_rack_" + ((WineRackBlock) this.getCachedState().getBlock()).getModelPostFix();
    }

    public int getNonEmptySlotCount() {
        int count = 0;
        for (ItemStack itemStack : this.getInvStackList()) {
            if (!itemStack.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public void addItemStack(ItemStack stack) {
        for (int i = 0; i < this.getInvStackList().size(); ++i) {
            ItemStack itemStack = this.getInvStackList().get(i);
            if (itemStack.isEmpty()) {
                this.getInvStackList().set(i, stack);
                return;
            }
        }
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}