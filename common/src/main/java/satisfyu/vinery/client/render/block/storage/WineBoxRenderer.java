package satisfyu.vinery.client.render.block.storage;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.block.WineBottleBlock;
import satisfyu.vinery.block.entity.StorageBlockEntity;
import satisfyu.vinery.client.render.block.storage.api.StorageTypeRenderer;
import satisfyu.vinery.util.ClientUtil;

public class WineBoxRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        BlockRenderDispatcher manager = Minecraft.getInstance().getBlockRenderer();
        matrices.translate(0.35, 0.6, -0.35);
        matrices.scale(0.7f, 0.7f, 0.7f);
        ItemStack stack = itemStacks.get(0);
        if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
            matrices.mulPose(Vector3f.ZP.rotation(90f));

            matrices.mulPose(Vector3f.YN.rotation(90f));

            ClientUtil.renderBlock(blockItem.getBlock().defaultBlockState().setValue(WineBottleBlock.COUNT, 0), matrices, vertexConsumers, entity);
        }
    }
}
