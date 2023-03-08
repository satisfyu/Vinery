package satisfyu.vinery.client.gui;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.StoveGuiHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.screen.sideTip.RecipeHandledGUI;

public class StoveGui extends RecipeHandledGUI<StoveGuiHandler> {
    private static final Identifier BACKGROUND;
    private static final Identifier SIDE_TIP;
    private static final int FRAMES = 1;

    public StoveGui(StoveGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, BACKGROUND, SIDE_TIP, FRAMES);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    static {
        BACKGROUND = new VineryIdentifier("textures/gui/stove_gui.png");
        SIDE_TIP = new VineryIdentifier("textures/gui/cooking_cauldron_recipe_book.png");
    }
}
