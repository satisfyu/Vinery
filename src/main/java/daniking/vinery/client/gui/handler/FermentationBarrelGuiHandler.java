package daniking.vinery.client.gui.handler;

import daniking.vinery.block.entity.FermentationBarrelBlockEntity;
import daniking.vinery.client.gui.handler.slot.StoveOutputSlot;
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

public class FermentationBarrelGuiHandler extends ScreenHandler  {

    private final PropertyDelegate delegate;


    public FermentationBarrelGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(FermentationBarrelBlockEntity.CAPACITY), new ArrayPropertyDelegate(4));
    }
    public FermentationBarrelGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(VineryScreenHandlerTypes.FERMENTATION_BARREL_GUI_HANDLER, syncId);

        // Wine input
        this.addSlot(new Slot(inventory, 0, 63, 50));
        // Output
        this.addSlot(new StoveOutputSlot(playerInventory.player, inventory, 1, 125,  35));
        // Inputs
        this.addSlot(new Slot(inventory, 2, 18, 26));
        this.addSlot(new Slot(inventory, 3, 36, 26));
        this.addSlot(new Slot(inventory, 4, 18, 44));
        this.addSlot(new Slot(inventory, 5, 36, 44));
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.delegate = propertyDelegate;
        addProperties(delegate);
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
