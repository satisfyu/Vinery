package daniking.vinery.client.gui.handler;

import daniking.vinery.block.entity.CookingPotEntity;
import daniking.vinery.compat.farmersdelight.FarmersCookingPot;
import daniking.vinery.recipe.CookingPotRecipe;
import daniking.vinery.registry.VineryRecipeTypes;
import daniking.vinery.registry.VineryScreenHandlerTypes;
import daniking.vinery.util.VineryUtils;
import java.util.stream.Stream;
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

public class CookingPotGuiHandler extends AbstractContainerMenu {

    private final ContainerData propertyDelegate;
    private final Level world;
    public CookingPotGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(8), new SimpleContainerData(2));
    }

    public CookingPotGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(VineryScreenHandlerTypes.COOKING_POT_SCREEN_HANDLER, syncId);
        buildBlockEntityContainer(inventory);
        buildPlayerContainer(playerInventory);
        this.world = playerInventory.player.getLevel();
        this.propertyDelegate = propertyDelegate;
        addDataSlots(this.propertyDelegate);
    }

    private void buildBlockEntityContainer(Container inventory) {
        for (int row = 0; row < 2; row++) {
            for (int slot = 0; slot < 3; slot++) {
                this.addSlot(new Slot(inventory, slot + row + (row * 2), 30 + (slot * 18), 17 + (row * 18)));
            }
        }
        this.addSlot(new Slot(inventory, 6,92, 55));
        this.addSlot(new Slot(inventory, 7, 124, 28) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
    }

    private void buildPlayerContainer(Inventory playerInventory) {
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
    public boolean stillValid(Player player) {
        return true;
    }

    public boolean isBeingBurned() {
        return propertyDelegate.get(1) != 0;
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack;
        final Slot slot = this.getSlot(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack stackInSlot = slot.getItem();
            stack = stackInSlot.copy();
            if (VineryUtils.isIndexInRange(index, 0, 7)) {
                if (!this.moveItemStackTo(stackInSlot, 8, 43, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stackInSlot, stack);

            } else if (isItemIngredient(stackInSlot)) {
                if (!this.moveItemStackTo(stackInSlot, 0, 5, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (isItemContainer(stack)) {
                if (!this.moveItemStackTo(stackInSlot, 6, 7, false)) {
                    return ItemStack.EMPTY;
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

    private boolean isItemIngredient(ItemStack stack) {
        if(VineryUtils.isFDLoaded()){
            if(FarmersCookingPot.isItemIngredient(stack, this.world)){
                return true;
            }
        }
        return recipeStream().anyMatch(cookingPotRecipe -> cookingPotRecipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }

    private Stream<CookingPotRecipe> recipeStream() {
        return this.world.getRecipeManager().getAllRecipesFor(VineryRecipeTypes.COOKING_POT_RECIPE_TYPE).stream();
    }
    private boolean isItemContainer(ItemStack stack) {
        if(VineryUtils.isFDLoaded()){
            if(FarmersCookingPot.isItemContainer(stack, this.world)){
                return true;
            }
        }
        return recipeStream().anyMatch(cookingPotRecipe -> cookingPotRecipe.getContainer().is(stack.getItem()));
    }

    public int getScaledProgress() {
        final int progress = this.propertyDelegate.get(0);
        final int totalProgress = CookingPotEntity.MAX_COOKING_TIME;
        if (progress == 0) {
            return 0;
        }
        return progress * 22 / totalProgress;
    }


}
