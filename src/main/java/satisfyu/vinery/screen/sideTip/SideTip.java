package satisfyu.vinery.screen.sideTip;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SideTip extends DrawableHelper implements Drawable, Element{
    private int x;
    private int y;
    public static final int WIDTH = 147;
    private final int height;
    private final Identifier TEXTURE;
    private final int textureWidth;
    private final int textureHeight;
    private final int vOffset;
    private final int frameTicks = 40;
    private final int frames;
    private int currentTick;
    protected boolean hovered;
    public boolean visible = false;

    public SideTip(int x, int y, int width, int height, int vOffset, Identifier texture, int textureWidth, int textureHeight, int frames) {
        TEXTURE = texture;
        this.x = x;
        this.y = y;
        //this.width = width;
        this.height = height;
        this.vOffset = vOffset;
        this.frames = frames;
        this.currentTick = 0;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.WIDTH && mouseY < this.y + this.height;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE);
            RenderSystem.enableDepthTest();
            int offsetFactor = currentTick / frameTicks;
            drawTexture(matrices, this.x, this.y, 0, vOffset * (offsetFactor % frames), this.WIDTH, this.height, this.textureWidth, this.textureHeight);
        }
    }

    public void tick() {
        currentTick++;
        currentTick = currentTick % (frames * frameTicks);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
