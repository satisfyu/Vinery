package daniking.vinery.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import daniking.vinery.block.StorageBlock;
import daniking.vinery.block.entity.StorageBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class StorageBlockEntityRenderer implements BlockEntityRenderer<StorageBlockEntity> {

    public StorageBlockEntityRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(StorageBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!entity.hasLevel()) {
            return;
        }
        BlockState state = entity.getBlockState();
        if(state.getBlock() instanceof StorageBlock sB){
            NonNullList<ItemStack> itemStacks = entity.getInventory();
            matrices.pushPose();
            applyBlockAngle(matrices, state, 180);
            ResourceLocation type = sB.type();
            StorageBlock.getRendererForId(type).render(entity, matrices, vertexConsumers, itemStacks);
            matrices.popPose();
        }
    }


    public static void applyBlockAngle(PoseStack matrices, BlockState state, float angleOffset) {
        float angle = state.getValue(StorageBlock.FACING).toYRot();
        matrices.translate(0.5, 0, 0.5);
        matrices.mulPose(Vector3f.YP.rotationDegrees(angleOffset - angle));
    }
}
