package daniking.vinery.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import daniking.vinery.block.entity.StorageBlockEntity;
import daniking.vinery.util.ClientUtil;
import daniking.vinery.util.VineryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class WineBoxRenderer implements StorageTypeRenderer{
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        BlockRenderDispatcher manager = Minecraft.getInstance().getBlockRenderer();
        matrices.translate(0.35, 0.6, -0.35);
        matrices.scale(0.7f, 0.7f, 0.7f);
        ItemStack stack = itemStacks.get(0);
        if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
            matrices.mulPose(Vector3f.ZP.rotationDegrees(90f));

            matrices.mulPose(Vector3f.YN.rotationDegrees(90f));

            ClientUtil.renderBlockFromItem(blockItem, matrices, vertexConsumers, entity);
        }
    }
}
