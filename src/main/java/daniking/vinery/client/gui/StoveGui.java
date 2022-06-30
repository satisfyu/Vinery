package daniking.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.handler.StoveGuiHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class StoveGui extends HandledScreen<StoveGuiHandler> {

    private static final Identifier BG = new VineryIdentifier("textures/gui/stove_gui.png");

    public StoveGui(StoveGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BG);
        final int posX = this.x;
        final int posY = this.y;
        this.drawTexture(matrices, posX, posY, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int k;
        if (this.handler.isBurning()) {
            k = (this.handler).getFuelProgress();
            this.drawTexture(matrices, posX + 56, posY + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        k = (this.handler).getCookProgress();
        this.drawTexture(matrices, posX + 79, posY + 34, 176, 14, k + 1, 16);
    }
}
