package satisfyu.vinery.block.entity;

import satisfyu.vinery.block.CookingPotBlock;
import satisfyu.vinery.client.gui.handler.CookingPotGuiHandler;
import satisfyu.vinery.compat.farmersdelight.FarmersCookingPot;
import satisfyu.vinery.item.food.EffectFood;
import satisfyu.vinery.item.food.EffectFoodHelper;
import satisfyu.vinery.recipe.CookingPotRecipe;
import satisfyu.vinery.registry.VineryBlockEntityTypes;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.util.VineryTags;
import satisfyu.vinery.util.VineryUtils;
import net.minecraft.block.Block;
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
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.item.ItemStack.canCombine;

public class CookingPotEntity extends BlockEntity implements BlockEntityTicker<CookingPotEntity>, Inventory, NamedScreenHandlerFactory {
	
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(MAX_CAPACITY, ItemStack.EMPTY);
	private static final int MAX_CAPACITY = 8;
	public static final int MAX_COOKING_TIME = 600; // Time in ticks (30s)
	private int cookingTime;
	public static final int BOTTLE_INPUT_SLOT = 6;
	public static final int OUTPUT_SLOT = 7;
	private static final int INGREDIENTS_AREA = 2 * 3;
	
	private boolean isBeingBurned;

	private final PropertyDelegate delegate;
	
	public CookingPotEntity(BlockPos pos, BlockState state) {
		super(VineryBlockEntityTypes.COOKING_POT_BLOCK_ENTITY, pos, state);
		this.delegate = new PropertyDelegate() {
			@Override
			public int get(int index) {
				return switch (index) {
					case 0 -> CookingPotEntity.this.cookingTime;
					case 1 -> CookingPotEntity.this.isBeingBurned ? 1 : 0;
					default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
					case 0 -> CookingPotEntity.this.cookingTime = value;
					case 1 -> CookingPotEntity.this.isBeingBurned = value != 0;
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
			Inventories.readNbt(nbt, this.inventory);
			this.cookingTime = nbt.getInt("CookingTime");
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, this.inventory);
		nbt.putInt("CookingTime", this.cookingTime);
	}
	
	public boolean isBeingBurned() {
		if (getWorld() == null)
			throw new NullPointerException("Null world invoked");
		final BlockState belowState = this.getWorld().getBlockState(getPos().down());
		final var optionalList = Registry.BLOCK.getEntryList(VineryTags.ALLOWS_COOKING_ON_POT);
		final var entryList = optionalList.orElse(null);
		if (entryList == null) {
			return false;
		} else if (!entryList.contains(belowState.getBlock().getRegistryEntry())) {
			return false;
		} else
			return belowState.get(Properties.LIT);
	}
	
	private boolean canCraft(Recipe<?> recipe) {
		if (recipe == null || recipe.getOutput().isEmpty()) {
			return false;
		}
		if(recipe instanceof CookingPotRecipe cookingRecipe){
			if (!this.getStack(BOTTLE_INPUT_SLOT).isOf(cookingRecipe.getContainer().getItem())) {
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
		else {
			if(VineryUtils.isFDLoaded()){
				return FarmersCookingPot.canCraft(recipe, this);
			}
		}
		return false;
	}
	
	private void craft(Recipe<?> recipe) {
		if (!canCraft(recipe)) {
			return;
		}
		final ItemStack recipeOutput = generateOutputItem(recipe);
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

	private ItemStack generateOutputItem(Recipe<?> recipe) {
		ItemStack outputStack = recipe.getOutput();

		if (!(outputStack.getItem() instanceof EffectFood)) {
			return outputStack;
		}

		for (Ingredient ingredient : recipe.getIngredients()) {
			for (int j = 0; j < 6; j++) {
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
	public void tick(World world, BlockPos pos, BlockState state, CookingPotEntity blockEntity) {
		if (world.isClient()) {
			return;
		}
		this.isBeingBurned = isBeingBurned();
		if (!this.isBeingBurned){
			if(state.get(CookingPotBlock.LIT)) {
				world.setBlockState(pos, state.with(CookingPotBlock.LIT, false), Block.NOTIFY_ALL);
			}
			return;
		}
		Recipe<?> recipe = world.getRecipeManager().getFirstMatch(VineryRecipeTypes.COOKING_POT_RECIPE_TYPE, this, world).orElse(null);
		if(recipe == null && VineryUtils.isFDLoaded()){
			recipe = FarmersCookingPot.getRecipe(world, this);
		}

		boolean canCraft = canCraft(recipe);
		if (canCraft) {
			this.cookingTime++;
			if (this.cookingTime >= MAX_COOKING_TIME) {
				this.cookingTime = 0;
				craft(recipe);
			}
		} else if (!canCraft(recipe)) {
			this.cookingTime = 0;
		}
		if (canCraft) {
			world.setBlockState(pos, this.getCachedState().getBlock().getDefaultState().with(CookingPotBlock.COOKING, true).with(CookingPotBlock.LIT, true), Block.NOTIFY_ALL);
		} else if (state.get(CookingPotBlock.COOKING)) {
			world.setBlockState(pos, this.getCachedState().getBlock().getDefaultState().with(CookingPotBlock.COOKING, false).with(CookingPotBlock.LIT, true), Block.NOTIFY_ALL);
		}
		else if(state.get(CookingPotBlock.LIT) != isBeingBurned){
			world.setBlockState(pos, state.with(CookingPotBlock.LIT, isBeingBurned), Block.NOTIFY_ALL);
		}
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
		this.markDirty();
	}


	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		if (this.world.getBlockEntity(this.pos) != this) {
			return false;
		} else {
			return player.squaredDistanceTo((double) this.pos.getX() + 0.5, (double) this.pos.getY() + 0.5, (double) this.pos.getZ() + 0.5) <= 64.0;
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
		return new CookingPotGuiHandler(syncId, inv, this, this.delegate);
	}
}


