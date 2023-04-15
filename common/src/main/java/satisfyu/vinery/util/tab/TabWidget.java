package satisfyu.vinery.util.tab;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import static com.mojang.blaze3d.systems.RenderSystem.*;

@Environment(EnvType.CLIENT)
public class TabWidget extends ButtonWidget {
    public static final int TABS_PER_COLUMN = 4;

    private final TabbedItemGroup group;
    private final int index;
    private final GUIIcon<Identifier> backgroundTexture;
    private boolean selected;

    public TabWidget(int x, int y, TabbedItemGroup group, int index, Text message, GUIIcon<Identifier> backgroundTexture) {
        super(x, y, 34, 26, message, button -> {});
        this.group = group;
        this.index = index;
        this.backgroundTexture = backgroundTexture;
    }

    public TabbedItemGroup getGroup() {
        return this.group;
    }

    public int getIndex() {
        return this.index;
    }

    public Tab getTab() {
        TabbedItemGroup group = this.getGroup();
        List<Tab> tabs = group.getTabs();
        int i = this.getIndex();
        return tabs.get(i);
    }

    public Identifier getBackgroundTexture() {
        return this.backgroundTexture.getIcon(this.isHovered(), this.isSelected());
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isRightColumn() {
        return isRightColumn(this.getIndex());
    }

    public static boolean isRightColumn(int index) {
        return index >= TABS_PER_COLUMN;
    }

    @Override
    public void onPress() {
        if (this.group.getSelectedTabIndex() == this.index) {
            this.group.setSelectedTabIndex(-1);
            this.setSelected(false);
            return;
        }

        this.group.setSelectedTabIndex(this.index);
        this.setSelected(true);
    }

    @Override
    protected int getYImage(boolean hover) {
        return hover || this.isSelected() ? 1 : 0;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        Tab tab = this.getTab();
        GUIIcon<?> icon = tab.getIcon();
        boolean hovered = this.isHovered();
        boolean selected = this.isSelected();

        int xo = hovered || selected ? (this.isRightColumn() ? 2 : -2) : 0;
        int x = this.x + 11 + xo;
        int y = this.y + 4;

        // render background
        this.renderBackground(matrices, client, mouseX, mouseY);

        // render icon
        GUIIcon.optional(icon, hovered, selected, Identifier.class).ifPresentOrElse(
                texture -> this.renderButtonTexture(texture, matrices, x, y),
                () -> this.renderButtonStack(icon, hovered, selected, x, y)
        );

        // render tooltip
        if (hovered) this.renderTooltip(matrices, mouseX, mouseY);
    }

    public void renderButtonTexture(Identifier texture, MatrixStack matrices, int x, int y) {
        setShader(GameRenderer::getPositionTexShader);
        setShaderTexture(0, texture);
        drawTexture(matrices, x, y, 0, 0, 16, 16, 16, 16);
    }

    public void renderButtonStack(GUIIcon<?> icon, boolean hovered, boolean selected, int x, int y) {
        GUIIcon.optional(icon, hovered, selected, ItemStack.class).ifPresent(stack -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ItemRenderer itemRenderer = client.getItemRenderer();
            itemRenderer.renderInGui(stack, x, y);
        });
    }

    @Override
    protected void renderBackground(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
        enableBlend();
        defaultBlendFunc();
        blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        setShaderTexture(0, this.getBackgroundTexture());
        drawTexture(matrices, this.x, this.y, 0, this.isRightColumn() ? 32 : 0, 64, 32, 64, 64);
    }

    @Override
    public void renderTooltip(MatrixStack matrices, int mouseX, int mouseY) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen != null && !this.isSelected()) {
            Text message = this.getMessage();
            TooltipComponent tooltip = TooltipComponent.of(message.asOrderedText());
            int width = tooltip.getWidth(client.textRenderer);
            int height = tooltip.getHeight();
            int ox = this.isRightColumn() ? -this.width + 62 : -(width + this.width) + 20;
            int oz = -(height + this.height) + 16;
            client.currentScreen.renderTooltip(matrices, message, x + ox, y - oz);
        }
    }
}
