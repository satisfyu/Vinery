package daniking.vinery.compat.rei;


import daniking.vinery.Vinery;
import daniking.vinery.recipe.CookingPotRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CookingPotDisplay extends BasicDisplay {

    public static final CategoryIdentifier<CookingPotDisplay> MY_DISPLAY = CategoryIdentifier.of(Vinery.MODID, "cooking_pot_display");

    public CookingPotDisplay(CookingPotRecipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.ofNullable(recipe.getId()));
    }

    public CookingPotDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return new CookingPotCategory().getCategoryIdentifier();
    }
}
