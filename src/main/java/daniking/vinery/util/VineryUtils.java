package daniking.vinery.util;

import com.google.gson.JsonArray;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class VineryUtils {

    public static boolean inventoryMatches(Inventory inventory, DefaultedList<Ingredient> inputs) {
        final List<ItemStack> validStacks = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            final ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) validStacks.add(stack);
        }
        if (inputs.size() != validStacks.size()) {
            return false;
        }
        for (Ingredient ingredient : inputs) {
            boolean found = false;
            for (ItemStack stack : validStacks) {
                if (ingredient.test(stack)) {
                    found = true;
                    validStacks.remove(stack);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public static DefaultedList<Ingredient> deserializeIngredients(JsonArray json) {
        DefaultedList<Ingredient> ingredients = DefaultedList.of();
        for (int i = 0; i < json.size(); i++) {
            Ingredient ingredient = Ingredient.fromJson(json.get(i));
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }
}
