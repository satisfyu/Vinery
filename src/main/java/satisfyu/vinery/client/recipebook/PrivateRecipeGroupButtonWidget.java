package satisfyu.vinery.client.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PrivateRecipeGroupButtonWidget extends ToggleButtonWidget {
    private final IRecipeBookGroup group;
    private float bounce;

    public PrivateRecipeGroupButtonWidget(IRecipeBookGroup group) {
        super(0, 0, 35, 27, false);
        this.group = group;
        this.setTextureUV(153, 2, 35, 0, PrivateRecipeBookWidget.TEXTURE);
    }

    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.bounce > 0.0F) {
            float f = 1.0F + 0.1F * (float)Math.sin((this.bounce / 15.0F * 3.1415927F));
            matrices.push();
            matrices.translate((this.getX() + 8), (this.getY() + 12), 0.0);
            matrices.scale(1.0F, f, 1.0F);
            matrices.translate((-(this.getX() + 8)), (-(this.getY() + 12)), 0.0);
        }

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.disableDepthTest();
        int i = this.u;
        int j = this.v;
        if (this.toggled) {
            i += this.pressedUOffset;
        }

        if (this.isHovered()) {
            j += this.hoverVOffset;
        }

        int k = this.getX();
        if (this.toggled) {
            k -= 2;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexture(matrices, k, this.getY(), i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcons(matrices, minecraftClient.getItemRenderer());
        if (this.bounce > 0.0F) {
            matrices.pop();
            this.bounce -= delta;
        }
    }

    private void renderIcons(MatrixStack matrices, ItemRenderer itemRenderer) {
        List<ItemStack> list = this.group.getIcons();
        int i = this.toggled ? -2 : 0;
        if (list.size() == 1) {
            itemRenderer.renderInGui(matrices, list.get(0), this.getX() + 9 + i, this.getY() + 5);
        } else if (list.size() == 2) {
            itemRenderer.renderInGui(matrices, list.get(0), this.getX() + 3 + i, this.getY() + 5);
            itemRenderer.renderInGui(matrices, list.get(1), this.getX() + 14 + i, this.getY() + 5);
        }

    }

    public IRecipeBookGroup getGroup() {
        return this.group;
    }
}