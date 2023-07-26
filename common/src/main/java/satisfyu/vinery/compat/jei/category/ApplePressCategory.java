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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.client.gui.ApplePressGui;
import satisfyu.vinery.compat.jei.VineryJEIPlugin;
import satisfyu.vinery.recipe.ApplePressRecipe;
import satisfyu.vinery.registry.ObjectRegistry;

public class ApplePressCategory implements IRecipeCategory<ApplePressRecipe> {
    public static final RecipeType<ApplePressRecipe> APPLE_PRESS = RecipeType.create(Vinery.MODID, "apple_mashing", ApplePressRecipe.class);
    public static final int WIDTH = 124;
    public static final int HEIGHT = 60;
    public static final int WIDTH_OF = 26;
    public static final int HEIGHT_OF = 13;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final Component localizedName;

    public ApplePressCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(ApplePressGui.TEXTURE, WIDTH_OF, HEIGHT_OF, WIDTH, HEIGHT);
        this.arrow = helper.drawableBuilder(ApplePressGui.TEXTURE, 176, 0, 26, 20)
                .buildAnimated(50, IDrawableAnimated.StartDirection.LEFT, false);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ObjectRegistry.APPLE_PRESS.get().asItem().getDefaultInstance());
        this.localizedName = Component.translatable("rei.vinery.apple_press_category");
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ApplePressRecipe recipe, IFocusGroup focuses) {
        VineryJEIPlugin.addSlot(builder, 48 - WIDTH_OF, 34 - HEIGHT_OF, recipe.input);

        // Output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116 - WIDTH_OF,  35 - HEIGHT_OF).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(ApplePressRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, ApplePressGui.ARROW_X - WIDTH_OF, ApplePressGui.ARROW_Y - HEIGHT_OF);
    }

    @Override
    public RecipeType<ApplePressRecipe> getRecipeType() {
        return APPLE_PRESS;
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
