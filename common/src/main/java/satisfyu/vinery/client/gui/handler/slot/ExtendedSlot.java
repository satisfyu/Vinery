package satisfyu.vinery.client.gui.handler.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class ExtendedSlot extends Slot {
	private final Predicate<ItemStack> filter;

	public ExtendedSlot(Container inventory, int index, int x, int y) {
		this(inventory, index, x, y, stack -> true);
	}

	public ExtendedSlot(Container inventory, int index, int x, int y, Predicate<ItemStack> filter) {
		super(inventory, index, x, y);
		this.filter = filter;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return this.filter.test(stack);
	}
}
