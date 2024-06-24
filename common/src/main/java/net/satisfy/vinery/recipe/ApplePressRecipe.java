package net.satisfy.vinery.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.registry.RecipeTypesRegistry;
import org.jetbrains.annotations.NotNull;

public class ApplePressRecipe implements Recipe<Container> {
    private final ResourceLocation identifier;
    public final Ingredient input;
    private final ItemStack output;

    public ApplePressRecipe(ResourceLocation identifier, Ingredient input, ItemStack output) {
        this.identifier = identifier;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(Container inventory, Level world) {
        return input.test(inventory.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.output.copy();
    }

    public ItemStack assemble() {
        return assemble(null, null);
    }

    public ItemStack getResultItem() {
         return getResultItem(null);
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input);
        return list;
    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.identifier;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypesRegistry.APPLE_PRESS_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypesRegistry.APPLE_PRESS_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
    public static class Serializer implements RecipeSerializer<ApplePressRecipe> {

        @Override
        public @NotNull ApplePressRecipe fromJson(ResourceLocation id, JsonObject json) {
            final Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));

            if (ingredient.isEmpty()) {
                throw new JsonParseException("No ingredients for recipe: " + id);
            } else {
                return new ApplePressRecipe(id, ingredient, ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output")));
            }
        }

        @Override
        public @NotNull ApplePressRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new ApplePressRecipe(id, Ingredient.fromNetwork(buf), buf.readItem());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ApplePressRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeItem(recipe.output);
        }
    }
}
