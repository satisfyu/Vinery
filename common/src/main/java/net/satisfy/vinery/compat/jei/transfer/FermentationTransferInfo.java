package net.satisfy.vinery.compat.jei.transfer;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.satisfy.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.satisfy.vinery.compat.jei.category.FermentationBarrelCategory;
import net.satisfy.vinery.recipe.FermentationBarrelRecipe;
import net.satisfy.vinery.registry.ScreenhandlerTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FermentationTransferInfo implements IRecipeTransferInfo<FermentationBarrelGuiHandler, FermentationBarrelRecipe> {
    @Override
    public @NotNull Class<? extends FermentationBarrelGuiHandler> getContainerClass() {
        return FermentationBarrelGuiHandler.class;
    }

    @Override
    public @NotNull Optional<MenuType<FermentationBarrelGuiHandler>> getMenuType() {
        return Optional.of(ScreenhandlerTypeRegistry.FERMENTATION_BARREL_GUI_HANDLER.get());
    }

    @Override
    public @NotNull RecipeType<FermentationBarrelRecipe> getRecipeType() {
        return FermentationBarrelCategory.FERMENTATION_BARREL;
    }

    @Override
    public boolean canHandle(FermentationBarrelGuiHandler container, FermentationBarrelRecipe recipe) {
        return true;
    }

    @Override
    public @NotNull List<Slot> getRecipeSlots(FermentationBarrelGuiHandler container, FermentationBarrelRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        slots.add(container.getSlot(0));
        for(int i = 1; i <= recipe.getIngredients().size() && i < 5; i++){
            slots.add(container.getSlot(i));
        }
        return slots;
    }

    @Override
    public @NotNull List<Slot> getInventorySlots(FermentationBarrelGuiHandler container, FermentationBarrelRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 6; i < 6 + 36; i++) {
            Slot slot = container.getSlot(i);
            slots.add(slot);
        }
        return slots;
    }
}
