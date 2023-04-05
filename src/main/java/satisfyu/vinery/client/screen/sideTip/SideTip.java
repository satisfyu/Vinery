package satisfyu.vinery.client.screen.sideTip;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class SideTip extends Screen {
    private int x;
    private int y;
    public static final int WIDTH = 147;
    private final int height;
    private final Identifier[] TEXTURES;
    private final int textureWidth;
    private final int textureHeight;
    private final int vOffset;
    private final int frameTicks = 40;
    private final int frames;
    private int currentTick;
    protected boolean hovered;
    public boolean visible = false;
    public int active = 0;
    private List<List<SideToolTip>> tooltips;

    public SideTip(int x, int y, int width, int height, int vOffset, Identifier[] texture, int textureWidth, int textureHeight, int frames, List<List<SideToolTip>> tooltips) {
        super(Text.of(""));
        TEXTURES = texture;
        this.x = x;
        this.y = y;
        //this.width = width;
        this.height = height;
        this.vOffset = vOffset;
        this.frames = frames;
        this.currentTick = 0;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.tooltips = tooltips;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + WIDTH && mouseY < this.y + this.height;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURES[active]);
            RenderSystem.enableDepthTest();

            int offsetFactor = currentTick / frameTicks;
            drawTexture(matrices, this.x, this.y, 0, vOffset * (offsetFactor % frames), WIDTH, this.height, this.textureWidth, this.textureHeight);

            //NEXT_SIDE_BUTTON.setPos(this.x - 50, this.y + 165);
            //LAST_SIDE_BUTTON.setPos(this.x - 100, this.y + 165);
            //addDrawable(NEXT_SIDE_BUTTON);
            //addDrawableChild(LAST_SIDE_BUTTON);

            super.render(matrices, mouseX, mouseY, delta);
            renderTooltips(matrices, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void tick() {
        currentTick++;
        currentTick = currentTick % (frames * frameTicks);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getActive() {
        return this.active;
    }

    public void nextSideTip() {
        if (this.active < TEXTURES.length - 1) {
            active++;
        } else {
            active = 0;
        }
    }

    public void lastSideTip() {
        if (this.active > 0) {
            active--;
        } else {
            active = TEXTURES.length - 1;
        }
    }

    private void renderTooltips(MatrixStack matrices, int mouseX, int mouseY) {
        if (visible) {
            for (SideToolTip sideToolTip : tooltips.get(active)) {
                if (sideToolTip.isMouseOver(mouseX - this.x, mouseY - this.y)) {
                    renderTooltip(matrices, sideToolTip.getText(), mouseX, mouseY);
                }
            }
        }
    }

}
