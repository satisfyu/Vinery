package net.satisfy.vinery.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.satisfy.vinery.client.gui.handler.ApplePressGuiHandler;
import net.satisfy.vinery.compat.jei.category.ApplePressCategory;
import net.satisfy.vinery.compat.jei.category.FermentationBarrelCategory;
import net.satisfy.vinery.compat.jei.transfer.FermentationTransferInfo;
import net.satisfy.vinery.recipe.ApplePressRecipe;
import net.satisfy.vinery.recipe.FermentationBarrelRecipe;
import net.satisfy.vinery.registry.ObjectRegistry;
import net.satisfy.vinery.registry.RecipeTypesRegistry;
import net.satisfy.vinery.registry.ScreenhandlerTypeRegistry;
import net.satisfy.vinery.util.VineryIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@JeiPlugin
public class VineryJEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FermentationBarrelCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ApplePressCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<RecipeHolder<FermentationBarrelRecipe>> fermentationHolderBarrelRecipes = rm.getAllRecipesFor(RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_TYPE.get());
        List<FermentationBarrelRecipe> fermentationBarrelRecipes = new ArrayList<>();
        fermentationHolderBarrelRecipes.iterator().forEachRemaining(recipeHolder -> fermentationBarrelRecipes.add(recipeHolder.value()));

        registration.addRecipes(FermentationBarrelCategory.FERMENTATION_BARREL, fermentationBarrelRecipes);

        List<RecipeHolder<ApplePressRecipe>> applePressHolderRecipes = rm.getAllRecipesFor(RecipeTypesRegistry.APPLE_PRESS_RECIPE_TYPE.get());
        List<ApplePressRecipe> applePressRecipes = new ArrayList<>();
        applePressHolderRecipes.iterator().forEachRemaining(recipeHolder -> applePressRecipes.add(recipeHolder.value()));
        registration.addRecipes(ApplePressCategory.APPLE_PRESS, applePressRecipes);
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return VineryIdentifier.of("jei_plugin");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ApplePressGuiHandler.class, ScreenhandlerTypeRegistry.APPLE_PRESS_GUI_HANDLER.get(), ApplePressCategory.APPLE_PRESS,
                0, 1, 2, 36);
        registration.addRecipeTransferHandler(new FermentationTransferInfo());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ObjectRegistry.FERMENTATION_BARREL.get().asItem().getDefaultInstance(), FermentationBarrelCategory.FERMENTATION_BARREL);
        registration.addRecipeCatalyst(ObjectRegistry.APPLE_PRESS.get().asItem().getDefaultInstance(), ApplePressCategory.APPLE_PRESS);
    }

    public static void addSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient){
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
    }
}
