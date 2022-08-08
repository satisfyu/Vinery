package daniking.vinery.util;

import com.google.gson.JsonArray;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class VineryUtils {

    public static boolean matchesRecipe(Inventory inventory, DefaultedList<Ingredient> recipe, int startIndex, int endIndex) {
        final List<ItemStack> validStacks = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            final ItemStack stackInSlot = inventory.getStack(i);
            if (!stackInSlot.isEmpty()) validStacks.add(stackInSlot);
        }
        if (validStacks.size() != recipe.size()) {
            return false;
        }
        for (Ingredient entry : recipe) {
            boolean matches = false;
            for (ItemStack item : validStacks) {
                if (entry.test(item)) {
                    matches = true;
                    validStacks.remove(item);
                    break;
                }
            }
            if (!matches) {
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

    public static boolean isIndexInRange(int index, int startInclusive, int endInclusive) {
        return index >= startInclusive && index <= endInclusive;
    }
}
