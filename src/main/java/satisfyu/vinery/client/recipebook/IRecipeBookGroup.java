package satisfyu.vinery.client.recipebook;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;

import java.util.List;

public interface IRecipeBookGroup {
    boolean fitRecipe(Recipe<? extends Inventory> recipe);
    List<ItemStack> getIcons();
}
