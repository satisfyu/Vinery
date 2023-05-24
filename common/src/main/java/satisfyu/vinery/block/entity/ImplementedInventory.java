package satisfyu.vinery.block.entity;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ImplementedInventory extends WorldlyContainer {
	static ImplementedInventory of(NonNullList<ItemStack> items) {
		return () -> items;
	}

	static ImplementedInventory ofSize(int size) {
		return of(NonNullList.withSize(size, ItemStack.EMPTY));
	}

	NonNullList<ItemStack> getItems();

	@Override
	default int[] getSlotsForFace(Direction side) {
		int[] result = new int[getItems().size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = i;
		}
		return result;
	}

	@Override
	default boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
		return true;
	}

	@Override
	default boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
		return true;
	}

	@Override
	default int getContainerSize() {
		return getItems().size();
	}

	@Override
	default boolean isEmpty() {
		for (int i = 0; i < getContainerSize(); i++) {
			ItemStack stack = getItem(i);
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	default ItemStack getItem(int slot) {
		return getItems().get(slot);
	}

	@Override
	default ItemStack removeItem(int slot, int count) {
		ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);
		if (!result.isEmpty()) {
			setChanged();
		}
		return result;
	}

	@Override
	default ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(getItems(), slot);
	}

	@Override
	default void setItem(int slot, ItemStack stack) {
		getItems().set(slot, stack);
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}
	}

	@Override
	default void clearContent() {
		getItems().clear();
	}

	@Override
	default void setChanged() {
	}

	@Override
	default boolean stillValid(Player player) {
		return true;
	}
}
