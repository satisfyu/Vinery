package satisfyu.vinery.client.render.block;

import net.minecraft.util.math.RotationAxis;
import satisfyu.vinery.block.WineBottleBlock;
import satisfyu.vinery.block.entity.StorageBlockEntity;
import satisfyu.vinery.util.ClientUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class FourBottleRenderer implements StorageTypeRenderer{
    @Override
    public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {
        matrices.translate(-0.13, 0.335, 0.125);
        matrices.scale(0.9f, 0.9f, 0.9f);
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                matrices.push();
                if(i == 0){
                    matrices.translate(-0.35f, 0, 0f);
                }
                else if(i == 1){
                    matrices.translate(0, -0.33f, 0f);
                }
                else if(i == 2){
                    matrices.translate(-0.7f, -0.33f, 0f);
                }
                else if(i == 3){
                    matrices.translate(-0.35f, -0.66f, 0f);
                }
                else {
                    matrices.pop();
                    continue;
                }
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
                ClientUtil.renderBlock(blockItem.getBlock().getDefaultState().with(WineBottleBlock.COUNT, 0), matrices, vertexConsumers, entity);
                matrices.pop();
            }
        }
    }
}
