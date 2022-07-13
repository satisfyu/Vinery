package daniking.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FermentationBarrelGui extends HandledScreen<FermentationBarrelGuiHandler> {

    private static final Identifier BG = new VineryIdentifier("textures/gui/barrel_gui.png");

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, PlayerInventory inventory, Text title) {
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
        this.drawTexture(matrices, posX, posY, 0, 0, this.backgroundWidth - 1, this.backgroundHeight);
        final int  k = (this.handler).getCookProgress();
        this.drawTexture(matrices, posX + 89, posY + 34, 175, 14, k + 1, 16);

    }
}
