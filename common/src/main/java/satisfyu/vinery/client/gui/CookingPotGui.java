package satisfyu.vinery.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.CookingPotGuiHandler;
import satisfyu.vinery.client.screen.recipe.custom.CookingPotRecipeBook;

public class CookingPotGui extends AbstractRecipeBookGUIScreen<CookingPotGuiHandler> {

    public CookingPotGui(CookingPotGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, new CookingPotRecipeBook(), new VineryIdentifier("textures/gui/pot_gui.png"));
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX += 20;
    }

    @Override
    protected void renderProgressArrow(PoseStack matrices) {
        int progress = this.menu.getScaledProgress(18);
        this.blit(matrices, leftPos + 95, topPos + 14, 178, 15, progress, 30); //Position Arrow
    }

    @Override
    protected void renderBurnIcon(PoseStack matrices, int posX, int posY) {
        if (menu.isBeingBurned()) {
            this.blit(matrices, posX + 124, posY + 56, 176, 0, 17, 15); //fire
        }
    }
}
