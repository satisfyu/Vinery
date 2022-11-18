package daniking.vinery.compat.rei;

import daniking.vinery.compat.rei.cooking.CookingPotCategory;
import daniking.vinery.compat.rei.cooking.CookingPotDisplay;
import daniking.vinery.compat.rei.stove.StoveCategory;
import daniking.vinery.compat.rei.stove.StoveDisplay;
import daniking.vinery.compat.rei.wine.WineCategory;
import daniking.vinery.compat.rei.wine.WineDisplay;
import daniking.vinery.recipe.CookingPotRecipe;
import daniking.vinery.recipe.FermentationBarrelRecipe;
import daniking.vinery.recipe.StoveCookingRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;

public class VineryReiClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CookingPotCategory());
        registry.add(new StoveCategory());
        registry.add(new WineCategory());
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(CookingPotRecipe.class, CookingPotDisplay::new);
        registry.registerFiller(StoveCookingRecipe.class, StoveDisplay::new);
        registry.registerFiller(FermentationBarrelRecipe.class, WineDisplay::new);
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(StoveDisplay.MY_STOVE_DISPLAY, StoveDisplay.serializer(StoveDisplay::new));
    }
}
