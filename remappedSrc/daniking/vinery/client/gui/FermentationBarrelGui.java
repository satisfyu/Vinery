package daniking.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FermentationBarrelGui extends AbstractContainerScreen<FermentationBarrelGuiHandler> {

    private static final ResourceLocation BG = new VineryIdentifier("textures/gui/barrel_gui.png");

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.renderTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BG);
        final int posX = this.leftPos;
        final int posY = this.topPos;
        this.blit(matrices, posX, posY, 0, 0, this.imageWidth - 1, this.imageHeight);
        final int  k = (this.menu).getCookProgress();
        this.blit(matrices, posX + 89, posY + 34, 175, 14, k + 1, 16);

    }
}
