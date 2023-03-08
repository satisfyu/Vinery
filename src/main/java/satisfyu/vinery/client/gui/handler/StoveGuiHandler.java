package satisfyu.vinery.client.gui.handler;

import net.minecraft.screen.slot.FurnaceOutputSlot;
import satisfyu.vinery.client.gui.handler.slot.ExtendedSlot;
import satisfyu.vinery.client.gui.handler.slot.StoveOutputSlot;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.registry.VineryScreenHandlerTypes;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
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
import satisfyu.vinery.screen.sideTip.RecipeGUIHandler;

public class StoveGuiHandler extends RecipeGUIHandler {
    public static final int FUEL_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;
    public StoveGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(4));
    }

    public StoveGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, syncId, playerInventory, inventory, 4, delegate);
        buildBlockEntityContainer(playerInventory, inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(PlayerInventory playerInventory, Inventory inventory) {
        this.addSlot(new ExtendedSlot(inventory, 0, 38, 17, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 1, 56, 17, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 2, 74, 17, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 3, 56, 53, StoveGuiHandler::isFuel));

        this.addSlot(new StoveOutputSlot(playerInventory.player, inventory, 4, 116,  35));
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

    public int getCookProgress() {
        int i = this.propertyDelegate.get(2);
        int j = this.propertyDelegate.get(3);
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 24 / j;
    }

    public boolean isBurning() {
        return this.propertyDelegate.get(0) > 0;
    }

    private boolean isIngredient(ItemStack stack) {
        return this.world.getRecipeManager().listAllOfType(VineryRecipeTypes.WOOD_FIRED_OVEN_RECIPE_TYPE).stream().anyMatch(recipe -> recipe.getInputs().stream().anyMatch(x -> x.test(stack)));
    }
    private static boolean isFuel(ItemStack stack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(stack);
    }

    public int getFuelProgress() {
        int i = this.propertyDelegate.get(1);
        if (i == 0) {
            i = 200;
        }
        return this.propertyDelegate.get(0) * 13 / i;
    }
}
