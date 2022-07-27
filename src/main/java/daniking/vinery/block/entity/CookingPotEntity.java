package daniking.vinery.block.entity;

import daniking.vinery.Vinery;
import daniking.vinery.block.CookingPotBlock;
import daniking.vinery.client.gui.handler.CookingPotGuiHandler;
import daniking.vinery.recipe.CookingPotRecipe;
import daniking.vinery.recipe.StoveCookingRecipe;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VineryBlockEntityTypes;
import daniking.vinery.registry.VineryRecipeTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
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
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
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

public class CookingPotEntity extends BlockEntity implements BlockEntityTicker<CookingPotEntity>, Inventory, ExtendedScreenHandlerFactory {

    private DefaultedList<ItemStack> inventory;
    private static final int MAX_CAPACITY = 8;
    private static final int MAX_COOKING_TIME = 60; // Time in ticks (30s)
    private int cookingTime = MAX_COOKING_TIME;
    private static final int BOTTLE_INPUT_SLOT = 6;
    private static final int OUTPUT_SLOT = 7;
    private static final int INGREDIENTS_AREA = 2 * 3;

    private boolean isBeingBurned = false;
    private int totalCookingTime;


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
        Inventories.readNbt(nbt, this.inventory);
        nbt.putInt("CookingTime", this.cookingTime);
        nbt.putBoolean("isBeingBurned", this.isBeingBurned);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.cookingTime = nbt.getInt("CookingTime");
        this.isBeingBurned = nbt.getBoolean("isBeingBurned");
        Inventories.writeNbt(nbt, this.inventory);
    }

    public boolean isBeingBurned() {
        if (getWorld() == null) throw new NullPointerException("Null world invoked");
        final BlockState belowState = this.getWorld().getBlockState(getPos().down());
        final var optionalList = Registry.BLOCK.getEntryList(Vinery.ALLOWS_COOKING_ON_POT);
        final var entryList = optionalList.orElse(null);
        if (entryList == null) {
            return false;
        } else if (!entryList.contains(belowState.getBlock().getRegistryEntry())) {
            return false;
        } else return belowState.get(Properties.LIT);

    }

    private boolean canCraft(CookingPotRecipe recipe) {
        if (recipe == null || recipe.getOutput().isEmpty()) {
            return false;
        }  else if (!this.getStack(BOTTLE_INPUT_SLOT).isOf(recipe.getContainer().getItem())) {
            return false;
        } else if (this.getStack(OUTPUT_SLOT).isEmpty()) {
            return true;
        } else {
            final ItemStack recipeOutput = recipe.getOutput();
            final ItemStack outputSlotStack = this.getStack(OUTPUT_SLOT);
            final int outputSlotCount = outputSlotStack.getCount();
            if (!outputSlotStack.isItemEqualIgnoreDamage(recipeOutput)) {
                return false;
            } else if (outputSlotCount < this.getMaxCountPerStack() && outputSlotCount < outputSlotStack.getMaxCount()) {
                return true;
            } else {
                return outputSlotCount < recipeOutput.getMaxCount();
            }
        }
    }

    private void craft(CookingPotRecipe recipe) {
        if (!canCraft(recipe)) {
            return;
        }
        final ItemStack recipeOutput = recipe.getOutput();
        final ItemStack outputSlotStack = this.getStack(OUTPUT_SLOT);
        if (outputSlotStack.isEmpty()) {
            setStack(OUTPUT_SLOT, recipeOutput.copy());
        } else if (outputSlotStack.isOf(recipeOutput.getItem())) {
            outputSlotStack.increment(recipeOutput.getCount());
        }
        final DefaultedList<Ingredient> ingredients = recipe.getIngredients();
        // each slot can only be used once because in canMake we only checked if decrement by 1 still retains the recipe
        // otherwise recipes can break when an ingredient is used multiple times
        boolean[] slotUsed = new boolean[INGREDIENTS_AREA];
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            // Looks for the best slot to take it from
            final ItemStack bestSlot = this.getStack(i);
            if (ingredient.test(bestSlot) && !slotUsed[i]) {
                slotUsed[i] = true;
                bestSlot.decrement(1);
            } else {
                // check all slots in search of the ingredient
                for (int j = 0; j < INGREDIENTS_AREA; j++) {
                    ItemStack stack = this.getStack(j);
                    if (ingredient.test(stack) && !slotUsed[j]) {
                        slotUsed[j] = true;
                        stack.decrement(1);
                    }
                }
            }
        }
        this.getStack(BOTTLE_INPUT_SLOT).decrement(1);
    }


    @Override
    public void tick(World world, BlockPos pos, BlockState state, CookingPotEntity blockEntity) {
        if (world.isClient()) {
            return;
        }
        this.isBeingBurned = isBeingBurned();
        if (!this.isBeingBurned) return;
        boolean dirty = false;
        final var recipe = world.getRecipeManager()
                .getFirstMatch(VineryRecipeTypes.COOKING_POT_RECIPE_TYPE, this, world)
                .orElse(null);
        boolean canCraft = canCraft(recipe);
        if (canCraft) {
            ++this.cookingTime;
            if (this.cookingTime == this.totalCookingTime) {
                this.cookingTime = 0;
                craft(recipe);
                dirty = true;
            }
        } else if (!canCraft(recipe)){
            this.cookingTime = 0;
        }
        if (canCraft) {
            world.setBlockState(pos, this.getCachedState().getBlock().getDefaultState().with(CookingPotBlock.HAS_CHERRIES_INSIDE, true), Block.NOTIFY_ALL);
            dirty = true;
        } else if (state.get(CookingPotBlock.HAS_CHERRIES_INSIDE)) {
            world.setBlockState(pos, this.getCachedState().getBlock().getDefaultState().with(CookingPotBlock.HAS_CHERRIES_INSIDE, false), Block.NOTIFY_ALL);
            dirty = true;
        }
        if (dirty) markDirty();
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
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (totalCookingTime == 0) {
            this.totalCookingTime = MAX_COOKING_TIME;
        }
        this.markDirty();
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
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBoolean(this.isBeingBurned);
    }
}


