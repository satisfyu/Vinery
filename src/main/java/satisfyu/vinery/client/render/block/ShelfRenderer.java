package satisfyu.vinery.client.render.block;

import net.minecraft.util.math.RotationAxis;
import satisfyu.vinery.block.entity.StorageBlockEntity;
import satisfyu.vinery.util.ClientUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class ShelfRenderer implements StorageTypeRenderer{
    @Override
    public void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks) {

        matrices.translate(-0.4, 0.5, 0.25);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(90));
        matrices.scale(0.5f, 0.5f, 0.5f);

        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty()) {
                matrices.push();
                matrices.translate(0f, 0f, 0.2f * i);
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotation(90));
                ClientUtil.renderItem(stack, matrices, vertexConsumers, entity, entity.getWorld());
                matrices.pop();
            }
        }
    }
}
