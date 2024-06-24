package net.satisfy.vinery.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.registry.RecipeTypesRegistry;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.identifier;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
    public static class Serializer implements RecipeSerializer<FermentationBarrelRecipe> {

        @Override
        public @NotNull FermentationBarrelRecipe fromJson(ResourceLocation id, JsonObject json) {
            final var ingredients = GeneralUtil.deserializeIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Fermentation Barrel");
            } else if (ingredients.size() > 4) {
                throw new JsonParseException("Too many ingredients for Fermentation Barrel");
            } else {
                return new FermentationBarrelRecipe(id, ingredients, ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result")));
            }
        }

        @Override
        public @NotNull FermentationBarrelRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
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
