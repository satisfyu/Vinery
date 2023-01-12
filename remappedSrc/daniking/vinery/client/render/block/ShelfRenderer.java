package daniking.vinery.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import daniking.vinery.block.entity.StorageBlockEntity;
import daniking.vinery.util.ClientUtil;
import daniking.vinery.util.VineryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public class ShelfRenderer implements StorageTypeRenderer{
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        matrices.translate(-0.4, 0.5, 0.25);
        matrices.mulPose(Vector3f.YP.rotationDegrees(90f));
        matrices.scale(0.5f, 0.5f, 0.5f);

        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty()) {
                matrices.pushPose();
                matrices.translate(0f, 0f, 0.2f * i);
                matrices.mulPose(Vector3f.YN.rotationDegrees(22.5f));
                ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                matrices.popPose();
            }
        }
    }
}
