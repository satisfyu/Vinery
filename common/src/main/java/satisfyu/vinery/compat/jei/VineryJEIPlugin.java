package satisfyu.vinery.compat.jei;

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
import net.minecraft.world.item.crafting.RecipeManager;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.ApplePressGuiHandler;
import satisfyu.vinery.compat.jei.category.ApplePressCategory;
import satisfyu.vinery.compat.jei.category.FermentationBarrelCategory;
import satisfyu.vinery.compat.jei.transfer.FermentationTransferInfo;
import satisfyu.vinery.recipe.ApplePressRecipe;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VineryRecipeTypes;
import satisfyu.vinery.registry.VineryScreenHandlerTypes;

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

        List<FermentationBarrelRecipe> fermentationBarrelRecipes = rm.getAllRecipesFor(VineryRecipeTypes.FERMENTATION_BARREL_RECIPE_TYPE.get());
        registration.addRecipes(FermentationBarrelCategory.FERMENTATION_BARREL, fermentationBarrelRecipes);

        List<ApplePressRecipe> applePressRecipes = rm.getAllRecipesFor(VineryRecipeTypes.APPLE_PRESS_RECIPE_TYPE.get());
        registration.addRecipes(ApplePressCategory.APPLE_PRESS, applePressRecipes);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new VineryIdentifier("jei_plugin");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ApplePressGuiHandler.class, VineryScreenHandlerTypes.APPLE_PRESS_GUI_HANDLER.get(), ApplePressCategory.APPLE_PRESS,
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
