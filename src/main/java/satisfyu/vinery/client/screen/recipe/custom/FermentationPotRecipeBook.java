package satisfyu.vinery.client.screen.recipe.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.client.recipebook.PrivateRecipeBookWidget;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VineryRecipeTypes;

import java.util.List;

public class FermentationPotRecipeBook extends PrivateRecipeBookWidget {
    private static final Text TOGGLE_FERMENTABLE_TEXT;

    public FermentationPotRecipeBook() {
    }

    @Override
    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        this.ghostSlots.addSlot(recipe.getOutput(this.client.world.getRegistryManager()), slots.get(5).x, slots.get(5).y);
        this.ghostSlots.addSlot(ObjectRegistry.WINE_BOTTLE.asItem().getDefaultStack(), slots.get(0).x, slots.get(0).y);
        int j = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            ItemStack[] inputStacks = ingredient.getMatchingStacks();
            ItemStack inputStack = inputStacks[Random.create().nextBetween(0, inputStacks.length - 1)];
            this.ghostSlots.addSlot(inputStack, slots.get(j).x, slots.get(j++).y);
        }
    }

    @Override
    public void insertRecipe(Recipe<?> recipe) {
        if (recipe instanceof FermentationBarrelRecipe) {
            int slotIndex = 0;
            for (Slot slot : screenHandler.slots) {
                if (slot.getStack().getItem() == ObjectRegistry.WINE_BOTTLE.asItem()) {
                    MinecraftClient.getInstance().interactionManager.clickSlot(screenHandler.syncId, slotIndex, 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
                    MinecraftClient.getInstance().interactionManager.clickSlot(screenHandler.syncId, 0, 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
                    break;
                }
                ++slotIndex;
            }
        }
        int usedInputSlots = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            int slotIndex = 0;
            for (Slot slot : screenHandler.slots) {
                ItemStack itemStack = slot.getStack();

                if (ingredient.test(itemStack) && usedInputSlots < 5) {
                    MinecraftClient.getInstance().interactionManager.clickSlot(screenHandler.syncId, slotIndex, 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
                    MinecraftClient.getInstance().interactionManager.clickSlot(screenHandler.syncId, usedInputSlots, 0, SlotActionType.PICKUP, MinecraftClient.getInstance().player);
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
        if (slot != null && slot.id < this.screenHandler.getCraftingSlotCount()) {
            this.ghostSlots.reset();
        }
    }

    @Override
    protected RecipeType<? extends Recipe<Inventory>> getRecipeType() {
        return VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_TYPE;
    }

    @Override
    protected Text getToggleCraftableButtonText() {
        return TOGGLE_FERMENTABLE_TEXT;
    }

    static {
        TOGGLE_FERMENTABLE_TEXT = Text.translatable("gui.vinery.recipebook.toggleRecipes.fermentable");
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }
}
