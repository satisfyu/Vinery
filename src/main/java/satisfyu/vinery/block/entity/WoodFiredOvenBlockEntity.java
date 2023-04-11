package satisfyu.vinery.block.entity;

import net.minecraft.recipe.Recipe;
import satisfyu.vinery.block.WoodFiredOvenBlock;
import satisfyu.vinery.client.gui.handler.StoveGuiHandler;
import satisfyu.vinery.item.food.EffectFood;
import satisfyu.vinery.item.food.EffectFoodHelper;
import satisfyu.vinery.recipe.WoodFiredOvenRecipe;
import satisfyu.vinery.registry.VineryBlockEntityTypes;
import satisfyu.vinery.registry.VineryRecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.item.ItemStack.canCombine;

public class WoodFiredOvenBlockEntity extends BlockEntity implements BlockEntityTicker<WoodFiredOvenBlockEntity>, Inventory, NamedScreenHandlerFactory {

    private DefaultedList<ItemStack> inventory;

    protected int burnTime;
    protected int burnTimeTotal;
    protected int cookTime;
    protected int cookTimeTotal;

    protected float experience;

    protected static final int FUEL_SLOT = StoveGuiHandler.FUEL_SLOT;
    protected static final int[] INGREDIENT_SLOTS = {0, 1, 2};
    protected static final int OUTPUT_SLOT = StoveGuiHandler.OUTPUT_SLOT;

    public static final int TOTAL_COOKING_TIME = 240;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> WoodFiredOvenBlockEntity.this.burnTime;
                case 1 -> WoodFiredOvenBlockEntity.this.burnTimeTotal;
                case 2 -> WoodFiredOvenBlockEntity.this.cookTime;
                case 3 -> WoodFiredOvenBlockEntity.this.cookTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> WoodFiredOvenBlockEntity.this.burnTime = value;
                case 1 -> WoodFiredOvenBlockEntity.this.burnTimeTotal = value;
                case 2 -> WoodFiredOvenBlockEntity.this.cookTime = value;
                case 3 -> WoodFiredOvenBlockEntity.this.cookTimeTotal = value;
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };
    public WoodFiredOvenBlockEntity(BlockPos pos, BlockState state) {
        super(VineryBlockEntityTypes.WOOD_FIRED_OVEN_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    }

    public void dropExperience(ServerWorld world, Vec3d pos) {
        ExperienceOrbEntity.spawn(world, pos, (int) experience);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.burnTimeTotal = this.getTotalBurnTime(this.getStack(FUEL_SLOT));
        this.experience = nbt.getFloat("Experience");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", (short)this.burnTime);
        nbt.putShort("CookTime", (short)this.cookTime);
        nbt.putShort("CookTimeTotal", (short)this.cookTimeTotal);
        nbt.putFloat("Experience", this.experience);
        Inventories.writeNbt(nbt, this.inventory);
    }

    protected boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WoodFiredOvenBlockEntity blockEntity) {
        if (world.isClient) {
            return;
        }
        boolean initialBurningState = blockEntity.isBurning();
        boolean dirty = false;
        if (initialBurningState) {
            --this.burnTime;
        }

        final WoodFiredOvenRecipe recipe = world.getRecipeManager().getFirstMatch(VineryRecipeTypes.WOOD_FIRED_OVEN_RECIPE_TYPE, this, world).orElse(null);
        if (!initialBurningState && canCraft(recipe)) {
            this.burnTime = this.burnTimeTotal = this.getTotalBurnTime(this.getStack(FUEL_SLOT));
            if (burnTime > 0) {
                dirty = true;
                final ItemStack fuelStack = this.getStack(FUEL_SLOT);
                if (fuelStack.getItem().hasRecipeRemainder()) {
                    setStack(FUEL_SLOT, new ItemStack(fuelStack.getItem().getRecipeRemainder()));
                } else if (fuelStack.getCount() > 1) {
                    removeStack(FUEL_SLOT, 1);
                } else if (fuelStack.getCount() == 1) {
                    setStack(FUEL_SLOT, ItemStack.EMPTY);
                }
            }
        }
        if (isBurning() && canCraft(recipe)) {
            ++this.cookTime;
            if (this.cookTime == cookTimeTotal) {
                this.cookTime = 0;
                craft(recipe);
                dirty = true;
            }
        } else if (!canCraft(recipe)) {
            this.cookTime = 0;
        }
        if (initialBurningState != isBurning()) {
            if (state.get(WoodFiredOvenBlock.LIT) != (burnTime > 0)) {
                world.setBlockState(pos, state.with(WoodFiredOvenBlock.LIT, burnTime > 0), Block.NOTIFY_ALL);
                dirty = true;
            }
        }
        if (dirty) {
            markDirty();
        }

    }

    protected boolean canCraft(WoodFiredOvenRecipe recipe) {
        if (recipe == null || recipe.getOutput().isEmpty()) {
            return false;
        } else if (this.getStack(FUEL_SLOT).isEmpty()) {
            return false;
        } else if (this.getStack(OUTPUT_SLOT).isEmpty()) {
            return true;
        } else {
            if (this.getStack(OUTPUT_SLOT).isEmpty()) {
                return true;
            }
            final ItemStack recipeOutput = this.generateOutputItem(recipe);
            final ItemStack outputSlotStack = this.getStack(OUTPUT_SLOT);
            final int outputSlotCount = outputSlotStack.getCount();
            if (this.getStack(OUTPUT_SLOT).isEmpty()) {
                return true;
            }
            else if (!canCombine(outputSlotStack, recipeOutput)) {
                return false;
            } else if (outputSlotCount < this.getMaxCountPerStack() && outputSlotCount < outputSlotStack.getMaxCount()) {
                return true;
            } else {
                return outputSlotCount < recipeOutput.getMaxCount();
            }
        }
    }

    protected void craft(WoodFiredOvenRecipe recipe) {
        if (recipe == null || !canCraft(recipe)) {
            return;
        }
        final ItemStack recipeOutput = generateOutputItem(recipe);
        final ItemStack outputSlotStack = this.getStack(OUTPUT_SLOT);
        if (outputSlotStack.isEmpty()) {
            setStack(OUTPUT_SLOT, recipeOutput);
        } else if (outputSlotStack.isOf(recipeOutput.getItem())) {
            outputSlotStack.increment(recipeOutput.getCount());
        }


        final DefaultedList<Ingredient> ingredients = recipe.getIngredients();
        boolean[] slotUsed = new boolean[3];
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            // Looks for the best slot to take it from
            ItemStack bestSlot = getStack(i);
            if (ingredient.test(bestSlot) && !slotUsed[i]) {
                slotUsed[i] = true;
                ItemStack remainderStack = getRemainderItem(bestSlot);
                bestSlot.decrement(1);
                if (!remainderStack.isEmpty()) {
                    setStack(i, remainderStack);
                }
            } else {
                // check all slots in search of the ingredient
                for (int j = 0; j < 3; j++) {
                    ItemStack stack = getStack(j);
                    if (ingredient.test(stack) && !slotUsed[j]) {
                        slotUsed[j] = true;
                        ItemStack remainderStack = getRemainderItem(stack);
                        stack.decrement(1);
                        if (!remainderStack.isEmpty()) {
                           setStack(j, remainderStack);
                        }
                    }
                }
            }
        }
        this.experience += recipe.getExperience();
    }

    private ItemStack generateOutputItem(Recipe<?> recipe) {
        ItemStack outputStack = recipe.getOutput();

        if (!(outputStack.getItem() instanceof EffectFood)) {
            return outputStack;
        }

        for (Ingredient ingredient : recipe.getIngredients()) {
            for (int j = 0; j < 3; j++) {
                ItemStack stack = this.getStack(j);
                if (ingredient.test(stack)) {
                    EffectFoodHelper.getEffects(stack).forEach(effect -> EffectFoodHelper.addEffect(outputStack, effect));
                    break;
                }
            }
        }
        return outputStack;
    }

    protected int getTotalBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            final Item item = fuel.getItem();
            return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
        }
    }
    private ItemStack getRemainderItem(ItemStack stack) {
        if (stack.getItem().hasRecipeRemainder()) {
            return new ItemStack(stack.getItem().getRecipeRemainder());
        }
        return ItemStack.EMPTY;
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
        boolean dirty = !stack.isEmpty() && stack.isItemEqual(stackInSlot) && ItemStack.areNbtEqual(stack, stackInSlot);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (slot == INGREDIENT_SLOTS[0] || slot == INGREDIENT_SLOTS[1] || slot == INGREDIENT_SLOTS[2] && !dirty) {
            this.cookTimeTotal = TOTAL_COOKING_TIME;
            this.cookTime = 0;
            this.markDirty();
        }
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
        return Text.translatable(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new StoveGuiHandler(syncId, inv, this, this.propertyDelegate);
    }
}
