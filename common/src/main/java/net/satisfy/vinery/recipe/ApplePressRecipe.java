package net.satisfy.vinery.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.registry.RecipeTypesRegistry;
import org.jetbrains.annotations.NotNull;

public class ApplePressRecipe implements Recipe<RecipeInput> {
    public final Ingredient input;
    private final ItemStack output;

    public ApplePressRecipe(Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
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
    public boolean matches(RecipeInput recipeInput, Level level) {
        return input.test(recipeInput.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output;
    }

    public @NotNull ResourceLocation getId() {
        return RecipeTypesRegistry.APPLE_PRESS_RECIPE_TYPE.getId();
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
        public static final MapCodec<ApplePressRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
                        ItemStack.CODEC.fieldOf("output").forGetter(ApplePressRecipe::getResultItem)
                ).apply(instance, ApplePressRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, ApplePressRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.input,
                ItemStack.OPTIONAL_STREAM_CODEC, ApplePressRecipe::getResultItem,
                ApplePressRecipe::new
        );

        @Override
        public @NotNull MapCodec<ApplePressRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ApplePressRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
