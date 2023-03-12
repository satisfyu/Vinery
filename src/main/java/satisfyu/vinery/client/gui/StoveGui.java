package satisfyu.vinery.client.gui;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.StoveGuiHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.screen.sideTip.RecipeHandledGUI;
import satisfyu.vinery.screen.sideTip.SideToolTip;

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

    public void renderProgressArrow(MatrixStack matrices, int posX, int posY) {
        int progress = this.handler.getScaledProgress(18);
        this.drawTexture(matrices, x + 93, y + 32, 178, 20, progress, 25); //Position Arrow
    }

    public void renderBurnIcon(MatrixStack matrices, int posX, int posY) {
        if (this.handler.isBeingBurned()) {
            this.drawTexture(matrices, posX + 62, posY + 49, 176, 0, 17, 15); //fire
        }
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    public void addToolTips() {
        super.addToolTips();
        final int normalWidthAndHeight = 18;
        final int firstRow = 18;
        final int secondRow = 42;
        final int containerRow = 66;
        final int resultRow = 102;

        final int firstLine = 19;
        addToolTip(new SideToolTip(firstRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.apple_mash")));
        addToolTip(new SideToolTip(secondRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.dough")));
        addToolTip(new SideToolTip(containerRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.milk_bucket")));
        addToolTip(new SideToolTip(resultRow, firstLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.apple_pie")));

        final int secondLine = 43;
        addToolTip(new SideToolTip(firstRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")));
        addToolTip(new SideToolTip(secondRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.dough")));
        addToolTip(new SideToolTip(containerRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.cocoa_beans")));
        addToolTip(new SideToolTip(resultRow, secondLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.chocolate_bread")));

        final int thirdLine = 67;
        addToolTip(new SideToolTip(firstRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.dough")));
        addToolTip(new SideToolTip(secondRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.dough")));
        addToolTip(new SideToolTip(containerRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.water_bucket")));
        addToolTip(new SideToolTip(resultRow, thirdLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("block.vinery.crusty_bread")));

        final int fourthLine = 91;
        addToolTip(new SideToolTip(firstRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sweet_berries")));
        addToolTip(new SideToolTip(secondRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.dough")));
        addToolTip(new SideToolTip(containerRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")));
        addToolTip(new SideToolTip(resultRow, fourthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.donut")));

        final int fifthLine = 115;
        addToolTip(new SideToolTip(firstRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.milk_bucket")));
        addToolTip(new SideToolTip(secondRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")));
        addToolTip(new SideToolTip(containerRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.dough")));
        addToolTip(new SideToolTip(resultRow, fifthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.milk_bread")));

        final int sixthLine = 139;
        addToolTip(new SideToolTip(secondRow, sixthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.minecraft.sugar")));
        addToolTip(new SideToolTip(containerRow, sixthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.dough")));
        addToolTip(new SideToolTip(resultRow, sixthLine, normalWidthAndHeight, normalWidthAndHeight, Text.translatable("item.vinery.toast")));
    }

    static {
        BACKGROUND = new VineryIdentifier("textures/gui/stove_gui.png");
        SIDE_TIP = new VineryIdentifier("textures/gui/oven_recipe_book.png");
    }
}
