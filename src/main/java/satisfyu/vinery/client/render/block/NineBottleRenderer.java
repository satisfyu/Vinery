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

public class NineBottleRenderer implements StorageTypeRenderer{
    @Override
    public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
        BlockRenderManager manager = MinecraftClient.getInstance().getBlockRenderManager();
        matrices.translate(-0.13, 0.335, 0.125);
        matrices.scale(0.9f, 0.9f, 0.9f);
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                matrices.push();
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
                matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90f));
                ClientUtil.renderBlock(blockItem.getBlock().getDefaultState().with(WineBottleBlock.COUNT, 0), matrices, vertexConsumers, entity);
                matrices.pop();
            }
        }
    }
}
