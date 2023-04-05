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
import satisfyu.vinery.recipe.CookingPotRecipe;
import satisfyu.vinery.registry.VineryRecipeTypes;

import java.util.List;

public class CookingPotRecipeBook extends PrivateRecipeBookWidget {
    private static final Text TOGGLE_COOKABLE_TEXT;

    public CookingPotRecipeBook() {
    }

    @Override
    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        this.ghostSlots.addSlot(recipe.getOutput(), slots.get(7).x, slots.get(7).y);
        if (recipe instanceof CookingPotRecipe cookingPotRecipe) {
            this.ghostSlots.addSlot(cookingPotRecipe.getContainer(), slots.get(0).x, slots.get(0).y);
        }
        int j = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            ItemStack[] inputStacks = ingredient.getMatchingStacks();
            ItemStack inputStack = inputStacks[Random.create().nextBetween(0, inputStacks.length - 1)];
            this.ghostSlots.addSlot(inputStack, slots.get(j).x, slots.get(j++).y);
        }
    }

    @Override
    public void insertRecipe(Recipe<?> recipe) {
        if (recipe instanceof CookingPotRecipe cookingPanRecipe) {
            int slotIndex = 0;
            for (Slot slot : screenHandler.slots) {
                if (cookingPanRecipe.getContainer().getItem() == slot.getStack().getItem()) {
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

                if (ingredient.test(itemStack) && usedInputSlots < 7) {
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
    protected void setCraftableButtonTexture() {
        this.toggleCraftableButton.setTextureUV(152, 41, 28, 18, TEXTURE);
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
        return VineryRecipeTypes.COOKING_POT_RECIPE_TYPE;
    }

    @Override
    protected Text getToggleCraftableButtonText() {
        return TOGGLE_COOKABLE_TEXT;
    }

    static {
        TOGGLE_COOKABLE_TEXT = Text.translatable("gui.vinery.recipebook.toggleRecipes.cookable");
    }
}
