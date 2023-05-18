package satisfyu.vinery.client.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PrivateRecipeGroupButtonWidget extends StateSwitchingButton {
    private final IRecipeBookGroup group;
    private float bounce;

    public PrivateRecipeGroupButtonWidget(IRecipeBookGroup group) {
        super(0, 0, 35, 27, false);
        this.group = group;
        this.initTextureValues(153, 2, 35, 0, PrivateRecipeBookWidget.TEXTURE);
    }

    public void renderButton(PoseStack matrices, int mouseX, int mouseY, float delta) {
        if (this.bounce > 0.0F) {
            float f = 1.0F + 0.1F * (float)Math.sin((this.bounce / 15.0F * 3.1415927F));
            matrices.pushPose();
            matrices.translate((this.x + 8), (this.y + 12), 0.0);
            matrices.scale(1.0F, f, 1.0F);
            matrices.translate((-(this.x + 8)), (-(this.y + 12)), 0.0);
        }

        Minecraft minecraftClient = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        RenderSystem.disableDepthTest();
        int i = this.xTexStart;
        int j = this.yTexStart;
        if (this.isStateTriggered) {
            i += this.xDiffTex;
        }

        if (this.isHoveredOrFocused()) {
            j += this.yDiffTex;
        }

        int k = this.x;
        if (this.isStateTriggered) {
            k -= 2;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(matrices, k, this.y, i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcons(minecraftClient.getItemRenderer());
        if (this.bounce > 0.0F) {
            matrices.popPose();
            this.bounce -= delta;
        }
    }

    private void renderIcons(ItemRenderer itemRenderer) {
        List<ItemStack> list = this.group.getIcons();
        int i = this.isStateTriggered ? -2 : 0;
        if (list.size() == 1) {
            itemRenderer.renderAndDecorateFakeItem( list.get(0), this.x + 9 + i, this.y + 5);
        } else if (list.size() == 2) {
            itemRenderer.renderAndDecorateFakeItem( list.get(0), this.x + 3 + i, this.y + 5);
            itemRenderer.renderAndDecorateFakeItem( list.get(1), this.x + 14 + i, this.y + 5);
        }

    }

    public IRecipeBookGroup getGroup() {
        return this.group;
    }
}