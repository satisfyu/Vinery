package daniking.vinery.client.gui.handler;

import daniking.vinery.registry.VineryScreenHandlerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CookingPotGuiHandler extends ScreenHandler {

    private final PropertyDelegate propertyDelegate;

    public CookingPotGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(8), new ArrayPropertyDelegate(2));
    }

    public CookingPotGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(VineryScreenHandlerTypes.COOKING_POT_SCREEN_HANDLER, syncId);
        buildBlockEntityContainer(inventory);
        buildPlayerContainer(playerInventory);
        this.propertyDelegate = propertyDelegate;
        addProperties(this.propertyDelegate);
    }
    private void buildBlockEntityContainer(Inventory inventory) {
        for (int row = 0; row < 2; row++) {
            for (int slot = 0; slot < 3; slot++) {
                this.addSlot(new Slot(inventory, slot + row + (row * 2), 30 + (slot * 18), 17 + (row * 18)));
            }
        }
        this.addSlot(new Slot(inventory, 6,92, 55));
        this.addSlot(new Slot(inventory, 7, 124, 28));
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

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

}
