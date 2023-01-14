package satisfyu.vinery.client.gui.handler;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import satisfyu.vinery.recipe.CookingPotRecipe;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.registry.VineryScreenHandlerTypes;
import satisfyu.vinery.util.VineryUtils;

import java.util.stream.Stream;

public class CookingPotGuiHandler extends ScreenHandler {

    private final PropertyDelegate propertyDelegate;
    private final World world;
    private boolean isBeingBurned;
    public CookingPotGuiHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf packet) {
        this(syncId, playerInventory, new SimpleInventory(8), new ArrayPropertyDelegate(2));
        this.isBeingBurned = packet.readBoolean();
    }

    public CookingPotGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(VineryScreenHandlerTypes.COOKING_POT_SCREEN_HANDLER, syncId);
        buildBlockEntityContainer(inventory);
        buildPlayerContainer(playerInventory);
        this.world = playerInventory.player.getWorld();
        this.propertyDelegate = propertyDelegate;
        addProperties(this.propertyDelegate);
    }

    private void buildBlockEntityContainer(Inventory inventory) {
        for (int row = 0; row < 2; row++) {
            for (int slot = 0; slot < 3; slot++) {
                this.addSlot(new Slot(inventory, slot + row + (row * 2), 30 + (slot * 18), 17 + (row * 18)));
            }
        }
        this.addSlot(new Slot(inventory, 6,92, 55));
        this.addSlot(new Slot(inventory, 7, 124, 28) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
    }

    private void buildPlayerContainer(PlayerInventory playerInventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public boolean isBeingBurned() {
        return isBeingBurned;
    }


    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack stack;
        final Slot slot = this.getSlot(index);
        if (slot != null && slot.hasStack()) {
            final ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();
            if (VineryUtils.isIndexInRange(index, 0, 7)) {
                if (!this.insertItem(stackInSlot, 8, 43, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(stackInSlot, stack);

            } else if (isItemIngredient(stackInSlot)) {
                if (!this.insertItem(stackInSlot, 0, 5, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (isItemContainer(stack)) {
                if (!this.insertItem(stackInSlot, 6, 7, false)) {
                    return ItemStack.EMPTY;
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

    private boolean isItemIngredient(ItemStack stack) {
        return recipeStream().anyMatch(cookingPotRecipe -> cookingPotRecipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }

    private Stream<CookingPotRecipe> recipeStream() {
        return this.world.getRecipeManager().listAllOfType(VineryRecipeTypes.COOKING_POT_RECIPE_TYPE).stream();
    }
    private boolean isItemContainer(ItemStack stack) {
        return recipeStream().anyMatch(cookingPotRecipe -> cookingPotRecipe.getContainer().isOf(stack.getItem()));
    }

    public int getScaledProgress() {
        final int progress = this.propertyDelegate.get(0);
        final int totalProgress = this.propertyDelegate.get(1);
        if (totalProgress == 0 || progress == 0) {
            return 0;
        }
        return progress * 22 / totalProgress;
    }


}