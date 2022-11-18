package daniking.vinery.compat.rei;

import daniking.vinery.compat.rei.cooking.CookingPotCategory;
import daniking.vinery.compat.rei.cooking.CookingPotDisplay;
import daniking.vinery.compat.rei.stove.WoodFiredOvenCategory;
import daniking.vinery.compat.rei.stove.WoodFiredOvenDisplay;
import daniking.vinery.compat.rei.wine.FermentationBarrelCategory;
import daniking.vinery.compat.rei.wine.FermentationBarrelDisplay;
import daniking.vinery.recipe.CookingPotRecipe;
import daniking.vinery.recipe.FermentationBarrelRecipe;
import daniking.vinery.recipe.WoodFiredOvenRecipe;
import daniking.vinery.registry.ObjectRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;

public class VineryReiClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CookingPotCategory());
        registry.add(new WoodFiredOvenCategory());
        registry.add(new FermentationBarrelCategory());

        registry.addWorkstations(CookingPotDisplay.COOKING_POT_DISPLAY, EntryStacks.of(ObjectRegistry.COOKING_POT));
        registry.addWorkstations(WoodFiredOvenDisplay.WOOD_FIRED_OVEN_DISPLAY, EntryStacks.of(ObjectRegistry.WOOD_FIRED_OVEN));
        registry.addWorkstations(FermentationBarrelDisplay.FERMENTATION_BARREL_DISPLAY, EntryStacks.of(ObjectRegistry.FERMENTATION_BARREL));
        registry.addWorkstations(BuiltinPlugin.FUEL, EntryStacks.of(ObjectRegistry.WOOD_FIRED_OVEN));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(CookingPotRecipe.class, CookingPotDisplay::new);
        registry.registerFiller(WoodFiredOvenRecipe.class, WoodFiredOvenDisplay::new);
        registry.registerFiller(FermentationBarrelRecipe.class, FermentationBarrelDisplay::new);
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(WoodFiredOvenDisplay.WOOD_FIRED_OVEN_DISPLAY, WoodFiredOvenDisplay.serializer(WoodFiredOvenDisplay::new));
    }

}
