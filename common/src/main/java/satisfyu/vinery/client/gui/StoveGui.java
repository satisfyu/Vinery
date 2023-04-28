package satisfyu.vinery.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.StoveGuiHandler;
import satisfyu.vinery.client.screen.recipe.custom.WoodFiredOvenRecipeBook;

public class StoveGui extends AbstractRecipeBookGUIScreen<StoveGuiHandler> {

    public StoveGui(StoveGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, new WoodFiredOvenRecipeBook(),  new VineryIdentifier("textures/gui/stove_gui.png"));
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    @Override
    public void renderProgressArrow(PoseStack matrices) {
        int progress = this.menu.getScaledProgress(18);
        blit(matrices, leftPos + 93, topPos + 32, 178, 20, progress, 25); //Position Arrow
    }

    @Override
    public void renderBurnIcon(PoseStack matrices, int posX, int posY) {
        if (this.menu.isBeingBurned()) {
            blit(matrices, posX + 62, posY + 49, 176, 0, 17, 15); //fire
        }
    }
}
