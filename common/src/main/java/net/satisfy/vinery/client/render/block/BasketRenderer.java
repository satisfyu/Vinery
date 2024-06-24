package net.satisfy.vinery.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.block.BasketBlock;
import net.satisfy.vinery.block.entity.BasketBlockEntity;
import net.satisfy.vinery.util.VineryIdentifier;

import java.util.Objects;

public class BasketRenderer implements BlockEntityRenderer<BasketBlockEntity> {
    private static final ResourceLocation TEXTURE = new VineryIdentifier("textures/entity/basket.png");
    private final ModelPart lidleft;
    private final ModelPart lidright;
    private final ModelPart bottom;
    private final ModelPart handle;

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new VineryIdentifier("basket"), "main");

    public BasketRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(LAYER_LOCATION);
        this.bottom = modelPart.getChild("bottom");
        this.handle = modelPart.getChild("handle");
        this.lidright = modelPart.getChild("lidright");
        this.lidleft = modelPart.getChild("lidleft");
    }


    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bottom = partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -20.5F, 3.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 20.5F, 0.0F));

        PartDefinition handle = partdefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(2, 22).addBox(-2.5F, -7.5F, 7.0F, 12.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).mirror().addBox(9.51F, -15.5F, 7.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 21).mirror().addBox(-2.51F, -15.5F, 7.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.5F, 20.5F, 0.0F));

        PartDefinition lidright = partdefinition.addOrReplaceChild("lidright", CubeListBuilder.create().texOffs(0, 15).mirror().addBox(2.0F, -19.0F, 3.0F, 12.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition lidleft = partdefinition.addOrReplaceChild("lidleft", CubeListBuilder.create().texOffs(0, 15).mirror().addBox(2.0F, -19.0F, 8.0F, 12.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 48, 48);
    }

    @Override
    public void render(BasketBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        float g = 0;

        if(blockEntity.hasLevel()){
            BlockState blockState = Objects.requireNonNull(blockEntity.getLevel()).getBlockState(blockEntity.getBlockPos());
            if(blockState.getBlock() instanceof BasketBlock){
                g = blockState.getValue(BasketBlock.FACING).toYRot();
            }
        }

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-g));
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        float openNess = blockEntity.getOpenNess(f);
        openNess = 1.0F - openNess;
        openNess = 1.0F - openNess * openNess * openNess;
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));

        this.renderLid(poseStack, vertexConsumer, this.lidleft, openNess, i, j, false);
        this.renderLid(poseStack, vertexConsumer, this.lidright, openNess, i, j, true);
        this.bottom.render(poseStack, vertexConsumer, i, j);
        this.handle.render(poseStack,vertexConsumer,i,j);
        this.renderLid(poseStack, vertexConsumer, this.lidleft, openNess, i, j, false);
        this.renderLid(poseStack, vertexConsumer, this.lidright, openNess, i, j, true);

        poseStack.popPose();
    }


    private void renderLid(PoseStack poseStack, VertexConsumer vertexConsumer, ModelPart lid, float openNess, int i, int j, boolean isMirrored) {
        float lidRotationAngle = -(openNess * 0.2F);
        float lidVerticalMovement = openNess * -1.8F;

        if (isMirrored) {
            lidRotationAngle = -lidRotationAngle;
            lidVerticalMovement = -lidVerticalMovement;
        }

        lid.xRot = lidRotationAngle;
        lid.y = lid.y + lidVerticalMovement;
        lid.render(poseStack, vertexConsumer, i, j);
        lid.y = lid.y - lidVerticalMovement;
    }

    public ModelPart getTop() {
        return this.lidleft;
    }

    public ModelPart getBottom() {
        return this.bottom;
    }
}