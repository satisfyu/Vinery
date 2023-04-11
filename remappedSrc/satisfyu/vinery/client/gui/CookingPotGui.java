package satisfyu.vinery.client.gui;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.CookingPotGuiHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import satisfyu.vinery.client.screen.recipe.custom.CookingPotRecipeBook;

public class CookingPotGui extends AbstractRecipeBookGUIScreen<CookingPotGuiHandler> {

    public CookingPotGui(CookingPotGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, new CookingPotRecipeBook(), new VineryIdentifier("textures/gui/pot_gui.png"));
    }

    @Override
    protected void init() {
        super.init();
        titleX += 20;
    }

    @Override
    protected void renderProgressArrow(MatrixStack matrices) {
        int progress = this.handler.getScaledProgress(18);
        this.drawTexture(matrices, x + 95, y + 14, 178, 15, progress, 30); //Position Arrow
    }

    @Override
    protected void renderBurnIcon(MatrixStack matrices, int posX, int posY) {
        if (handler.isBeingBurned()) {
            this.drawTexture(matrices, posX + 124, posY + 56, 176, 0, 17, 15); //fire
        }
    }
}
