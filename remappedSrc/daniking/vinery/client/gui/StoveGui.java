package daniking.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.handler.StoveGuiHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StoveGui extends AbstractContainerScreen<StoveGuiHandler> {

    private static final ResourceLocation BG = new VineryIdentifier("textures/gui/stove_gui.png");

    public StoveGui(StoveGuiHandler handler, Inventory inventory, Component title) {
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
        this.blit(matrices, posX, posY, 0, 0, this.imageWidth, this.imageHeight);
        int k;
        if (this.menu.isBurning()) {
            k = (this.menu).getFuelProgress();
            this.blit(matrices, posX + 56, posY + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        k = (this.menu).getCookProgress();
        this.blit(matrices, posX + 79, posY + 34, 176, 14, k + 1, 16);
    }
}
