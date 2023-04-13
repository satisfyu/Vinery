package satisfyu.vinery.client.render.block;

import net.minecraft.util.math.RotationAxis;
import satisfyu.vinery.block.StorageBlock;
import satisfyu.vinery.block.entity.StorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class StorageBlockEntityRenderer implements BlockEntityRenderer<StorageBlockEntity> {

    public StorageBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }

    @Override
    public void render(StorageBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.hasWorld()) {
            return;
        }
        BlockState state = entity.getCachedState();
        if(state.getBlock() instanceof StorageBlock sB){
            DefaultedList<ItemStack> itemStacks = entity.getInventory();
            matrices.push();
            applyBlockAngle(matrices, state, 180);
            Identifier type = sB.type();
            StorageBlock.getRendererForId(type).render(entity, matrices, vertexConsumers, itemStacks);
            matrices.pop();
        }
    }


    public static void applyBlockAngle(MatrixStack matrices, BlockState state, float angleOffset) {
        float angle = state.get(StorageBlock.FACING).asRotation();
        matrices.translate(0.5, 0, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angleOffset - angle));
    }
}
