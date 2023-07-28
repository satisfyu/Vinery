package satisfyu.vinery.client.gui;

import de.cristelknight.doapi.client.recipebook.screen.AbstractRecipeBookGUIScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import satisfyu.vinery.client.recipebook.FermentationPotRecipeBook;

@Environment(EnvType.CLIENT)
public class FermentationBarrelGui extends AbstractRecipeBookGUIScreen<FermentationBarrelGuiHandler> {

    public static ResourceLocation BACKGROUND = new VineryIdentifier("textures/gui/barrel_gui.png");

    public static final int ARROW_X = 94;
    public static final int ARROW_Y = 37;

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, new FermentationPotRecipeBook(), BACKGROUND);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX += 20;
    }

    @Override
    protected void renderProgressArrow(GuiGraphics guiGraphics) {
        int progress = this.menu.getScaledProgress(23);
        guiGraphics.blit(BACKGROUND, leftPos + ARROW_X, topPos + ARROW_Y, 177, 17, progress, 10);
    }

}