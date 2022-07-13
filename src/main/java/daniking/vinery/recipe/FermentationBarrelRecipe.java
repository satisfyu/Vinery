package daniking.vinery.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daniking.vinery.registry.VineryRecipeTypes;
import daniking.vinery.util.GrapevineType;
import daniking.vinery.util.VineryUtils;
import daniking.vinery.util.WineType;
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
    private final String wineType;

    public FermentationBarrelRecipe(Identifier identifier, DefaultedList<Ingredient> inputs, String wineType, ItemStack output) {
        this.identifier = identifier;
        this.inputs = inputs;
        this.output = output;
        this.wineType = wineType;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        int matchingStacks = 0;
        for (final Ingredient entry : this.inputs) {
            if (entry.test(inventory.getStack(2))) {
                matchingStacks++;
            } else if (entry.test(inventory.getStack(3))) {
                matchingStacks++;
            } else if (entry.test(inventory.getStack(4))) {
                matchingStacks++;
            } else if (entry.test(inventory.getStack(5))) {
                matchingStacks++;
            }
        }
        return matchingStacks == this.inputs.size();
    }

    public WineType getWineType() {
        return switch (this.wineType) {
            case "red" -> WineType.RED;
            case "white" -> WineType.WHITE;
            default -> throw new IllegalArgumentException("Invalid wine type was set");
        };
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

    public static class Serializer implements RecipeSerializer<FermentationBarrelRecipe> {

        @Override
        public FermentationBarrelRecipe read(Identifier id, JsonObject json) {
            final var ingredients = VineryUtils.deserializeIngredients(JsonHelper.getArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Fermentation Barrel");
            } else if (ingredients.size() > 4) {
                throw new JsonParseException("Too many ingredients for Fermentation Barrel");
            } else {
                return new FermentationBarrelRecipe(id, ingredients, JsonHelper.getString(json, "wineType"), ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result")));
            }
        }

        @Override
        public FermentationBarrelRecipe read(Identifier id, PacketByteBuf buf) {
            final var ingredients  = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromPacket(buf));
            return new FermentationBarrelRecipe(id, ingredients, buf.readString(), buf.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buf, FermentationBarrelRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(buf);
            }
            buf.writeString(recipe.getWineType().toString());
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
