package daniking.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.handler.CookingPotGuiHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CookingPotGui extends AbstractContainerScreen<CookingPotGuiHandler> {

    private static final ResourceLocation BG = new VineryIdentifier("textures/gui/pot_gui.png");

    public CookingPotGui(CookingPotGuiHandler handler, Inventory inventory, Component title) {
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
        final int progress = this.menu.getScaledProgress();
        this.blit(matrices, posX + 89, posY + 26, 176, 16, progress + 1, 16);
        if (menu.isBeingBurned()) {
            this.blit(matrices, posX + 124, posY + 56, 176, 0, 17, 15);
        }
    }
}
