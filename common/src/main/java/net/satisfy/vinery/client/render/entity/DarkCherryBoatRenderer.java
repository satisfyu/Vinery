package net.satisfy.vinery.client.render.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.entity.DarkCherryBoatEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.Map;
import java.util.stream.Stream;

public class DarkCherryBoatRenderer<T extends DarkCherryBoatEntity> extends EntityRenderer<T> {
    private final Map<DarkCherryBoatEntity.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public DarkCherryBoatRenderer(EntityRendererProvider.Context context, boolean hasChest) {
        super(context);
        this.shadowRadius = 0.8f;
        this.boatResources = Stream.of(DarkCherryBoatEntity.Type.values()).collect(ImmutableMap.toImmutableMap(type -> type, type ->
                Pair.of(type.getTexture(hasChest), this.createBoatModel(context, type, hasChest))));
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, DarkCherryBoatEntity.Type type, boolean hasChest) {
        ModelLayerLocation modelLayerLocation = hasChest ?
                new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, type.getChestModelLocation()), "main")
                : new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, type.getModelLocation()), "main");
        ModelPart modelPart = context.bakeLayer(modelLayerLocation);
        return hasChest ? new ChestBoatModel(modelPart) : new BoatModel(modelPart);
    }

    @Override
    @SuppressWarnings("unused")
    public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        float h;
        matrixStack.pushPose();
        matrixStack.translate(0.0f, 0.375f, 0.0f);
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0f - entityYaw));
        float f = (float) entity.getHurtTime() - partialTicks;
        float g = entity.getDamage() - partialTicks;
        if (g < 0.0f) {
            g = 0.0f;
        }
        if (f > 0.0f) {
            matrixStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * g / 10.0f * (float) entity.getHurtDir()));
        }
        if (!Mth.equal(entity.getBubbleAngle(partialTicks), 0.0f)) {
            matrixStack.mulPose(new Quaternionf().setAngleAxis(entity.getBubbleAngle(partialTicks) * ((float) Math.PI / 180), 1.0f, 0.0f, 1.0f));
        }
        Pair<ResourceLocation, ListModel<Boat>> pair = this.boatResources.get(entity.getWoodType());
        ResourceLocation resourceLocation = pair.getFirst();
        ListModel<Boat> listModel = pair.getSecond();
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.mulPose(Axis.YP.rotationDegrees(90.0f));
        listModel.setupAnim(entity, partialTicks, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = buffer.getBuffer(listModel.renderType(resourceLocation));
        listModel.renderToBuffer(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1);
        if (!entity.isUnderWater()) {
            VertexConsumer vertexConsumer2 = buffer.getBuffer(RenderType.waterMask());
            if (listModel instanceof WaterPatchModel waterPatchModel) {
                waterPatchModel.waterPatch().render(matrixStack, vertexConsumer2, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DarkCherryBoatEntity boat) {
        return boatResources.get(boat.getWoodType()).getFirst();
    }
}