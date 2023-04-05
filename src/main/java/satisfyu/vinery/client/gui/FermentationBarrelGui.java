package satisfyu.vinery.client.gui;

import net.minecraft.client.util.math.MatrixStack;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import satisfyu.vinery.client.screen.recipe.custom.FermentationPotRecipeBook;

public class FermentationBarrelGui extends AbstractRecipeBookGUIScreen<FermentationBarrelGuiHandler> {

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, new FermentationPotRecipeBook(), new VineryIdentifier("textures/gui/barrel_gui.png"));
    }

    @Override
    protected void init() {
        super.init();
        titleX += 20;
    }

    @Override
    public void renderProgressArrow(MatrixStack matrices) {
        int progress = this.handler.getScaledProgress(23);
        this.drawTexture(matrices, x + 94, y + 37, 177, 17, progress, 10); //Position Arrow
    }
}
