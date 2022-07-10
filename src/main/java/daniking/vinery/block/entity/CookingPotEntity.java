package daniking.vinery.block.entity;

import daniking.vinery.Vinery;
import daniking.vinery.block.CookingPotBlock;
import daniking.vinery.registry.VineryBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Objects;

public class CookingPotEntity extends BlockEntity implements BlockEntityTicker<CookingPotEntity>  {


    private DefaultedList<ItemStack> cherryInventory;
    private static final int MAX_CAPACITY = 4;
    private static final int MAX_COOKING_TIME = 60; // Time in ticks (30s)
    private int cookingTime = MAX_COOKING_TIME;
    public CookingPotEntity(BlockPos pos, BlockState state) {
        super(VineryBlockEntityTypes.COOKING_POT_BLOCK_ENTITY, pos, state);
        this.cherryInventory = DefaultedList.ofSize(MAX_CAPACITY, ItemStack.EMPTY);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.cherryInventory = DefaultedList.ofSize(MAX_CAPACITY, ItemStack.EMPTY);
        nbt.putInt("CookingTime", this.cookingTime);
        Inventories.readNbt(nbt, this.cherryInventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.cookingTime = nbt.getInt("CookingTime");
        Inventories.writeNbt(nbt, this.cherryInventory);
    }

    public boolean insertCherry(ItemStack cherryJam) {
        if (cherryJam != null && !cherryJam.isEmpty() && getNotEmptyStacks() != MAX_CAPACITY) {
            for (int i = 0; i < this.cherryInventory.size(); i++) {
                final ItemStack stackInInv = this.cherryInventory.get(i);
                if (stackInInv.isEmpty()) {
                    this.cherryInventory.set(i, new ItemStack(cherryJam.getItem(), 1));
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
        for (ItemStack entry : this.cherryInventory) {
            if (!entry.isEmpty()) c++;
        }
        return c;
    }

    private boolean isCooking() {
        return this.cookingTime > 0;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, CookingPotEntity blockEntity) {
        final BlockState belowState = world.getBlockState(pos.down());
        if (!belowState.contains(Properties.LIT)) {
            return;
        }
        final var optionalList = Registry.BLOCK.getEntryList(Vinery.ALLOWS_COOKING_ON_POT);
        final var entryList = optionalList.orElse(null);

        if (entryList != null) {
            // TODO: deprecated
            if (entryList.contains(belowState.getBlock().getRegistryEntry())) {
                final boolean isCooking = this.isCooking();
                boolean dirty = false;
                if (isCooking) {
                    --this.cookingTime;
                }
                // Check amount of cherry stacks
                if (getNotEmptyStacks() == MAX_CAPACITY) {
                    // Validate that the below state is on lit
                    if (belowState.get(Properties.LIT)) {
                        // Start burning
                        if (!state.get(CookingPotBlock.COOKING)) {
                            dirty = true;
                            this.cookingTime = MAX_COOKING_TIME;
                            world.setBlockState(pos, state.with(CookingPotBlock.COOKING, true), Block.NOTIFY_ALL);
                            return;
                        }
                        // Check cooking time
                        if (this.cookingTime == 0) {
                            dirty = true;
                            ItemScatterer.spawn(world, pos, this.cherryInventory);
                            clearCherryInventory();
                            world.updateListeners(pos, state, state, Block.NOTIFY_ALL);

                        }
                    }
                } else {
                    if (this.isCooking() || state.get(CookingPotBlock.COOKING)) {
                        world.setBlockState(pos, this.getCachedState().getBlock().getDefaultState().with(CookingPotBlock.HAS_CHERRIES_INSIDE, false).with(CookingPotBlock.COOKING, false), Block.NOTIFY_ALL);
                        this.cookingTime = 0;
                    }
                }
                if (dirty) {
                    markDirty();
                }
            }
        }

    }

    private void clearCherryInventory() {
        Collections.fill(this.cherryInventory, ItemStack.EMPTY);
    }
}


