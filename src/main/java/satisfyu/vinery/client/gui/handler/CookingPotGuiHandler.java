package satisfyu.vinery.client.gui.handler;

import satisfyu.vinery.block.entity.CookingPotEntity;
import satisfyu.vinery.block.entity.FermentationBarrelBlockEntity;
import satisfyu.vinery.compat.farmersdelight.FarmersCookingPot;
import satisfyu.vinery.recipe.CookingPotRecipe;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.registry.VineryScreenHandlerTypes;
import satisfyu.vinery.screen.sideTip.RecipeGUIHandler;
import satisfyu.vinery.util.VineryUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class CookingPotGuiHandler extends RecipeGUIHandler {

    public CookingPotGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(8), new ArrayPropertyDelegate(2));
    }

    public CookingPotGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(VineryScreenHandlerTypes.COOKING_POT_SCREEN_HANDLER, syncId, playerInventory, inventory, 6, propertyDelegate);
        buildBlockEntityContainer(inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(Inventory inventory) {
        for (int row = 0; row < 2; row++) {
            for (int slot = 0; slot < 3; slot++) {
                this.addSlot(new Slot(inventory, slot + row + (row * 2), 30 + (slot * 18), 17 + (row * 18)));
            }
        }
        this.addSlot(new Slot(inventory, 6,92, 55));
        this.addSlot(new Slot(inventory, 7, 124, 28) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
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

    //Maybe Use later
    private boolean isItemIngredient(ItemStack stack) {
        if(VineryUtils.isFDLoaded()){
            if(FarmersCookingPot.isItemIngredient(stack, this.world)){
                return true;
            }
        }
        return recipeStream().anyMatch(cookingPotRecipe -> cookingPotRecipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }
    private Stream<CookingPotRecipe> recipeStream() {
        return this.world.getRecipeManager().listAllOfType(VineryRecipeTypes.COOKING_POT_RECIPE_TYPE).stream();
    }

    public int getScaledProgress(int arrowWidth) {
        final int progress = this.propertyDelegate.get(0);
        final int totalProgress = CookingPotEntity.MAX_COOKING_TIME;
        if (progress == 0) {
            return 0;
        }
        return progress * arrowWidth/ totalProgress + 1;
    }
}
