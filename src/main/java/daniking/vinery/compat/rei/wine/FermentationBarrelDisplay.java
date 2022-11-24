package daniking.vinery.compat.rei.wine;


import daniking.vinery.Vinery;
import daniking.vinery.compat.rei.VineryReiClientPlugin;
import daniking.vinery.recipe.FermentationBarrelRecipe;
import daniking.vinery.registry.ObjectRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FermentationBarrelDisplay extends BasicDisplay {

    public static final CategoryIdentifier<FermentationBarrelDisplay> FERMENTATION_BARREL_DISPLAY = CategoryIdentifier.of(Vinery.MODID, "fermentation_barrel_display");


    public FermentationBarrelDisplay(FermentationBarrelRecipe recipe) {
        this(EntryIngredients.ofIngredients(VineryReiClientPlugin.ingredients(recipe, new ItemStack(ObjectRegistry.WINE_BOTTLE))), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.ofNullable(recipe.getId()));
    }

    public FermentationBarrelDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }





    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return FERMENTATION_BARREL_DISPLAY;
    }
}
