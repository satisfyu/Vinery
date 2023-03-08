package satisfyu.vinery.client.gui;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.screen.sideTip.RecipeHandledGUI;

public class FermentationBarrelGui extends RecipeHandledGUI<FermentationBarrelGuiHandler> {

    private static final Identifier BACKGROUND;
    private static final Identifier SIDE_TIP;
    private static final int FRAMES = 1;

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, BACKGROUND, SIDE_TIP, FRAMES);
    }

    static {
        BACKGROUND = new VineryIdentifier("textures/gui/barrel_gui.png");
        SIDE_TIP = new VineryIdentifier("textures/gui/cooking_cauldron_recipe_book.png");
    }
}
