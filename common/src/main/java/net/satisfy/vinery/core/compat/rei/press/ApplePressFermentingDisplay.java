package net.satisfy.vinery.core.compat.rei.press;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.recipe.ApplePressFermentingRecipe;
import net.satisfy.vinery.core.recipe.ApplePressMashingRecipe;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("all")
public class ApplePressFermentingDisplay extends BasicDisplay {

    public static final CategoryIdentifier<ApplePressFermentingDisplay> APPLE_PRESS_DISPLAY = CategoryIdentifier.of(Vinery.MOD_ID, "apple_press_fermenting_display");

    public ApplePressFermentingDisplay(RecipeHolder<ApplePressFermentingRecipe> recipe) {
        this(Collections.singletonList(EntryIngredients.ofIngredient(recipe.value().input)), Collections.singletonList(EntryIngredients.of(recipe.value().getResultItem(null))));
    }

    public ApplePressFermentingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return APPLE_PRESS_DISPLAY;
    }


}
