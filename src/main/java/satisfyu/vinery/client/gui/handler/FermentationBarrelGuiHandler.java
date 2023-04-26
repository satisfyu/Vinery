package satisfyu.vinery.client.gui.handler;

import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import satisfyu.vinery.client.gui.handler.slot.ExtendedSlot;
import satisfyu.vinery.client.gui.handler.slot.StoveOutputSlot;
import satisfyu.vinery.client.recipebook.IRecipeBookGroup;
import satisfyu.vinery.client.recipebook.custom.FermentationBarrelRecipeBookGroup;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.registry.VineryScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public class FermentationBarrelGuiHandler extends AbstractRecipeBookGUIScreenHandler {

    public FermentationBarrelGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(2));
    }
    public FermentationBarrelGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(VineryScreenHandlerTypes.FERMENTATION_BARREL_GUI_HANDLER, syncId, 5, playerInventory, inventory, propertyDelegate);
        buildBlockEntityContainer(playerInventory, inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(PlayerInventory playerInventory, Inventory inventory) {
        // Wine input
        this.addSlot(new ExtendedSlot(inventory, 0, 79, 51, stack -> stack.isOf(ObjectRegistry.WINE_BOTTLE)));
        // Inputs
        this.addSlot(new ExtendedSlot(inventory, 1, 33, 26, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 2, 51, 26, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 3, 33, 44, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 4, 51, 44, this::isIngredient));
        // Output
        this.addSlot(new StoveOutputSlot(playerInventory.player, inventory, 5, 128,  35));
    }

    private void buildPlayerContainer(PlayerInventory playerInventory) {
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
        return this.world.getRecipeManager().listAllOfType(VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_TYPE).stream().anyMatch(recipe -> recipe.getIngredients().stream().anyMatch(x -> x.test(stack)));
    }

    public int getScaledProgress(int arrowWidth) {
        final int progress = this.propertyDelegate.get(0);
        final int totalProgress = this.propertyDelegate.get(1);
        if (progress == 0) {
            return 0;
        }

        System.out.println(progress * arrowWidth/ totalProgress + 1);
        return progress * arrowWidth/ totalProgress + 1;
    }

    @Override
    public List<IRecipeBookGroup> getGroups() {
        return FermentationBarrelRecipeBookGroup.FERMENTATION_GROUPS;
    }

    @Override
    public boolean hasIngredient(Recipe<?> recipe) {
        if (recipe instanceof FermentationBarrelRecipe fermentationBarrelRecipe) {
            for (Ingredient ingredient : fermentationBarrelRecipe.getIngredients()) {
                boolean found = false;
                for (Slot slot : this.slots) {
                    if (ingredient.test(slot.getStack())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            for (Slot slot : this.slots) {
                if (slot.getStack().getItem() == ObjectRegistry.WINE_BOTTLE.asItem()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getCraftingSlotCount() {
        return 5;
    }
}
