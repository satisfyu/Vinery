package satisfyu.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.CookingPotGuiHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.screen.sideTip.RecipeHandledGUI;

public class CookingPotGui extends RecipeHandledGUI<CookingPotGuiHandler> {

    private static final Identifier BACKGROUND;
    private static final Identifier SIDE_TIP;
    private static final int FRAMES = 1;

    public CookingPotGui(CookingPotGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, BACKGROUND, SIDE_TIP, FRAMES);
    }

    public void renderProgressArrow(MatrixStack matrices, int posX, int posY) {
        int progress = this.handler.getScaledProgress(18);
        this.drawTexture(matrices, x + 95, y + 14, 178, 15, progress, 30); //Position Arrow
    }

    public void renderBurnIcon(MatrixStack matrices, int posX, int posY) {
        if (handler.isBeingBurned()) {
            this.drawTexture(matrices, posX + 124, posY + 56, 176, 0, 17, 15); //fire
        }
    }

    static {
        BACKGROUND = new VineryIdentifier("textures/gui/pot_gui.png");
        SIDE_TIP = new VineryIdentifier("textures/gui/cooking_pot_recipe_book.png");
    }
}
