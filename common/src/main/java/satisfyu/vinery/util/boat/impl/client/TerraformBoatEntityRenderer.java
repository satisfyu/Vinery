package satisfyu.vinery.util.boat.impl.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.api.client.TerraformBoatClientHelper;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatHolder;

@Environment(EnvType.CLIENT)
public class TerraformBoatEntityRenderer extends BoatRenderer {
	private final Map<TerraformBoatType, Pair<ResourceLocation, BoatModel>> texturesAndModels;

	public TerraformBoatEntityRenderer(EntityRendererProvider.Context context, boolean chest) {
		super(context, chest);

		String prefix = chest ? "chest_boat/" : "boat/";
		this.texturesAndModels = TerraformBoatTypeRegistry.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getValue, entry -> {
			ResourceLocation id = entry.getKey();
			ResourceLocation textureId = new ResourceLocation(id.getNamespace(), "textures/entity/" + prefix + id.getPath() + ".png");

			ModelLayerLocation layer = TerraformBoatClientHelper.getLayer(id, chest);
			BoatModel model = new BoatModel(context.bakeLayer(layer), chest);

			return new Pair<>(textureId, model);
		}));
	}

	@Override
	public void render(Boat boat, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		if(boat instanceof TerraformBoatHolder holder){
			poseStack.pushPose();
			poseStack.translate(0.0, 0.375, 0.0);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
			float h = (float)boat.getHurtTime() - g;
			float j = boat.getDamage() - g;
			if (j < 0.0f) {
				j = 0.0f;
			}
			if (h > 0.0f) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0f * (float)boat.getHurtDir()));
			}
			if (!Mth.equal(boat.getBubbleAngle(g), 0.0f)) {
				poseStack.mulPose(new Quaternion(new Vector3f(1.0f, 0.0f, 1.0f), boat.getBubbleAngle(g), true));
			}
			Pair<ResourceLocation, BoatModel> pair = this.getTextureAndModel(holder);
			ResourceLocation resourceLocation = pair.getFirst();
			BoatModel boatModel = pair.getSecond();
			poseStack.scale(-1.0f, -1.0f, 1.0f);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0f));
			boatModel.setupAnim(boat, g, 0.0f, -0.1f, 0.0f, 0.0f);
			VertexConsumer vertexConsumer = multiBufferSource.getBuffer(boatModel.renderType(resourceLocation));
			boatModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
			if (!boat.isUnderWater()) {
				VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.waterMask());
				boatModel.waterPatch().render(poseStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY);
			}
			poseStack.popPose();
		}
		else super.render(boat, f, g, poseStack, multiBufferSource, i);
	}

	@Override
	public ResourceLocation getTextureLocation(Boat entity) {
		if (entity instanceof TerraformBoatHolder) {
			TerraformBoatType boat = ((TerraformBoatHolder) entity).getTerraformBoat();
			return this.texturesAndModels.get(boat).getFirst();
		}
		return super.getTextureLocation(entity);
	}

	public Pair<ResourceLocation, BoatModel> getTextureAndModel(TerraformBoatHolder holder) {
		return this.texturesAndModels.get(holder.getTerraformBoat());
	}
}
