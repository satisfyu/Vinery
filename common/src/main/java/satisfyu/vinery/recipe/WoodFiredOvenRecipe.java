package satisfyu.vinery.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.architectury.core.AbstractRecipeSerializer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.util.VineryUtils;

public class WoodFiredOvenRecipe implements Recipe<Container> {
	protected final ResourceLocation id;

	protected final NonNullList<Ingredient> inputs;

	protected final ItemStack output;

	protected final float experience;

	public WoodFiredOvenRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, float experience) {
		this.id = id;
		this.inputs = inputs;
		this.output = output;
		this.experience = experience;
	}

	@Override
	public boolean matches(Container inventory, Level world) {
		return VineryUtils.matchesRecipe(inventory, inputs, 0, 2);
	}

	@Override
	public ItemStack assemble(Container container) {
		return output.copy();
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
	public NonNullList<Ingredient> getIngredients() {
		return this.inputs;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	public float getExperience() {
		return experience;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return VineryRecipeTypes.WOOD_FIRED_OVEN_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return VineryRecipeTypes.WOOD_FIRED_OVEN_RECIPE_TYPE.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer extends AbstractRecipeSerializer<WoodFiredOvenRecipe> {
		@Override
		public @NotNull WoodFiredOvenRecipe fromJson(ResourceLocation id, JsonObject json) {
			final var ingredients = VineryUtils.deserializeIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
			if (ingredients.isEmpty()) {
				throw new JsonParseException("No ingredients for StoveCooking Recipe");
			}
			else if (ingredients.size() > 3) {
				throw new JsonParseException("Too many ingredients for StoveCooking Recipe");
			}
			else {
				final ItemStack outputStack = ShapedRecipe.itemStackFromJson(json);
				float xp = GsonHelper.getAsFloat(json, "experience", 0.0F);
				return new WoodFiredOvenRecipe(id, ingredients, outputStack, xp);
			}
		}

		@Override
		public @NotNull WoodFiredOvenRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			final var ingredients = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
			ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buf));
			final ItemStack output = buf.readItem();
			final float xp = buf.readFloat();
			return new WoodFiredOvenRecipe(id, ingredients, output, xp);
		}

		@Override
		public void toNetwork(FriendlyByteBuf packet, WoodFiredOvenRecipe recipe) {
			packet.writeVarInt(recipe.inputs.size());
			recipe.inputs.forEach(entry -> entry.toNetwork(packet));
			packet.writeItem(recipe.output);
			packet.writeFloat(recipe.experience);
		}
	}
}
