package daniking.vinery.util;

import com.google.gson.JsonArray;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

public class VineryUtils {


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
