package satisfyu.vinery.client.gui;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.StoveGuiHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import satisfyu.vinery.client.screen.recipe.custom.WoodFiredOvenRecipeBook;

public class StoveGui extends AbstractRecipeBookGUIScreen<StoveGuiHandler> {

    public StoveGui(StoveGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, new WoodFiredOvenRecipeBook(),  new VineryIdentifier("textures/gui/stove_gui.png"));
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    public void renderProgressArrow(MatrixStack matrices) {
        int progress = this.handler.getScaledProgress(18);
        this.drawTexture(matrices, x + 93, y + 32, 178, 20, progress, 25); //Position Arrow
    }

    @Override
    public void renderBurnIcon(MatrixStack matrices, int posX, int posY) {
        if (this.handler.isBeingBurned()) {
            this.drawTexture(matrices, posX + 62, posY + 49, 176, 0, 17, 15); //fire
        }
    }
}
