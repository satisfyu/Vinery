package satisfyu.vinery.client.recipebook;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractPrivateRecipeScreenHandler extends ScreenHandler {
    protected AbstractPrivateRecipeScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    public abstract List<IRecipeBookGroup>  getGroups();

    public abstract boolean hasIngredient(Recipe<?> recipe);

    public abstract int getCraftingSlotCount();

    public abstract ItemStack transferSlot(PlayerEntity player, int invSlot);
}
