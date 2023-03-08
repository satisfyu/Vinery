package satisfyu.vinery.screen.sideTip;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class RecipeHandledGUI<T extends RecipeGUIHandler> extends HandledScreen<T> {

    private final SideTipButton SIDE_TIP_BUTTON = new SideTipButton(this.x, this.y, (buttonWidget) -> showSideTip());
    private final Identifier BACKGROUND;
    private final SideTip SIDE_TIP;

    private final List<SideToolTip> sideToolTips = new ArrayList<>();


    public RecipeHandledGUI(T handler, PlayerInventory inventory, Text title, Identifier background, Identifier sideTip, int frames) {
        super(handler, inventory, title);
        BACKGROUND = background;
        SIDE_TIP = new SideTip(this.x, this.y, 147, 180, 256, sideTip, 256, 256 * frames, frames);
        addToolTips();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        SIDE_TIP.setPos(this.x - SideTip.WIDTH, this.y);
        SIDE_TIP_BUTTON.setPos(this.x + 4, this.y + 25);
        addDrawable(SIDE_TIP);
        addDrawableChild(SIDE_TIP_BUTTON);

        super.render(matrices, mouseX, mouseY, delta);

        renderTooltips(matrices, mouseX, mouseY);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    private void renderTooltips(MatrixStack matrices, int mouseX, int mouseY) {
        for (SideToolTip sideToolTip : sideToolTips) {
            if (sideToolTip.isMouseOver(mouseX - this.x, mouseY - this.y)) {
                renderTooltip(matrices, sideToolTip.getText(), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        SIDE_TIP.tick();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        final int posX = this.x;
        final int posY = this.y;
        this.drawTexture(matrices, posX, posY, 0, 0, this.backgroundWidth - 1, this.backgroundHeight);

        renderProgressArrow(matrices, posX, posY);
        renderBurnIcon(matrices, posX, posY);
    }

    public void renderProgressArrow(MatrixStack matrices, int posX, int posY) {
    }

    public void renderBurnIcon(MatrixStack matrices, int posX, int posY) {
    }

    private void showSideTip() {
        SIDE_TIP.visible = !SIDE_TIP.visible;
    }

    public void addToolTips() {
        addToolTip(new SideToolTip(SideTip.WIDTH + 4, 25, 20, 18, Text.translatable("tooltip.vinery.recipe_book")));
    }

    public void addToolTip(SideToolTip sideToolTip) {
        //pos in Book
        this.sideToolTips.add(sideToolTip);
    }


}
