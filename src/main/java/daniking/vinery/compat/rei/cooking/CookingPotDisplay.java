package daniking.vinery.compat.rei.cooking;


import daniking.vinery.Vinery;
import daniking.vinery.recipe.CookingPotRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CookingPotDisplay extends BasicDisplay {

    public static final CategoryIdentifier<CookingPotDisplay> MY_DISPLAY = CategoryIdentifier.of(Vinery.MODID, "cooking_pot_display");


    public CookingPotDisplay(CookingPotRecipe recipe) {

        this(EntryIngredients.ofIngredients(ingredients(recipe, recipe.getContainer())),


                Collections.singletonList(EntryIngredients.of(
                        recipe.getOutput())),
                Optional.ofNullable(recipe.getId()));

    }

    public CookingPotDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }


    public static List<Ingredient> ingredients(CookingPotRecipe recipe, ItemStack stack){
        List<Ingredient> l = recipe.getIngredients();
        l.add(0, Ingredient.ofItems(stack.getItem()));
        return l;
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return new CookingPotCategory().getCategoryIdentifier();
    }
}
