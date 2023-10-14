package satisfyu.vinery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import satisfyu.vinery.registry.VineryRecipeTypes;

public class ApplePressRecipe implements Recipe<Container> {
    public final Ingredient ingredient;
    private final ItemStack result;

    public ApplePressRecipe(ItemStack result, Ingredient ingredient) {
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public boolean matches(Container inventory, Level world) {
        return ingredient.test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    public ItemStack assemble() {
        return assemble(null, null);
    }

    public ItemStack getResultItem() {
         return getResultItem(null);
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VineryRecipeTypes.APPLE_PRESS_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return VineryRecipeTypes.APPLE_PRESS_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<ApplePressRecipe> {
        private static final Codec<ApplePressRecipe> CODEC = RecordCodecBuilder.create(
                (instance) -> instance.group(CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter((shapelessRecipe) -> shapelessRecipe.result),
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredients").forGetter((shapelessRecipe) -> shapelessRecipe.ingredient)
                ).apply(instance, ApplePressRecipe::new));
        @Override
        public Codec<ApplePressRecipe> codec() {
            return CODEC;
        }

        @Override
        public ApplePressRecipe fromNetwork(FriendlyByteBuf buf) {
            return new ApplePressRecipe(buf.readItem(), Ingredient.fromNetwork(buf));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ApplePressRecipe recipe) {
            recipe.ingredient.toNetwork(buf);
            buf.writeItem(recipe.result);
        }
    }
}
