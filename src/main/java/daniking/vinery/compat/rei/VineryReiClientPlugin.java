package daniking.vinery.compat.rei;

import daniking.vinery.compat.farmersdelight.FarmersCookingPot;
import daniking.vinery.compat.rei.cooking.CookingPotCategory;
import daniking.vinery.compat.rei.cooking.CookingPotDisplay;
import daniking.vinery.compat.rei.press.WinePressCategory;
import daniking.vinery.compat.rei.press.WinePressDisplay;
import daniking.vinery.compat.rei.stove.WoodFiredOvenCategory;
import daniking.vinery.compat.rei.stove.WoodFiredOvenDisplay;
import daniking.vinery.compat.rei.wine.FermentationBarrelCategory;
import daniking.vinery.compat.rei.wine.FermentationBarrelDisplay;
import daniking.vinery.recipe.CookingPotRecipe;
import daniking.vinery.recipe.FermentationBarrelRecipe;
import daniking.vinery.recipe.WoodFiredOvenRecipe;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.util.VineryUtils;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

public class VineryReiClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CookingPotCategory());
        registry.add(new WoodFiredOvenCategory());
        registry.add(new FermentationBarrelCategory());
        registry.add(new WinePressCategory());

        registry.addWorkstations(CookingPotDisplay.COOKING_POT_DISPLAY, EntryStacks.of(ObjectRegistry.COOKING_POT));
        registry.addWorkstations(WoodFiredOvenDisplay.WOOD_FIRED_OVEN_DISPLAY, EntryStacks.of(ObjectRegistry.WOOD_FIRED_OVEN));
        registry.addWorkstations(FermentationBarrelDisplay.FERMENTATION_BARREL_DISPLAY, EntryStacks.of(ObjectRegistry.FERMENTATION_BARREL));
        registry.addWorkstations(WinePressDisplay.WINE_PRESS_DISPLAY, EntryStacks.of(ObjectRegistry.WINE_PRESS));
        if(VineryUtils.isFDLoaded()) registry.addWorkstations(CategoryIdentifier.of("farmersdelight", "cooking"), EntryStacks.of(ObjectRegistry.COOKING_POT));

        registry.addWorkstations(BuiltinPlugin.FUEL, EntryStacks.of(ObjectRegistry.WOOD_FIRED_OVEN));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(CookingPotRecipe.class, CookingPotDisplay::new);
        if(VineryUtils.isFDLoaded()) registry.registerFiller(FarmersCookingPot.getRecipeClass(), CookingPotDisplay::new);
        registry.registerFiller(WoodFiredOvenRecipe.class, WoodFiredOvenDisplay::new);
        registry.registerFiller(FermentationBarrelRecipe.class, FermentationBarrelDisplay::new);
        registry.add(new WinePressDisplay());
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(WoodFiredOvenDisplay.WOOD_FIRED_OVEN_DISPLAY, WoodFiredOvenDisplay.serializer(WoodFiredOvenDisplay::new));
    }

    public static List<Ingredient> ingredients(Recipe<Inventory> recipe, ItemStack stack){
        List<Ingredient> l = new ArrayList<>(recipe.getIngredients());
        l.add(0, Ingredient.ofItems(stack.getItem()));
        return l;
    }

}
