package satisfyu.vinery.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.architectury.core.AbstractRecipeSerializer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.util.VineryUtils;

public class FermentationBarrelRecipe implements Recipe<Container> {
	private final ResourceLocation identifier;

	private final NonNullList<Ingredient> inputs;

	private final ItemStack output;

	public FermentationBarrelRecipe(ResourceLocation identifier, NonNullList<Ingredient> inputs, ItemStack output) {
		this.identifier = identifier;
		this.inputs = inputs;
		this.output = output;
	}

	@Override
	public boolean matches(Container inventory, Level world) {
		StackedContents recipeMatcher = new StackedContents();
		int matchingStacks = 0;
		for (int i = 1; i < 5; ++i) {
			ItemStack itemStack = inventory.getItem(i);
			if (!itemStack.isEmpty()) {
				++matchingStacks;
				recipeMatcher.accountStack(itemStack, 1);
			}
		}
		return matchingStacks == this.inputs.size() && recipeMatcher.canCraft(this, null);
	}

	@Override
	public ItemStack assemble(Container inventory) {
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputs;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return this.output.copy();
	}

	@Override
	public ResourceLocation getId() {
		return this.identifier;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_TYPE.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer extends AbstractRecipeSerializer<FermentationBarrelRecipe> {

		@Override
		public FermentationBarrelRecipe fromJson(ResourceLocation id, JsonObject json) {
			final var ingredients = VineryUtils.deserializeIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
			if (ingredients.isEmpty()) {
				throw new JsonParseException("No ingredients for Fermentation Barrel");
			}
			else if (ingredients.size() > 4) {
				throw new JsonParseException("Too many ingredients for Fermentation Barrel");
			}
			else {
				return new FermentationBarrelRecipe(id, ingredients,
						ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result")));
			}
		}

		@Override
		public FermentationBarrelRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			final var ingredients = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
			ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buf));
			return new FermentationBarrelRecipe(id, ingredients, buf.readItem());
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, FermentationBarrelRecipe recipe) {
			buf.writeVarInt(recipe.inputs.size());
			for (Ingredient ingredient : recipe.inputs) {
				ingredient.toNetwork(buf);
			}
			buf.writeItem(recipe.output);
		}
	}
}
