package daniking.vinery.compat.rei.wine;


import daniking.vinery.Vinery;
import daniking.vinery.recipe.FermentationBarrelRecipe;
import daniking.vinery.registry.ObjectRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FermentationBarrelDisplay extends BasicDisplay {

    public static final CategoryIdentifier<FermentationBarrelDisplay> FERMENTATION_BARREL_DISPLAY = CategoryIdentifier.of(Vinery.MODID, "fermentation_barrel_display");


    public FermentationBarrelDisplay(FermentationBarrelRecipe recipe) {
        this(EntryIngredients.ofIngredients(ingredients(recipe, new ItemStack(ObjectRegistry.WINE_BOTTLE))), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.ofNullable(recipe.getId()));
    }

    public FermentationBarrelDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }


    public static List<Ingredient> ingredients(Recipe<Inventory> recipe, ItemStack stack){
        List<Ingredient> l = recipe.getIngredients();
        l.add(0, Ingredient.ofItems(stack.getItem()));
        return l;
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return new FermentationBarrelCategory().getCategoryIdentifier();
    }
}
