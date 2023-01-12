package daniking.vinery.client.gui.handler;

import daniking.vinery.client.gui.handler.slot.ExtendedSlot;
import daniking.vinery.client.gui.handler.slot.StoveOutputSlot;
import daniking.vinery.registry.VineryRecipeTypes;
import daniking.vinery.registry.VineryScreenHandlerTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class StoveGuiHandler extends AbstractContainerMenu {

    private final Container inventory;
    private final ContainerData delegate;

    public static final int FUEL_SLOT = 3;
    public static final int INGREDIENT_SLOT_START_INDEX = 0;
    public static final int OUTPUT_SLOT = 4;
    private final Level world;
    public StoveGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(5), new SimpleContainerData(4));
    }

    public StoveGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData delegate) {
        super(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, syncId);
        this.inventory = inventory;
        this.world = playerInventory.player.level;


        this.addSlot(new ExtendedSlot(this.inventory, 0, 38, 17, this::isIngredient));
        this.addSlot(new ExtendedSlot(this.inventory, 1, 56, 17, this::isIngredient));
        this.addSlot(new ExtendedSlot(this.inventory, 2, 74, 17, this::isIngredient));
        this.addSlot(new ExtendedSlot(this.inventory, 3, 56, 53, StoveGuiHandler::isFuel));

        this.addSlot(new StoveOutputSlot(playerInventory.player, this.inventory, 4, 116,  35));

        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.delegate = delegate;
        this.addDataSlots(this.delegate);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }
    public int getCookProgress() {
        int i = this.delegate.get(2);
        int j = this.delegate.get(3);
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 24 / j;
    }

    public int getFuelProgress() {
        int i = this.delegate.get(1);
        if (i == 0) {
            i = 200;
        }
        return this.delegate.get(0) * 13 / i;
    }

    public boolean isBurning() {
        return this.delegate.get(0) > 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack;
        final Slot slot = this.getSlot(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack stackInSlot = slot.getItem();
            stack = stackInSlot.copy();
            if (index >= 0 && index <= OUTPUT_SLOT) {
                if (!this.moveItemStackTo(stackInSlot, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stackInSlot, stack);
            } else {
                // Ingredient
                if (this.isIngredient(stackInSlot)) {
                    if (!this.moveItemStackTo(stackInSlot, INGREDIENT_SLOT_START_INDEX, OUTPUT_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // Fuel
                if (isFuel(stackInSlot)) {
                    if (!this.moveItemStackTo(stackInSlot, 2, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (stackInSlot.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stackInSlot);
        }
        return ItemStack.EMPTY;
    }

    private boolean isIngredient(ItemStack stack) {
        return this.world.getRecipeManager().getAllRecipesFor(VineryRecipeTypes.WOOD_FIRED_OVEN_RECIPE_TYPE).stream().anyMatch(recipe -> recipe.getInputs().stream().anyMatch(x -> x.test(stack)));
    }
    private static boolean isFuel(ItemStack stack) {
        return AbstractFurnaceBlockEntity.isFuel(stack);
    }
}
