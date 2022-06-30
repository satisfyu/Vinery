package daniking.vinery.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import daniking.vinery.client.gui.handler.StoveGuiHandler;
import daniking.vinery.registry.VineryRecipeTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class StoveCookingRecipe implements Recipe<Inventory> {

    protected final Identifier id;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final float experience;

    public StoveCookingRecipe(Identifier id, Ingredient input, ItemStack output, float experience) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.experience = experience;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(StoveGuiHandler.INGREDIENT_SLOT));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.input);
        return defaultedList;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }


    public float getExperience() {
        return experience;
    }

    public Ingredient getInput() {
        return input;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return VineryRecipeTypes.STOVE_COOKING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return VineryRecipeTypes.STOVE_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<StoveCookingRecipe> {

        @Override
        public StoveCookingRecipe read(Identifier id, JsonObject json) {
            final JsonElement jsonElement =  JsonHelper.getObject(json, "ingredient");
            final Ingredient ingredient = Ingredient.fromJson(jsonElement);
            final ItemStack outputStack = ShapedRecipe.outputFromJson(json);
            float xp = JsonHelper.getFloat(json, "experience", 0.0F);
            return new StoveCookingRecipe(id, ingredient, outputStack, xp);
        }


        @Override
        public StoveCookingRecipe read(Identifier id, PacketByteBuf buf) {
            final Ingredient ingredient = Ingredient.fromPacket(buf);
            final ItemStack output = buf.readItemStack();
            final float xp = buf.readFloat();
            return new StoveCookingRecipe(id, ingredient, output, xp);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, StoveCookingRecipe recipe) {
            recipe.input.write(packetByteBuf);
            packetByteBuf.writeItemStack(recipe.output);
            packetByteBuf.writeFloat(recipe.experience);
        }
    }
}
