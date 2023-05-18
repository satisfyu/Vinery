package satisfyu.vinery.client.gui.handler;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import satisfyu.vinery.client.recipebook.AbstractPrivateRecipeScreenHandler;

public abstract class AbstractRecipeBookGUIScreenHandler extends AbstractPrivateRecipeScreenHandler {
    protected final Container inventory;
    protected final ContainerData propertyDelegate;
    protected final Level world;
    private final int inputSlots;

    protected AbstractRecipeBookGUIScreenHandler(MenuType<?> screenHandler, int syncId, int inputSlots, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(screenHandler, syncId);
        this.world = playerInventory.player.level;
        this.inventory = inventory;
        this.inputSlots = inputSlots;
        this.propertyDelegate = propertyDelegate;
        addDataSlots(this.propertyDelegate);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        final int entityInputStart = 0;
        int entityOutputSlot = this.inputSlots;
        final int inventoryStart = entityOutputSlot + 1;
        final int hotbarStart = inventoryStart + 9 * 3;
        final int hotbarEnd = hotbarStart + 9;


        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (invSlot == entityOutputSlot) {
                item.onCraftedBy(itemStack2, player.level, player);
                if (!this.moveItemStackTo(itemStack2, inventoryStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (invSlot >= entityInputStart && invSlot < entityOutputSlot ? !this.moveItemStackTo(itemStack2, inventoryStart, hotbarEnd, false) :
                    !this.moveItemStackTo(itemStack2, entityInputStart, entityOutputSlot, false) && (invSlot >= inventoryStart && invSlot < hotbarStart ? !this.moveItemStackTo(itemStack2, hotbarStart, hotbarEnd, false) :
                            invSlot >= hotbarStart && invSlot < hotbarEnd && !this.moveItemStackTo(itemStack2, inventoryStart, hotbarStart, false))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }
            slot.setChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemStack2);
            this.broadcastChanges();
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }
}
