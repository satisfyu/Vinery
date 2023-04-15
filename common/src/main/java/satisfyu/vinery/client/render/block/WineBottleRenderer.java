package satisfyu.vinery.client.render.block;

import satisfyu.vinery.block.WineBottleBlock;
import satisfyu.vinery.block.entity.WineBottleBlockEntity;
import satisfyu.vinery.registry.ObjectRegistry;

import static satisfyu.vinery.block.WineBottleBlock.COUNT;
import static satisfyu.vinery.util.ClientUtil.renderBlock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class WineBottleRenderer implements BlockEntityRenderer<WineBottleBlockEntity> {

    public WineBottleRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(WineBottleBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!entity.hasLevel()) {
            return;
        }
        BlockState selfState = entity.getBlockState();
        if (selfState.getBlock() instanceof WineBottleBlock) {
            BlockState defaultState = selfState.getBlock().defaultBlockState().setValue(COUNT, 0);
            matrices.pushPose();
            applyBlockAngle(matrices, selfState);
            switch (entity.getCount()) {
                default -> renderOne(entity, matrices, vertexConsumers, defaultState);
                case 2 -> renderTwo(entity, matrices, vertexConsumers, defaultState);
                case 3 -> renderThree(entity, matrices, vertexConsumers, defaultState);
            }
            matrices.popPose();
        }

    }

    public static void applyBlockAngle(PoseStack matrices, BlockState state) {
        switch (state.getValue(WineBottleBlock.FACING)) {
            case NORTH -> {
                matrices.translate(0f, 0f, 1f);
                matrices.mulPose(Axis.YP.rotation(90));
            }
            case WEST -> {
                matrices.translate(1f, 0f, 1f);
                matrices.mulPose(Axis.YP.rotation(180));
            }
            case SOUTH -> {
                matrices.translate(1f, 0f, 0f);
                matrices.mulPose(Axis.YP.rotation(270));
            }
        }
    }

    private void renderOne(WineBottleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, BlockState defaultState) {
        renderBlock(defaultState, matrices, vertexConsumers, entity);
    }

    private void renderTwo(WineBottleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, BlockState defaultState) {
        matrices.translate(-0.15f, 0f, -0.25f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        matrices.translate(.1f, 0f, .8f);
        matrices.mulPose(Axis.YP.rotation(30));
        renderBlock(defaultState, matrices, vertexConsumers, entity);
}

    private void renderThree(WineBottleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, BlockState defaultState) {
        matrices.translate(-0.25f, 0f, -0.25f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        matrices.translate(.15f, 0f, .5f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        if (defaultState.getBlock() == ObjectRegistry.KELP_CIDER) {
            matrices.translate(.35f, .7f, -.13f);
            matrices.mulPose(Axis.YP.rotation(90));
            renderBlock(defaultState, matrices, vertexConsumers, entity);
            return;
        }
        matrices.translate(.1f, 0f, 0f);
        matrices.mulPose(Axis.YP.rotation(30));
        renderBlock(defaultState, matrices, vertexConsumers, entity);
    }
}