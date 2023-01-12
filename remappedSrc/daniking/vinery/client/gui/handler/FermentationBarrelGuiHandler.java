package daniking.vinery.client.gui.handler;

import daniking.vinery.client.gui.handler.slot.ExtendedSlot;
import daniking.vinery.client.gui.handler.slot.StoveOutputSlot;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VineryScreenHandlerTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FermentationBarrelGuiHandler extends AbstractContainerMenu  {

    private final ContainerData delegate;


    public FermentationBarrelGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(6), new SimpleContainerData(2));
    }
    public FermentationBarrelGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(VineryScreenHandlerTypes.FERMENTATION_BARREL_GUI_HANDLER, syncId);

        // Wine input
        this.addSlot(new ExtendedSlot(inventory, 0, 63, 50, stack -> {
            return stack.is(Item.byBlock(ObjectRegistry.WINE_BOTTLE));
        }));
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
        addDataSlots(this.delegate);
    }
    public int getCookProgress() {
        int i = this.delegate.get(0);
        int j = this.delegate.get(1);
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 24 / j;
    }
    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
