package satisfyu.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.ApplePressGuiHandler;
@Environment(EnvType.CLIENT)
<<<<<<< HEAD:common/src/main/java/satisfyu/vinery/client/gui/WinePressGui.java
public class WinePressGui extends AbstractContainerScreen<WinePressGuiHandler> {


    public static ResourceLocation BACKGROUND = new VineryIdentifier("textures/gui/barrel_gui.png");

=======
public class ApplePressGui extends AbstractContainerScreen<ApplePressGuiHandler> {
    public static final ResourceLocation TEXTURE =
            new VineryIdentifier("textures/gui/wine_press.png");
>>>>>>> 4dbba8565e8918b5487e4c689fd77b26079a74bf:common/src/main/java/satisfyu/vinery/client/gui/ApplePressGui.java

    public static final int ARROW_X = 78;
    public static final int ARROW_Y = 35;

    public ApplePressGui(ApplePressGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(BACKGROUND, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }


    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
<<<<<<< HEAD:common/src/main/java/satisfyu/vinery/client/gui/WinePressGui.java
             guiGraphics.blit(BACKGROUND, + 78, y + 35, 176, 0, menu.getScaledProgress(), 20);
=======
             guiGraphics.blit(TEXTURE, x + ARROW_X, y + ARROW_Y, 176, 0, menu.getScaledProgress(), 20);
>>>>>>> 4dbba8565e8918b5487e4c689fd77b26079a74bf:common/src/main/java/satisfyu/vinery/client/gui/ApplePressGui.java
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
