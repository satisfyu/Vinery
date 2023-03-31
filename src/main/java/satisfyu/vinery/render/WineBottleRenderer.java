package satisfyu.vinery.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import satisfyu.vinery.block.WineBottleBlock;
import satisfyu.vinery.block.entity.WineBottleBlockEntity;
import satisfyu.vinery.registry.ObjectRegistry;

import static satisfyu.vinery.block.WineBottleBlock.COUNT;
import static satisfyu.vinery.util.ClientUtil.renderBlock;

public class WineBottleRenderer implements BlockEntityRenderer<WineBottleBlockEntity> {

    public WineBottleRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(WineBottleBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.hasWorld()) {
            return;
        }
        BlockState selfState = entity.getCachedState();
        if (selfState.getBlock() instanceof WineBottleBlock) {
            BlockState defaultState = selfState.getBlock().getDefaultState().with(COUNT, 0);
            matrices.push();
            applyBlockAngle(matrices, selfState);
            switch (entity.getCount()) {
                default -> renderOne(entity, matrices, vertexConsumers, defaultState);
                case 2 -> renderTwo(entity, matrices, vertexConsumers, defaultState);
                case 3 -> renderThree(entity, matrices, vertexConsumers, defaultState);
            }
            matrices.pop();
        }

    }

    public static void applyBlockAngle(MatrixStack matrices, BlockState state) {
        switch (state.get(WineBottleBlock.FACING)) {
            case NORTH -> {
                matrices.translate(0f, 0f, 1f);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
            }
            case WEST -> {
                matrices.translate(1f, 0f, 1f);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            }
            case SOUTH -> {
                matrices.translate(1f, 0f, 0f);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
            }
        }
    }

    private void renderOne(WineBottleBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, BlockState defaultState) {
        renderBlock(defaultState, matrices, vertexConsumers, entity);
    }

    private void renderTwo(WineBottleBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, BlockState defaultState) {
        matrices.translate(-0.15f, 0f, -0.25f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        matrices.translate(.1f, 0f, .8f);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(30));
        renderBlock(defaultState, matrices, vertexConsumers, entity);
}

    private void renderThree(WineBottleBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, BlockState defaultState) {
        matrices.translate(-0.25f, 0f, -0.25f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        matrices.translate(.15f, 0f, .5f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        if (defaultState.getBlock() == ObjectRegistry.KELP_CIDER) {
            matrices.translate(.35f, .7f, -.13f);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
            renderBlock(defaultState, matrices, vertexConsumers, entity);
            return;
        }
        matrices.translate(.1f, 0f, 0f);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(30));
        renderBlock(defaultState, matrices, vertexConsumers, entity);
    }
}