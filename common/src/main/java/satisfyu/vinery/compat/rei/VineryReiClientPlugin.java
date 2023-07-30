package satisfyu.vinery.compat.rei;

import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import satisfyu.vinery.compat.rei.press.ApplePressCategory;
import satisfyu.vinery.compat.rei.press.ApplePressDisplay;
import satisfyu.vinery.compat.rei.wine.FermentationBarrelCategory;
import satisfyu.vinery.compat.rei.wine.FermentationBarrelDisplay;
import satisfyu.vinery.recipe.ApplePressRecipe;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.List;


public class VineryReiClientPlugin {
    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new FermentationBarrelCategory());
        registry.add(new ApplePressCategory());
        registry.addWorkstations(FermentationBarrelDisplay.FERMENTATION_BARREL_DISPLAY, EntryStacks.of(ObjectRegistry.FERMENTATION_BARREL.get()));
        registry.addWorkstations(ApplePressDisplay.APPLE_PRESS_DISPLAY, EntryStacks.of(ObjectRegistry.APPLE_PRESS.get()));
    }

    public static void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(FermentationBarrelRecipe.class, FermentationBarrelDisplay::new);
        registry.registerFiller(ApplePressRecipe.class, ApplePressDisplay::new);
    }

    public static List<Ingredient> ingredients(Recipe<Container> recipe, ItemStack stack){
        List<Ingredient> l = new ArrayList<>(recipe.getIngredients());
        l.add(0, Ingredient.of(stack.getItem()));
        return l;
    }

}
