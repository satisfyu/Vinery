package satisfyu.vinery.client.render.block;

import satisfyu.vinery.block.WineBottleBlock;
import satisfyu.vinery.block.entity.StorageBlockEntity;
import satisfyu.vinery.util.ClientUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3f;

public class WineBoxRenderer implements StorageTypeRenderer{
    @Override
    public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
        BlockRenderManager manager = MinecraftClient.getInstance().getBlockRenderManager();
        matrices.translate(0.35, 0.6, -0.35);
        matrices.scale(0.7f, 0.7f, 0.7f);
        ItemStack stack = itemStacks.get(0);
        if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90f));

            matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(90f));

            ClientUtil.renderBlock(blockItem.getBlock().getDefaultState().with(WineBottleBlock.COUNT, 0), matrices, vertexConsumers, entity);
        }
    }
}
