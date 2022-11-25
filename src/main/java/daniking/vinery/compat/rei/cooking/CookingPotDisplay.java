package daniking.vinery.compat.rei.cooking;


import daniking.vinery.Vinery;
import daniking.vinery.compat.farmersdelight.FarmersCookingPot;
import daniking.vinery.compat.rei.VineryReiClientPlugin;
import daniking.vinery.recipe.CookingPotRecipe;
import daniking.vinery.util.VineryUtils;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CookingPotDisplay extends BasicDisplay {

    public static final CategoryIdentifier<CookingPotDisplay> COOKING_POT_DISPLAY = CategoryIdentifier.of(Vinery.MODID, "cooking_pot_display");


    public CookingPotDisplay(Recipe<Inventory> recipe) {
        this(EntryIngredients.ofIngredients(VineryReiClientPlugin.ingredients(recipe, getContainer(recipe))), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.ofNullable(recipe.getId()));
    }

    public CookingPotDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return COOKING_POT_DISPLAY;
    }

    public static ItemStack getContainer(Recipe<Inventory> recipe){
        if(recipe instanceof CookingPotRecipe c){
            return c.getContainer();
        }
        else if(VineryUtils.isFDLoaded()){
            return FarmersCookingPot.getContainer(recipe);
        }
        else return ItemStack.EMPTY;
    }

}
