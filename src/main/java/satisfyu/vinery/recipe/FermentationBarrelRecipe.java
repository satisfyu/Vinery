package satisfyu.vinery.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.util.VineryUtils;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class FermentationBarrelRecipe implements Recipe<Inventory> {

    private final Identifier identifier;
    private final DefaultedList<Ingredient> inputs;
    private final ItemStack output;

    public FermentationBarrelRecipe(Identifier identifier, DefaultedList<Ingredient> inputs, ItemStack output) {
        this.identifier = identifier;
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int matchingStacks = 0;

        for(int i = 1; i < 5; ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                ++matchingStacks;
                recipeMatcher.addInput(itemStack, 1);
            }
        }

        return matchingStacks == this.inputs.size() && recipeMatcher.match(this, null);
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output.copy();
    }

    @Override
    public Identifier getId() {
        return this.identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_TYPE;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }
    public static class Serializer implements RecipeSerializer<FermentationBarrelRecipe> {

        @Override
        public FermentationBarrelRecipe read(Identifier id, JsonObject json) {
            final var ingredients = VineryUtils.deserializeIngredients(JsonHelper.getArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Fermentation Barrel");
            } else if (ingredients.size() > 4) {
                throw new JsonParseException("Too many ingredients for Fermentation Barrel");
            } else {
                return new FermentationBarrelRecipe(id, ingredients, ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result")));
            }
        }

        @Override
        public FermentationBarrelRecipe read(Identifier id, PacketByteBuf buf) {
            final var ingredients  = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromPacket(buf));
            return new FermentationBarrelRecipe(id, ingredients, buf.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buf, FermentationBarrelRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.getOutput());
        }
    }
}
