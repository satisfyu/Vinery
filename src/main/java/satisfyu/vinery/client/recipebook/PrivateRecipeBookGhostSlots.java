package satisfyu.vinery.client.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.util.List;

public class PrivateRecipeBookGhostSlots {
    private final List<PrivateGhostInputSlot> slots = Lists.newArrayList();
    float time;

    public PrivateRecipeBookGhostSlots() {
    }

    public void reset() {
        this.slots.clear();
        this.time = 0.0F;
    }

    public void addSlot(ItemStack itemStack, int x, int y) {
        this.slots.add(new PrivateGhostInputSlot(itemStack, x, y));
    }

    public PrivateGhostInputSlot getSlot(int index) {
        return this.slots.get(index);
    }

    public int getSlotCount() {
        return this.slots.size();
    }

    public void draw(MatrixStack matrices, MinecraftClient client, int i, int j, boolean bl, float f) {
        if (!Screen.hasControlDown()) {
            this.time += f;
        }

        for(int k = 0; k < this.slots.size(); ++k) {
            PrivateGhostInputSlot ghostInputSlot = this.slots.get(k);
            int l = ghostInputSlot.getX() + i;
            int m = ghostInputSlot.getY() + j;
            if (k == 0 && bl) {
                DrawableHelper.fill(matrices, l - 4, m - 4, l + 20, m + 20, 822018048);
            } else {
                DrawableHelper.fill(matrices, l, m, l + 16, m + 16, 822018048);
            }

            ItemStack itemStack = ghostInputSlot.getCurrentItemStack();
            ItemRenderer itemRenderer = client.getItemRenderer();
            itemRenderer.renderInGui(matrices, itemStack, l, m);
            RenderSystem.depthFunc(516);
            DrawableHelper.fill(matrices, l, m, l + 16, m + 16, 822083583);
            RenderSystem.depthFunc(515);
            if (k == 0) {
                itemRenderer.renderGuiItemOverlay(matrices, client.textRenderer, itemStack, l, m);
            }
        }

    }

    @Environment(EnvType.CLIENT)
    public static class PrivateGhostInputSlot {
        private final ItemStack itemStack;
        private final int x;
        private final int y;

        public PrivateGhostInputSlot(ItemStack itemStack, int x, int y) {
            this.itemStack = itemStack;
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public ItemStack getCurrentItemStack() {
            return itemStack;
        }
    }
}
