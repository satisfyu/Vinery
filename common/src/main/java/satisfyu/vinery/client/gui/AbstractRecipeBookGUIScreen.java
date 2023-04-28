package satisfyu.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.recipebook.AbstractPrivateRecipeScreenHandler;
import satisfyu.vinery.client.recipebook.PrivateRecipeBookWidget;

public abstract class AbstractRecipeBookGUIScreen<T extends AbstractPrivateRecipeScreenHandler> extends AbstractContainerScreen<T> {
    private final ResourceLocation BACKGROUND;
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE;
    public final PrivateRecipeBookWidget recipeBook;
    private boolean narrow;

    public AbstractRecipeBookGUIScreen(T handler, Inventory inventory, Component title, PrivateRecipeBookWidget recipeBook, ResourceLocation background) {
        super(handler, inventory, title);
        BACKGROUND = background;
        this.recipeBook = recipeBook;
    }

    @Override
    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.minecraft, this.narrow, this.menu);
        this.leftPos = this.recipeBook.findLeftEdge(this.width, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.topPos + 25, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (button) -> {
            this.recipeBook.toggleOpen();
            this.leftPos = this.recipeBook.findLeftEdge(this.width, this.imageWidth);
            button.setPosition(this.leftPos +  5, this.topPos + 25);
        }));
        this.titleLabelX += 20;
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        if (this.recipeBook.isOpen() && this.narrow) {
            this.renderBg(matrices, delta, mouseX, mouseY);
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
            super.render(matrices, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(matrices, this.leftPos, this.topPos, true, delta);
        }

        this.renderTooltip(matrices, mouseX, mouseY);
        this.recipeBook.drawTooltip(matrices, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        final int posX = this.leftPos;
        final int posY = this.topPos;
        blit(matrices, posX, posY, 0, 0, this.imageWidth - 1, this.imageHeight);

        renderProgressArrow(matrices);
        renderBurnIcon(matrices, posX, posY);
    }

    protected void renderProgressArrow(PoseStack matrices) {
    }

    protected void renderBurnIcon(PoseStack matrices, int posX, int posY) {
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.recipeBook.update();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.narrow && this.recipeBook.isOpen() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int button, ClickType actionType) {
        super.slotClicked(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return !this.recipeBook.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.imageWidth) || mouseY >= (double)(top + this.imageHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight) && bl;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return this.recipeBook.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
    }

    @Override
    public void removed() {
        //this.recipeBook.close();
        super.removed();
    }

    static {
        RECIPE_BUTTON_TEXTURE = new VineryIdentifier("textures/gui/recipe_button.png");
    }
}
