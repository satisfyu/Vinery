package daniking.vinery.client.gui.handler;

import daniking.vinery.client.gui.handler.slot.ExtendedSlot;
import daniking.vinery.client.gui.handler.slot.StoveOutputSlot;
import daniking.vinery.registry.VineryRecipeTypes;
import daniking.vinery.registry.VineryScreenHandlerTypes;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class StoveGuiHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate delegate;

    public static final int FUEL_SLOT = 0;
    public static final int BUCKET_SLOT = 1;
    public static final int INGREDIENT_SLOT = 2;
    public static final int OUTPUT_SLOT = 3;
    private final World world;
    public StoveGuiHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(4));
    }

    public StoveGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, syncId);
        this.inventory = inventory;
        this.world = playerInventory.player.world;
        // Fuel
        this.addSlot(new ExtendedSlot(this.inventory, 0, 56, 53, StoveGuiHandler::isFuel));
        // Bucket
        this.addSlot(new ExtendedSlot(this.inventory, 1, 44, 17, stack -> stack.isOf(Items.WATER_BUCKET)));
        // Ingredient
        this.addSlot(new ExtendedSlot(this.inventory, 2, 67, 17, this::isIngredient));
        // Output
        this.addSlot(new StoveOutputSlot(playerInventory.player, this.inventory, 3, 116,  35));

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
        this.addProperties(this.delegate);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
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
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack stack;
        final Slot slot = this.getSlot(index);
        if (slot != null && slot.hasStack()) {
            final ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();
            if (index >= 0 && index <= OUTPUT_SLOT) {
                if (!this.insertItem(stackInSlot, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(stackInSlot, stack);
            } else {
                // Ingredient
                if (this.isIngredient(stackInSlot)) {
                    if (!this.insertItem(stackInSlot, INGREDIENT_SLOT, OUTPUT_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // Bucket
                if (stackInSlot.getItem() == Items.WATER_BUCKET) {
                    if (!this.insertItem(stackInSlot, BUCKET_SLOT, INGREDIENT_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // Fuel
                if (isFuel(stackInSlot)) {
                    if (!this.insertItem(stackInSlot, FUEL_SLOT, BUCKET_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            if (stackInSlot.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (stackInSlot.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, stackInSlot);
        }
        return ItemStack.EMPTY;
    }

    private boolean isIngredient(ItemStack stack) {
        return this.world.getRecipeManager().listAllOfType(VineryRecipeTypes.STOVE_RECIPE_TYPE).stream().anyMatch(recipe -> recipe.getInput().test(stack));
    }
    private static boolean isFuel(ItemStack stack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(stack);
    }
}
