package satisfyu.vinery.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.util.VineryUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

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

        for(int i = 1; i < 5; ++i) {
            ItemStack itemStack = inventory.getItem(i);
            if (!itemStack.isEmpty()) {
                ++matchingStacks;
                recipeMatcher.accountStack(itemStack, 1);
            }
        }

        return matchingStacks == this.inputs.size() && recipeMatcher.canCraft(this, null);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
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
    public ItemStack getResultItem(RegistryAccess registryAccess) {
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
    public static class Serializer implements RecipeSerializer<FermentationBarrelRecipe> {

        @Override
        public FermentationBarrelRecipe fromJson(ResourceLocation id, JsonObject json) {
            final var ingredients = VineryUtils.deserializeIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Fermentation Barrel");
            } else if (ingredients.size() > 4) {
                throw new JsonParseException("Too many ingredients for Fermentation Barrel");
            } else {
                return new FermentationBarrelRecipe(id, ingredients, ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result")));
            }
        }

        @Override
        public FermentationBarrelRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            final var ingredients  = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
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
