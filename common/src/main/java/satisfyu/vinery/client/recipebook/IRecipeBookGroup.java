package satisfyu.vinery.client.recipebook;

import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public interface IRecipeBookGroup {
    boolean fitRecipe(Recipe<? extends Container> recipe);
    List<ItemStack> getIcons();
}
