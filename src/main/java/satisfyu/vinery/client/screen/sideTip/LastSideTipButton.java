package satisfyu.vinery.client.screen.sideTip;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.util.Identifier;
import satisfyu.vinery.VineryIdentifier;

public class LastSideTipButton extends ButtonWidget  {
    int u;
    int v;
    int hoveredVOffset;
    int textureWidth;
    int textureHeight;

    private final Identifier texture;

    public LastSideTipButton(int x, int y, PressAction pressAction) {
        this(x, y, 0, 0, 18, new VineryIdentifier("textures/gui/recipe_book.png"), 20, 36, pressAction);
    }
    public LastSideTipButton(int x, int y, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth , int textureHeight, PressAction pressAction) {
        super(x, y, 20, 18, ScreenTexts.EMPTY, pressAction, EMPTY);
        this.u = u;
        this.v = v;
        this.hoveredVOffset = hoveredVOffset;
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }


    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        int i = v;
        if (this.isHovered()) {
            i += hoveredVOffset;
        }
        RenderSystem.enableDepthTest();
        drawTexture(matrixStack, this.x, this.y, (float) u, (float)i, this.width, this.height, textureWidth, textureHeight);
        if (this.hovered) {
            this.renderTooltip(matrixStack, mouseX, mouseY);
        }
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}