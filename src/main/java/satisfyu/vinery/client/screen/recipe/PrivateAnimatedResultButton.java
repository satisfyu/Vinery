package satisfyu.vinery.client.screen.recipe;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.client.recipebook.AbstractPrivateRecipeScreenHandler;

import java.util.List;

public class PrivateAnimatedResultButton extends ClickableWidget {
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/recipe_book.png");
    private AbstractPrivateRecipeScreenHandler craftingScreenHandler;
    private Recipe<?> recipe;
    private float bounce;

    public PrivateAnimatedResultButton() {
        super(0, 0, 25, 25, ScreenTexts.EMPTY);
    }

    public void showResultCollection(Recipe<?> recipe, AbstractPrivateRecipeScreenHandler craftingScreenHandler) {
        this.recipe = recipe;
        this.craftingScreenHandler = craftingScreenHandler;
    }

    public Recipe<?> getRecipe() {
        return this.recipe;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = 29;
        if (!craftingScreenHandler.hasIngredient(this.recipe)) {
            i += 25;
        }

        int j = 206;

        boolean bl = this.bounce > 0.0F;
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        if (bl) {
            float f = 1.0F + 0.1F * (float)Math.sin((this.bounce / 15.0F * 3.1415927F));
            matrixStack.push();
            matrixStack.translate((this.x + 8), (this.y + 12), 0.0);
            matrixStack.scale(f, f, 1.0F);
            matrixStack.translate((-(this.x + 8)), (-(this.y + 12)), 0.0);
            RenderSystem.applyModelViewMatrix();
            this.bounce -= delta;
        }

        this.drawTexture(matrices, this.x, this.y, i, j, this.width, this.height);
        Recipe<?> recipe = this.getResult();
        int k = 4;

        minecraftClient.getItemRenderer().renderInGui(recipe.getOutput(), this.x + k, this.y + k);
        if (bl) {
            matrixStack.pop();
            RenderSystem.applyModelViewMatrix();
        }

    }

    private Recipe<?> getResult() {
        return this.recipe;
    }

    public boolean hasResult() {
        return this.getResult() != null;
    }

    public Recipe<?> currentRecipe() {
        return this.getResult();
    }

    @Override
    protected boolean isValidClickButton(int button) {
        return button == 0 ||button == 1;
    }

    public List<Text> getTooltip(Screen screen) {
        ItemStack itemStack = this.getResult().getOutput();
        return Lists.newArrayList(screen.getTooltipFromItem(itemStack));
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        ItemStack itemStack = this.getResult().getOutput();
        builder.put(NarrationPart.TITLE, Text.translatable("narration.recipe", itemStack.getName()));

        builder.put(NarrationPart.USAGE, Text.translatable("narration.button.usage.hovered"));
    }

    @Override
    public int getWidth() {
        return 25;
    }
}