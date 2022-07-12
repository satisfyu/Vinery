package daniking.vinery.block.entity;

import daniking.vinery.Vinery;
import daniking.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import daniking.vinery.registry.VineryBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FermentationBarrelBlockEntity extends BlockEntity implements Inventory, BlockEntityTicker<FermentationBarrelBlockEntity>, NamedScreenHandlerFactory, PropertyDelegate {

    private final DefaultedList<ItemStack> inventory;
    public static final int CAPACITY = 6;
    public static final int COOKING_TIME_IN_TICKS = 1800; // 90s or 3 minutes

    public FermentationBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(VineryBlockEntityTypes.FERMENTATION_BARREL_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(CAPACITY, ItemStack.EMPTY);
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, FermentationBarrelBlockEntity blockEntity) {

    }

    @Override
    public int get(int index) {
        return 0;
    }

    @Override
    public void set(int index, int value) {

    }

    @Override
    public int size() {
        return CAPACITY;
    }

    @Override
    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        final ItemStack stackInSlot = this.inventory.get(slot);
        boolean dirty = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(stackInSlot) && ItemStack.areNbtEqual(stack, stackInSlot);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
//        if (slot == INGREDIENT_SLOT && !dirty) {
//            this.cookTimeTotal = TOTAL_COOKING_TIME;
//            this.cookTime = 0;
//            this.markDirty();
//        }
    }
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
        }
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }


    @Override
    public Text getDisplayName() {
        return new TranslatableText(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new FermentationBarrelGuiHandler(syncId, inv, this, this);
    }
}
