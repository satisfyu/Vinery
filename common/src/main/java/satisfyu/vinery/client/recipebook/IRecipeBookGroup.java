package satisfyu.vinery.client.recipebook;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public interface IRecipeBookGroup {
	boolean fitRecipe(Recipe<? extends Container> recipe);

	List<ItemStack> getIcons();
}
