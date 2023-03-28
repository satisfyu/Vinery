package satisfyu.vinery.client.gui;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.CookingPotGuiHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.screen.sideTip.RecipeHandledGUI;
import satisfyu.vinery.screen.sideTip.SideToolTip;

import java.util.List;

public class CookingPotGui extends RecipeHandledGUI<CookingPotGuiHandler> {

    private static final Identifier BACKGROUND;
    private static final Identifier[] SIDE_TIP;
    private static final int FRAMES = 1;
    private static final List<List<SideToolTip>> TOOLTIPS;

    public CookingPotGui(CookingPotGuiHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, BACKGROUND, SIDE_TIP, FRAMES, TOOLTIPS);
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

    @Override
    protected void init() {
        super.init();
        titleX += 20;
    }

    private static List<List<SideToolTip>> generateToolTips() {
        final int normalWidthAndHeight = 18;
        final int firstRow = 15;
        final int secondRow = 41;
        final int containerRow = 67;
        final int resultRow = 101;

        final int firstLine = 19;
        final int secondLine = 44;
        final int thirdLine = 69;
        final int fourthLine = 94;
        final int fifthLine = 119;
        
        return List.of(
                List.of(new SideToolTip(firstRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sweet_berries")),
                        new SideToolTip(secondRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")),
                        new SideToolTip(containerRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.cherry_jar")),
                        new SideToolTip(resultRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.sweetberry_jam")),


                        new SideToolTip(firstRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.cherry")),
                        new SideToolTip(secondRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")),
                        new SideToolTip(containerRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.cherry_jar")),
                        new SideToolTip(resultRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.cherry_jam")),


                        new SideToolTip(firstRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.apple_mash")),
                        new SideToolTip(secondRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")),
                        new SideToolTip(containerRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.cherry_jar")),
                        new SideToolTip(resultRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.apple_jam")),


                        new SideToolTip(firstRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.red_grape")),
                        new SideToolTip(secondRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")),
                        new SideToolTip(containerRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.cherry_jar")),
                        new SideToolTip(resultRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.grape_jam")),


                        new SideToolTip(firstRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.apple")),
                        new SideToolTip(secondRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")),
                        new SideToolTip(containerRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.bowl")),
                        new SideToolTip(resultRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.applesauce")))
        );
        
    }

    static {
        BACKGROUND = new VineryIdentifier("textures/gui/pot_gui.png");
        SIDE_TIP = new Identifier[] {new VineryIdentifier("textures/gui/cooking_pot_recipe_book.png"),
                new VineryIdentifier("textures/gui/fermentation_barrel_recipe_book.png")};
        TOOLTIPS = generateToolTips();
    }
}
