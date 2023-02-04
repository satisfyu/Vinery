package satisfyu.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.CookingPotGuiHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CookingPotGui extends HandledScreen<CookingPotGuiHandler> {

    private static final Identifier BG = new VineryIdentifier("textures/gui/pot_gui.png");

    public CookingPotGui(CookingPotGuiHandler handler, PlayerInventory inventory, Text title) {
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
        final int progress = this.handler.getScaledProgress();
        this.drawTexture(matrices, posX + 89, posY + 26, 176, 16, progress + 1, 16);
        if (handler.isBeingBurned()) {
            this.drawTexture(matrices, posX + 124, posY + 56, 176, 0, 17, 15);
        }
    }
}
