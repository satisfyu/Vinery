package satisfyu.vinery.screen.sideTip;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class RecipeGUIHandler extends ScreenHandler {
    public final World world;
    public final Inventory inventory;
    public final PropertyDelegate propertyDelegate;
    private final int inputSlots;

    public RecipeGUIHandler(ScreenHandlerType<?> screenHandler, int syncId, PlayerInventory playerInventory, Inventory inventory, int inputSlots, PropertyDelegate propertyDelegate) {
        super(screenHandler, syncId);
        this.world = playerInventory.player.world;
        this.inventory = inventory;
        this.inputSlots = inputSlots;
        this.propertyDelegate = propertyDelegate;
        addProperties(this.propertyDelegate);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        final int entityInputStart = 0;
        int entityOutputSlot = this.inputSlots;
        final int inventoryStart = entityOutputSlot + 1;
        final int hotbarStart = inventoryStart + 9 * 3;
        final int hotbarEnd = hotbarStart + 9;


        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (invSlot == entityOutputSlot) {
                item.onCraft(itemStack2, player.world, player);
                if (!this.insertItem(itemStack2, inventoryStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (invSlot >= entityInputStart && invSlot < entityOutputSlot ? !this.insertItem(itemStack2, inventoryStart, hotbarEnd, false) :
                    !this.insertItem(itemStack2, entityInputStart, entityOutputSlot, false) && (invSlot >= inventoryStart && invSlot < hotbarStart ? !this.insertItem(itemStack2, hotbarStart, hotbarEnd, false) :
                            invSlot >= hotbarStart && invSlot < hotbarEnd && !this.insertItem(itemStack2, inventoryStart, hotbarStart, false))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            }
            slot.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
            this.sendContentUpdates();
        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
