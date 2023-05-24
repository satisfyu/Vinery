package satisfyu.vinery.client.render.block.storage.api;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.vinery.block.entity.StorageBlockEntity;
import satisfyu.vinery.block.storage.api.StorageBlock;

public class StorageBlockEntityRenderer implements BlockEntityRenderer<StorageBlockEntity> {
	public StorageBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	public static void applyBlockAngle(PoseStack matrices, BlockState state, float angleOffset) {
		float angle = state.getValue(StorageBlock.FACING).toYRot();
		matrices.translate(0.5, 0, 0.5);
		matrices.mulPose(Vector3f.YP.rotationDegrees(angleOffset - angle));
	}

	@Override
	public void render(StorageBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		if (!entity.hasLevel()) {
			return;
		}
		BlockState state = entity.getBlockState();
		if (state.getBlock() instanceof StorageBlock sB) {
			NonNullList<ItemStack> itemStacks = entity.getInventory();
			matrices.pushPose();
			applyBlockAngle(matrices, state, 180);
			ResourceLocation type = sB.type();
			StorageBlock.getRendererForId(type).render(entity, matrices, vertexConsumers, itemStacks);
			matrices.popPose();
		}
	}
}
