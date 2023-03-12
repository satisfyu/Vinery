package satisfyu.vinery.client.gui;

import net.minecraft.client.util.math.MatrixStack;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.screen.sideTip.RecipeHandledGUI;
import satisfyu.vinery.screen.sideTip.SideToolTip;

public class FermentationBarrelGui extends RecipeHandledGUI<FermentationBarrelGuiHandler> {

    private static final Identifier BACKGROUND;
    private static final Identifier SIDE_TIP;
    private static final int FRAMES = 1;

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, BACKGROUND, SIDE_TIP, FRAMES);
    }

    public void renderProgressArrow(MatrixStack matrices, int posX, int posY) {
        int progress = this.handler.getScaledProgress(23);
        this.drawTexture(matrices, x + 94, y + 37, 177, 17, progress, 10); //Position Arrow
    }

    @Override
    protected void init() {
        super.init();
        titleX += 20;
    }

    @Override
    public void addToolTips() {
        super.addToolTips();
        addToolTip(new SideToolTip(16, 18, 48, 47, Text.translatable("tooltip.vinery.aging_barrel")));
        addToolTip(new SideToolTip(96, 32, 17, 17, Text.translatable("block.vinery.wine_bottle")));
        addToolTip(new SideToolTip(54, 120, 34, 30, Text.translatable("tooltip.vinery.wine")));
    }

        static {
        BACKGROUND = new VineryIdentifier("textures/gui/barrel_gui.png");
        SIDE_TIP = new VineryIdentifier("textures/gui/fermentation_barrel_recipe_book.png");
    }
}
