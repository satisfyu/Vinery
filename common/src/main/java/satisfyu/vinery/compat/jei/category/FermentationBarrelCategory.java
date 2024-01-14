package satisfyu.vinery.compat.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.client.gui.FermentationBarrelGui;
import satisfyu.vinery.compat.jei.VineryJEIPlugin;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.registry.ObjectRegistry;

public class FermentationBarrelCategory implements IRecipeCategory<FermentationBarrelRecipe> {
    public static final RecipeType<FermentationBarrelRecipe> FERMENTATION_BARREL = RecipeType.create(Vinery.MOD_ID, "wine_fermentation", FermentationBarrelRecipe.class);
    public static final int WIDTH = 124;
    public static final int HEIGHT = 60;
    public static final int WIDTH_OF = 26;
    public static final int HEIGHT_OF = 13;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final Component localizedName;

    public FermentationBarrelCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(FermentationBarrelGui.BACKGROUND, WIDTH_OF, HEIGHT_OF, WIDTH, HEIGHT);
        this.arrow = helper.drawableBuilder(FermentationBarrelGui.BACKGROUND, 177, 17, 23, 10)
                .buildAnimated(50, IDrawableAnimated.StartDirection.LEFT, false);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ObjectRegistry.FERMENTATION_BARREL.get().asItem().getDefaultInstance());
        this.localizedName = Component.translatable("rei.vinery.fermentation_barrel_category");
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FermentationBarrelRecipe recipe, IFocusGroup focuses) {
        // Wine input
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int s = ingredients.size();

        builder.addSlot(RecipeIngredientRole.INPUT, 79 - WIDTH_OF, 51 - HEIGHT_OF).addItemStack(ObjectRegistry.WINE_BOTTLE.get().getDefaultInstance());
        if(s > 0) VineryJEIPlugin.addSlot(builder, 33 - WIDTH_OF, 26 - HEIGHT_OF, ingredients.get(0));
        if(s > 1) VineryJEIPlugin.addSlot(builder, 51 - WIDTH_OF, 26 - HEIGHT_OF, ingredients.get(1));
        if(s > 2) VineryJEIPlugin.addSlot(builder, 33 - WIDTH_OF, 44 - HEIGHT_OF, ingredients.get(2));
        if(s > 3) VineryJEIPlugin.addSlot(builder, 51 - WIDTH_OF, 44 - HEIGHT_OF, ingredients.get(3));

        // Output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 128 - WIDTH_OF,  35 - HEIGHT_OF).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }

    @Override
    public void draw(FermentationBarrelRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, FermentationBarrelGui.ARROW_X - WIDTH_OF, FermentationBarrelGui.ARROW_Y - HEIGHT_OF);
    }

    @Override
    public RecipeType<FermentationBarrelRecipe> getRecipeType() {
        return FERMENTATION_BARREL;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }
}
