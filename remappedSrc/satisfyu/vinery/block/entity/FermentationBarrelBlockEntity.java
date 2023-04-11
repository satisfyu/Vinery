package satisfyu.vinery.block.entity;

import net.minecraft.item.Item;
import net.minecraft.recipe.Recipe;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import satisfyu.vinery.item.food.EffectFood;
import satisfyu.vinery.item.food.EffectFoodHelper;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VineryBlockEntityTypes;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.util.WineYears;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FermentationBarrelBlockEntity extends BlockEntity implements Inventory, BlockEntityTicker<FermentationBarrelBlockEntity>, NamedScreenHandlerFactory {

    private DefaultedList<ItemStack> inventory;
    public static final int CAPACITY = 6;
    public static final int COOKING_TIME_IN_TICKS = 1800; // 90s or 3 minutes
    private static final int BOTTLE_INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 5;
    private int fermentationTime = 0;
    private int totalFermentationTime;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {

        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> FermentationBarrelBlockEntity.this.fermentationTime;
                case 1 -> FermentationBarrelBlockEntity.this.totalFermentationTime;
                default -> 0;
            };
        }


        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> FermentationBarrelBlockEntity.this.fermentationTime = value;
                case 1 -> FermentationBarrelBlockEntity.this.totalFermentationTime = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public FermentationBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(VineryBlockEntityTypes.FERMENTATION_BARREL_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(CAPACITY, ItemStack.EMPTY);
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.fermentationTime = nbt.getShort("FermentationTime");
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putShort("FermentationTime", (short) this.fermentationTime);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, FermentationBarrelBlockEntity blockEntity) {
        if (world.isClient) return;
        boolean dirty = false;
        Recipe<?> recipe = world.getRecipeManager().getFirstMatch(VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_TYPE, this, world).orElse(null);
        if (canCraft(recipe)) {
            this.fermentationTime++;

            if (this.fermentationTime >= this.totalFermentationTime) {
                this.fermentationTime = 0;
                craft(recipe);
                dirty = true;
            }
        } else {
            this.fermentationTime = 0;
        }
        if (dirty) {
            markDirty();
        }

    }

    private boolean canCraft(Recipe<?> recipe) {
        if (recipe == null || recipe.getOutput().isEmpty()) {
            return false;
        } else if (areInputsEmpty()) {
            return false;
        } else if (this.getStack(BOTTLE_INPUT_SLOT).isEmpty()) {
            return false;
        } else {
            final Item item = this.getStack(BOTTLE_INPUT_SLOT).getItem();
            if (item != ObjectRegistry.WINE_BOTTLE.asItem()) {
                return false;
            }
            return this.getStack(OUTPUT_SLOT).isEmpty();
        }
    }

    private boolean areInputsEmpty() {
        int emptyStacks = 0;
        for (int i = 1; i < 5; i++) {
            if (this.getStack(i).isEmpty()) emptyStacks++;
        }
        return emptyStacks == 4;
    }
    private void craft(Recipe<?> recipe) {
        if (!canCraft(recipe)) {
            return;
        }
        final ItemStack recipeOutput = generateOutputItem(recipe);
        final ItemStack outputSlotStack = this.getStack(OUTPUT_SLOT);
        if (outputSlotStack.isEmpty()) {
            ItemStack output = recipeOutput.copy();
            WineYears.setWineYear(output, this.world);
            setStack(OUTPUT_SLOT, output);
        }
        // Decrement bottles
        final ItemStack bottle = this.getStack(BOTTLE_INPUT_SLOT);
        if (bottle.getCount() > 1) {
            removeStack(BOTTLE_INPUT_SLOT, 1);
        } else if (bottle.getCount() == 1) {
            setStack(BOTTLE_INPUT_SLOT, ItemStack.EMPTY);
        }

        // Decrement ingredient
        for (Ingredient entry : recipe.getIngredients()) {
            if (entry.test(this.getStack(1))) {
                removeStack(1, 1);
            }
            if (entry.test(this.getStack(2))) {
                removeStack(2, 1);
            }
            if (entry.test(this.getStack(3))) {
                removeStack(3, 1);
            }
            if (entry.test(this.getStack(4))) {
                removeStack(4, 1);
            }
        }
    }

    private ItemStack generateOutputItem(Recipe<?> recipe) {
        ItemStack outputStack = recipe.getOutput();

        if (!(outputStack.getItem() instanceof EffectFood)) {
            return outputStack;
        }

        for (Ingredient ingredient : recipe.getIngredients()) {
            for (int j = 0; j < 5; j++) {
                ItemStack stack = this.getStack(j);
                if (ingredient.test(stack)) {
                    EffectFoodHelper.getEffects(stack).forEach(effect -> EffectFoodHelper.addEffect(outputStack, effect));
                    break;
                }
            }
        }
        return outputStack;
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
        if (slot == BOTTLE_INPUT_SLOT || slot == 2 || slot == 3 || slot == 4|| slot == 5) {
            if (!dirty) {
                this.totalFermentationTime = 50;
                this.fermentationTime = 0;
                markDirty();
            }
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
        this.inventory.clear();
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new FermentationBarrelGuiHandler(syncId, inv, this, this.propertyDelegate);
    }
}
