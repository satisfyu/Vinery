package satisfyu.vinery.client.render.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import satisfyu.vinery.block.FlowerPotBlock;
import satisfyu.vinery.block.entity.FlowerPotBlockEntity;

import static satisfyu.vinery.util.ClientUtil.renderBlock;

public class FlowerPotBlockEntityRenderer implements BlockEntityRenderer<FlowerPotBlockEntity> {

    public FlowerPotBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(FlowerPotBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.hasWorld()) {
            return;
        }
        BlockState selfState = entity.getCachedState();
        if (selfState.getBlock() instanceof FlowerPotBlock) {
            Item item = entity.getFlower();
            matrices.push();
            if (item instanceof BlockItem) {
                BlockState state = ((BlockItem) item).getBlock().getDefaultState();
                matrices.translate(0f, 0.4f, 0f);
                renderBlock(state, matrices, vertexConsumers, entity);
                state = ((BlockItem) item).getBlock().getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER);
                matrices.translate(0f, 1f, 0f);
                renderBlock(state, matrices, vertexConsumers, entity);
            }
        }
        matrices.pop();
    }
}