package satisfyu.vinery.util.boat.impl.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.api.client.TerraformBoatClientHelper;
import satisfyu.vinery.util.boat.impl.entity.MyHolder;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class MyBoatEntityRenderer extends BoatRenderer {

	private final Map<TerraformBoatType, Pair<ResourceLocation, ListModel<Boat>>> texturesAndModels;

	public MyBoatEntityRenderer(EntityRendererProvider.Context context, boolean chest) {
		super(context, chest);

		this.texturesAndModels = TerraformBoatTypeRegistry.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getValue, entry -> {
			boolean raft = entry.getValue().isRaft();
			String prefix = raft ? (chest ? "chest_raft/" : "raft/") : (chest ? "chest_boat/" : "boat/");

			ResourceLocation id = entry.getValue().getKey();
			ResourceLocation textureId = new ResourceLocation(id.getNamespace(), "textures/entity/" + prefix + id.getPath() + ".png");

			ModelLayerLocation layer = TerraformBoatClientHelper.getLayer(id, raft, chest);
			ListModel<Boat> model = createModel(context.bakeLayer(layer), raft, chest);

			return new Pair<>(textureId, model);
		}));
	}

	@Override
	public void render(Boat boat, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		if(boat instanceof MyHolder myHolder){
			poseStack.pushPose();
			poseStack.translate(0.0f, 0.375f, 0.0f);
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - f));
			float h = (float)boat.getHurtTime() - g;
			float j = boat.getDamage() - g;
			if (j < 0.0f) {
				j = 0.0f;
			}
			if (h > 0.0f) {
				poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0f * (float)boat.getHurtDir()));
			}
			if (!Mth.equal(boat.getBubbleAngle(g), 0.0f)) {
				poseStack.mulPose(new Quaternionf().setAngleAxis(boat.getBubbleAngle(g) * ((float)Math.PI / 180), 1.0f, 0.0f, 1.0f));
			}
			Pair<ResourceLocation, ListModel<Boat>> pair = this.getTextureAndModel(myHolder);
			ResourceLocation resourceLocation = pair.getFirst();
			ListModel<Boat> listModel = pair.getSecond();
			poseStack.scale(-1.0f, -1.0f, 1.0f);
			poseStack.mulPose(Axis.YP.rotationDegrees(90.0f));
			listModel.setupAnim(boat, g, 0.0f, -0.1f, 0.0f, 0.0f);
			VertexConsumer vertexConsumer = multiBufferSource.getBuffer(listModel.renderType(resourceLocation));
			listModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
			if (!boat.isUnderWater()) {
				VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.waterMask());
				if (listModel instanceof WaterPatchModel waterPatchModel) {
					waterPatchModel.waterPatch().render(poseStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY);
				}
			}
			poseStack.popPose();
		}
	}




	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Boat entity) {
		if (entity instanceof MyHolder) {
			TerraformBoatType boat = ((MyHolder) entity).getTerraformBoat();
			return this.texturesAndModels.get(boat).getFirst();
		}
		return super.getTextureLocation(entity);
	}

	public Pair<ResourceLocation, ListModel<Boat>> getTextureAndModel(MyHolder holder) {
		return this.texturesAndModels.get(holder.getTerraformBoat());
	}

	private ListModel<Boat> createModel(ModelPart part, boolean raft, boolean chest) {
		if (raft) {
			return chest ? new ChestRaftModel(part) : new RaftModel(part);
		} else {
			return chest ? new ChestBoatModel(part) : new BoatModel(part);
		}
	}
}
