package satisfyu.vinery.client.gui.handler;

import satisfyu.vinery.client.gui.handler.slot.ExtendedSlot;
import satisfyu.vinery.client.gui.handler.slot.StoveOutputSlot;
import satisfyu.vinery.client.recipebook.IRecipeBookGroup;
import satisfyu.vinery.client.recipebook.custom.WoodFiredOvenRecipeBookGroup;
import satisfyu.vinery.recipe.WoodFiredOvenRecipe;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.registry.VineryScreenHandlerTypes;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class StoveGuiHandler extends AbstractRecipeBookGUIScreenHandler {
    public static final int FUEL_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;
    public StoveGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(5), new SimpleContainerData(4));
    }

    public StoveGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData delegate) {
        super(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, syncId, 4, playerInventory, inventory, delegate);
        buildBlockEntityContainer(playerInventory, inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(Inventory playerInventory, Container inventory) {
        this.addSlot(new ExtendedSlot(inventory, 0, 29, 18, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 1, 47, 18, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 2, 65, 18, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 3, 42, 48, StoveGuiHandler::isFuel));

        this.addSlot(new StoveOutputSlot(playerInventory.player, inventory, 4, 126,  42));
    }

    private void buildPlayerContainer(Inventory playerInventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private boolean isIngredient(ItemStack stack) {
        return this.world.getRecipeManager().getAllRecipesFor(VineryRecipeTypes.WOOD_FIRED_OVEN_RECIPE_TYPE.get()).stream().anyMatch(recipe -> recipe.getIngredients().stream().anyMatch(x -> x.test(stack)));
    }
    private static boolean isFuel(ItemStack stack) {
        return AbstractFurnaceBlockEntity.isFuel(stack);
    }

    public int getScaledProgress(int arrowWidth) {
        final int progress = this.propertyDelegate.get(2);
        final int totalProgress = this.propertyDelegate.get(3);
        if (progress == 0) {
            return 0;
        }
        return progress * arrowWidth/ totalProgress + 1;
    }

    public boolean isBeingBurned() {
        return propertyDelegate.get(1) != 0;
    }

    @Override
    public List<IRecipeBookGroup> getGroups() {
        return WoodFiredOvenRecipeBookGroup.WOOD_FIRED_OVEN_GROUPS;
    }

    @Override
    public boolean hasIngredient(Recipe<?> recipe) {
        if (recipe instanceof WoodFiredOvenRecipe woodFiredOvenRecipe) {
            for (Ingredient ingredient : woodFiredOvenRecipe.getIngredients()) {
                boolean found = false;
                for (Slot slot : this.slots) {
                    if (ingredient.test(slot.getItem())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int getCraftingSlotCount() {
        return 5;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return null;
    }
}
