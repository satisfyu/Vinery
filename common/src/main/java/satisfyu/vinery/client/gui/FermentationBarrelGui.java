package satisfyu.vinery.client.gui;

import de.cristelknight.doapi.client.recipebook.screen.AbstractRecipeBookGUIScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import satisfyu.vinery.client.recipebook.FermentationPotRecipeBook;

@Environment(EnvType.CLIENT)
public class FermentationBarrelGui extends AbstractRecipeBookGUIScreen<FermentationBarrelGuiHandler> {

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, new FermentationPotRecipeBook(), new VineryIdentifier("textures/gui/barrel_gui.png"));
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX += 20;
    }

    @Override
    protected void renderProgressArrow(GuiGraphics guiGraphics) {
        int progress = this.menu.getScaledProgress(23);
        guiGraphics.fill(leftPos + 94, topPos + 37, 177, 17, progress, 10); //Position Arrow
    }

}
