package daniking.vinery.block.entity;

import daniking.vinery.Vinery;
import daniking.vinery.block.CookingPotBlock;
import daniking.vinery.client.gui.handler.CookingPotGuiHandler;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VineryBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CookingPotEntity extends BlockEntity implements BlockEntityTicker<CookingPotEntity>, Inventory, NamedScreenHandlerFactory {


    private DefaultedList<ItemStack> inventory;
    private static final int MAX_CAPACITY = 8;
    private static final int MAX_COOKING_TIME = 60; // Time in ticks (30s)
    private int cookingTime = MAX_COOKING_TIME;

    private static int BOTTLE_INPUT_SLOT = 6;
    private static int OUTPUT_SLOT = 7;
    private int totalCookingTime;
    private boolean isDone = false;

    private final PropertyDelegate delegate;
    public CookingPotEntity(BlockPos pos, BlockState state) {
        super(VineryBlockEntityTypes.COOKING_POT_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(MAX_CAPACITY, ItemStack.EMPTY);
        this.delegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CookingPotEntity.this.cookingTime;
                    case 1 -> CookingPotEntity.this.totalCookingTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CookingPotEntity.this.cookingTime = value;
                    case 1 -> CookingPotEntity.this.totalCookingTime = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(MAX_CAPACITY, ItemStack.EMPTY);
        nbt.putInt("CookingTime", this.cookingTime);
        nbt.putBoolean("isDone", this.isDone);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.cookingTime = nbt.getInt("CookingTime");
        this.isDone = nbt.getBoolean("isDone");
        Inventories.writeNbt(nbt, this.inventory);
    }

    public boolean insertCherry(ItemStack cherryJam) {
        if (cherryJam != null && !cherryJam.isEmpty() && getNotEmptyStacks() != MAX_CAPACITY) {
            for (int i = 0; i < this.inventory.size(); i++) {
                final ItemStack stackInInv = this.inventory.get(i);
                if (stackInInv.isEmpty()) {
                    this.inventory.set(i, new ItemStack(cherryJam.getItem(), 1));
                    cherryJam.decrement(1);
                    updateListeners();
                    return true;
                }
            }
        }
        return false;
    }
    private void updateListeners() {
        this.markDirty();
        Objects.requireNonNull(this.getWorld()).updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }

    private int getNotEmptyStacks() {
        int c = 0;
        for (ItemStack entry : this.inventory) {
            if (!entry.isEmpty()) c++;
        }
        return c;
    }

    private boolean isCooking() {
        return this.cookingTime > 0;
    }

    private boolean isBurning() {
        return false;
    }

    private boolean canCraft() {
        if (!hasAllInputs()) {
            return false;
        } else if (this.getStack(BOTTLE_INPUT_SLOT).isEmpty()) {
            return false;
        } else return this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean hasAllInputs() {
        int c = (int) inventory.stream().filter(stack -> !stack.isEmpty()).count();
        return c == MAX_CAPACITY;
    }
    private void craft() {
        if (!canCraft()) {
            return;
        }
    }


    @Override
    public void tick(World world, BlockPos pos, BlockState state, CookingPotEntity blockEntity) {
        final BlockState belowState = world.getBlockState(pos.down());
        if (!belowState.contains(Properties.LIT)) {
            return;
        }
        final var optionalList = Registry.BLOCK.getEntryList(Vinery.ALLOWS_COOKING_ON_POT);
        final var entryList = optionalList.orElse(null);
        if (entryList == null) {
            return;
        }
        if (!entryList.contains(belowState.getBlock().getRegistryEntry())) {
            return;
        }
        // There is a valid block on lit state
        boolean dirty = false;

//        if (this.isDone && this.getNotEmptyStacks() == 0) {
//            reset(world, state);
//        }
//        if (state.get(CookingPotBlock.COOKING) && this.getNotEmptyStacks() == 0) {
//            reset(world, state);
//        }
//        if (entryList != null) {
//            // TODO: deprecated
//            if (entryList.contains(belowState.getBlock().getRegistryEntry())) {
//                final boolean isCooking = this.isCooking();
//                boolean dirty = false;
//                if (isCooking) {
//                    --this.cookingTime;
//                }
//                // Check amount of cherry stacks
//                if (getNotEmptyStacks() == MAX_CAPACITY) {
//                    // Validate that the below state is on lit
//                    if (belowState.get(Properties.LIT)) {
//                        // Start burning
//                        if (!state.get(CookingPotBlock.COOKING)) {
//                            dirty = true;
//                            this.cookingTime = MAX_COOKING_TIME;
//                            world.setBlockState(pos, state.with(CookingPotBlock.COOKING, true), Block.NOTIFY_ALL);
//                            return;
//                        }
//                        // Check cooking time
//                        if (this.cookingTime == 0) {
//                            dirty = true;
//                            this.isDone = true;
//                            world.setBlockState(pos, state.with(CookingPotBlock.COOKING, false), Block.NOTIFY_ALL);
////                            ItemScatterer.spawn(world, pos, this.cherryInventory);
////                            clearCherryInventory();
//                            world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
//
//                        }
//                    } else {
//                        if (this.cookingTime > 0) {
//                            this.cookingTime = 0;
//                            world.setBlockState(pos, state.with(CookingPotBlock.COOKING, false), Block.NOTIFY_ALL);
//                        }
//                    }
//                } else {
//                    if (this.isCooking() || state.get(CookingPotBlock.COOKING)) {
//                        world.setBlockState(pos, this.getCachedState().getBlock().getDefaultState().with(CookingPotBlock.HAS_CHERRIES_INSIDE, false).with(CookingPotBlock.COOKING, false), Block.NOTIFY_ALL);
//                        this.cookingTime = 0;
//                    }
//                }
//                if (dirty) {
//                    markDirty();
//                }
//            }
//        }

    }
    private void reset(World world, BlockState state) {
        this.cookingTime = 0;
        world.setBlockState(pos, state.with(CookingPotBlock.COOKING, false), Block.NOTIFY_ALL);
        this.isDone = false;
        clearCherryInventory();
        markDirty();
    }
    public void onFinish(PlayerEntity player, ItemStack emptyJar) {
        if (this.isDone()) {
            final ItemStack output = new ItemStack(ObjectRegistry.CHERRY_JAM);
            if (!player.getInventory().insertStack(output)) {
                player.dropItem(output, false, false);
            }
            if (!player.isCreative()) emptyJar.decrement(1);
            final int i = this.getNotEmptyStacks() - 1;
            if (i >= 0) {
                this.inventory.set(i, ItemStack.EMPTY);
            }
            updateListeners();
        } else if (this.cookingTime == 0) {
            reset(world, getCachedState());
        }
    }
    public boolean isDone() {
        return this.cookingTime == 0 && isDone;
    }
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    private void clearCherryInventory() {
        Collections.fill(this.inventory, ItemStack.EMPTY);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CookingPotGuiHandler(syncId, inv, this, this.delegate);
    }

    @Override
    public int size() {
        return inventory.size();
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
        inventory.clear();
    }

}


