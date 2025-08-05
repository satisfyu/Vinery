package net.satisfy.vinery.core.util;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ImplementedInventory extends WorldlyContainer {
    NonNullList<ItemStack> getItems();

    static ImplementedInventory of(NonNullList<ItemStack> items) {
        return () -> {
            return items;
        };
    }

    static ImplementedInventory ofSize(int size) {
        return of(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    default void setChanged() {
    }

    default int getContainerSize() {
        return this.getItems().size();
    }

    default boolean isEmpty() {
        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack stack = this.getItem(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    default @NotNull ItemStack getItem(int slot) {
        return (ItemStack)this.getItems().get(slot);
    }

    default @NotNull ItemStack removeItem(int slot, int count) {
        ItemStack result = ContainerHelper.removeItem(this.getItems(), slot, count);
        if (!result.isEmpty()) {
            this.setChanged();
        }

        return result;
    }

    default @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.getItems(), slot);
    }

    default void setItem(int slot, ItemStack stack) {
        this.getItems().set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    default void clearContent() {
        this.getItems().clear();
    }

    default int @NotNull [] getSlotsForFace(Direction side) {
        int[] result = new int[this.getItems().size()];

        for(int i = 0; i < result.length; result[i] = i++) {
        }

        return result;
    }

    default boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        return true;
    }

    default boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return true;
    }

    default boolean stillValid(Player player) {
        return true;
    }
}

