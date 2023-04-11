package satisfyu.vinery.client.render.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.RotationAxis;
import satisfyu.vinery.block.FlowerBoxBlock;
import satisfyu.vinery.block.entity.FlowerBoxBlockEntity;

import static satisfyu.vinery.util.ClientUtil.renderBlock;


public class FlowerBoxBlockRenderer implements BlockEntityRenderer<FlowerBoxBlockEntity> {

    public FlowerBoxBlockRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(FlowerBoxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.hasWorld()) {
            return;
        }
        BlockState selfState = entity.getCachedState();
        if (selfState.getBlock() instanceof FlowerBoxBlock) {
            matrices.push();
            applyBlockAngle(matrices, selfState);
            matrices.translate(-0.25f, 0.25f, 0.25f);
            if (!entity.isSlotEmpty(0)) {
                BlockState state = ((BlockItem) entity.getFlower(0).getItem()).getBlock().getDefaultState();
                renderBlock(state, matrices, vertexConsumers, entity);
            }
            matrices.translate(0.5f, 0f, 0f);
            if (!entity.isSlotEmpty(1)) {
                BlockState state = ((BlockItem) entity.getFlower(1).getItem()).getBlock().getDefaultState();
                renderBlock(state, matrices, vertexConsumers, entity);
            }
            matrices.pop();
        }
    }

    public static void applyBlockAngle(MatrixStack matrices, BlockState state) {
        switch (state.get(FlowerBoxBlock.FACING)) {
            case EAST -> {
                matrices.translate(-0.5f, 0f, 1f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotation(90));
            }
            case SOUTH -> {
                matrices.translate(1f, 0f, 1f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotation(180));
            }
            case WEST -> {
                matrices.translate(1.5f, 0f, 0f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotation(270));
            }
        }
    }


}