package daniking.vinery.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import daniking.vinery.Vinery;
import daniking.vinery.block.entity.StorageBlockEntity;
import daniking.vinery.util.ClientUtil;
import daniking.vinery.util.VineryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class NineBottleRenderer implements StorageTypeRenderer{
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        BlockRenderDispatcher manager = Minecraft.getInstance().getBlockRenderer();
        matrices.translate(-0.13, 0.335, 0.125);
        matrices.scale(0.9f, 0.9f, 0.9f);
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                matrices.pushPose();
                int line = i >= 6 ? 3 : i >= 3 ? 2 : 1;
                float x;
                float y;
                if(line == 1){
                    x = -0.35f * i;
                    y = 0;
                }
                else if(line == 2){
                    x = -0.35f * (i - 3);
                    y = -0.33f;
                }
                else{
                    x = -0.35f * (i - 6);
                    y = -0.66f;
                }
                matrices.translate(x, y, 0f);
                matrices.mulPose(Vector3f.XN.rotationDegrees(90f));
                ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
                matrices.popPose();
            }
        }
    }
}
