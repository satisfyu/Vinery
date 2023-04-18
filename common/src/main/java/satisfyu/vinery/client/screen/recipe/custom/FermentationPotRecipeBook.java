package satisfyu.vinery.client.screen.recipe.custom;

import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.client.recipebook.PrivateRecipeBookWidget;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VineryRecipeTypes;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class FermentationPotRecipeBook extends PrivateRecipeBookWidget {
    private static final Component TOGGLE_FERMENTABLE_TEXT;

    public FermentationPotRecipeBook() {
    }

    @Override
    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        this.ghostSlots.addSlot(recipe.getResultItem(this.client.level.registryAccess()), slots.get(5).x, slots.get(5).y);
        this.ghostSlots.addSlot(ObjectRegistry.WINE_BOTTLE.get().asItem().getDefaultInstance(), slots.get(0).x, slots.get(0).y);
        int j = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            ItemStack[] inputStacks = ingredient.getItems();
            ItemStack inputStack = inputStacks[RandomSource.create().nextIntBetweenInclusive(0, inputStacks.length - 1)];
            this.ghostSlots.addSlot(inputStack, slots.get(j).x, slots.get(j++).y);
        }
    }

    @Override
    public void insertRecipe(Recipe<?> recipe) {
        if (recipe instanceof FermentationBarrelRecipe) {
            int slotIndex = 0;
            for (Slot slot : screenHandler.slots) {
                if (slot.getItem().getItem() == ObjectRegistry.WINE_BOTTLE.get().asItem()) {
                    Minecraft.getInstance().gameMode.handleInventoryMouseClick(screenHandler.containerId, slotIndex, 0, ClickType.PICKUP, Minecraft.getInstance().player);
                    Minecraft.getInstance().gameMode.handleInventoryMouseClick(screenHandler.containerId, 0, 0, ClickType.PICKUP, Minecraft.getInstance().player);
                    break;
                }
                ++slotIndex;
            }
        }
        int usedInputSlots = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            int slotIndex = 0;
            for (Slot slot : screenHandler.slots) {
                ItemStack itemStack = slot.getItem();

                if (ingredient.test(itemStack) && usedInputSlots < 5) {
                    Minecraft.getInstance().gameMode.handleInventoryMouseClick(screenHandler.containerId, slotIndex, 0, ClickType.PICKUP, Minecraft.getInstance().player);
                    Minecraft.getInstance().gameMode.handleInventoryMouseClick(screenHandler.containerId, usedInputSlots, 0, ClickType.PICKUP, Minecraft.getInstance().player);
                    ++usedInputSlots;
                    break;
                }
                ++slotIndex;
            }
        }
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < this.screenHandler.getCraftingSlotCount()) {
            this.ghostSlots.reset();
        }
    }

    @Override
    protected RecipeType<? extends Recipe<Container>> getRecipeType() {
        return VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_TYPE.get();
    }

    @Override
    protected Component getToggleCraftableButtonText() {
        return TOGGLE_FERMENTABLE_TEXT;
    }

    static {
        TOGGLE_FERMENTABLE_TEXT = Component.translatable("gui.vinery.recipebook.toggleRecipes.fermentable");
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }
}
