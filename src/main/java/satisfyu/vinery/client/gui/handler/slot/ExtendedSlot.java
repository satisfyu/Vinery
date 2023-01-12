package satisfyu.vinery.client.gui.handler.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class ExtendedSlot extends Slot {

    private final Predicate<ItemStack> filter;

    public ExtendedSlot(Inventory inventory, int index, int x, int y) {
        this(inventory, index, x, y, stack -> true);
    }

    public ExtendedSlot(Inventory inventory, int index, int x, int y, Predicate<ItemStack> filter) {
        super(inventory, index, x, y);
        this.filter = filter;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return this.filter.test(stack);
    }
}
