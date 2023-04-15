package satisfyu.vinery.client.recipebook;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractPrivateRecipeScreenHandler extends AbstractContainerMenu {
    protected AbstractPrivateRecipeScreenHandler(@Nullable MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    public abstract List<IRecipeBookGroup>  getGroups();

    public abstract boolean hasIngredient(Recipe<?> recipe);

    public abstract int getCraftingSlotCount();

    public abstract ItemStack transferSlot(Player player, int invSlot);
}
