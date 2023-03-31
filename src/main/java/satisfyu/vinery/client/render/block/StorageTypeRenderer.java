package satisfyu.vinery.client.render.block;

import satisfyu.vinery.block.entity.StorageBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface StorageTypeRenderer {
    void render(StorageBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks);
}
