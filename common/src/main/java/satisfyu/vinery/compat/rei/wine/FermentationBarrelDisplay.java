package satisfyu.vinery.compat.rei.wine;


import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.compat.rei.VineryReiClientPlugin;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FermentationBarrelDisplay extends BasicDisplay {

    public static final CategoryIdentifier<FermentationBarrelDisplay> FERMENTATION_BARREL_DISPLAY = CategoryIdentifier.of(Vinery.MODID, "fermentation_barrel_display");


    public FermentationBarrelDisplay(FermentationBarrelRecipe recipe) {
        this(EntryIngredients.ofIngredients(VineryReiClientPlugin.ingredients(recipe, new ItemStack(ObjectRegistry.WINE_BOTTLE.get()))), Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()))), Optional.ofNullable(recipe.getId()));
    }

    public FermentationBarrelDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location) {
        super(inputs, outputs, location);
    }





    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return FERMENTATION_BARREL_DISPLAY;
    }
}
