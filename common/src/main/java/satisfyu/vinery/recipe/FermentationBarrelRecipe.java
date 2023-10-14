package satisfyu.vinery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import satisfyu.vinery.registry.VineryRecipeTypes;

public class FermentationBarrelRecipe implements Recipe<Container> {
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public FermentationBarrelRecipe(ItemStack result, NonNullList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.result = result;
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

        return matchingStacks == this.ingredients.size() && recipeMatcher.canCraft(this, null);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result.copy();
    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
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
        private static final Codec<FermentationBarrelRecipe> CODEC = RecordCodecBuilder.create(
                (instance) -> instance.group(CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter((shapelessRecipe) -> shapelessRecipe.result)
                        , Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((list) -> {
                            Ingredient[] ingredients = list.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                            if (ingredients.length == 0) {
                                return DataResult.error(() -> "No ingredients for vinery:wine_fermentation recipe");
                            } else {
                                return ingredients.length > 3 ? DataResult.error(() -> "Too many ingredients for vinery:wine_fermentation recipe") : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
                            }
                        }, DataResult::success).forGetter((shapelessRecipe) -> shapelessRecipe.ingredients)
                ).apply(instance, FermentationBarrelRecipe::new));
        @Override
        public Codec<FermentationBarrelRecipe> codec() {
            return CODEC;
        }

        @Override
        public FermentationBarrelRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            final var ingredients  = NonNullList.withSize(friendlyByteBuf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(friendlyByteBuf));
            return new FermentationBarrelRecipe(friendlyByteBuf.readItem(), ingredients);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FermentationBarrelRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.result);
        }
    }
}
