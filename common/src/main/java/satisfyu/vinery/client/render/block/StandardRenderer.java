package satisfyu.vinery.client.render.block;

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
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.block.AbstractStandardBlock;
import satisfyu.vinery.block.AbstractStandardWallBlock;
import satisfyu.vinery.block.entity.StandardBlockEntity;

public class StandardRenderer implements BlockEntityRenderer<StandardBlockEntity>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Vinery.MODID, "standard"), "main");

    public static final String FLAG = "flag";
    private static final String POLE = "pole";
    private static final String BAR = "bar";
    private final ModelPart flag;
    private final ModelPart pole;
    private final ModelPart bar;

    public StandardRenderer(BlockEntityRendererProvider.Context context)
    {
        ModelPart modelPart = context.bakeLayer(LAYER_LOCATION);
        this.flag = modelPart.getChild(FLAG);
        this.pole = modelPart.getChild(POLE);
        this.bar = modelPart.getChild(BAR);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(FLAG, CubeListBuilder.create()
                .texOffs(0, 0).addBox(-10.0F, 0.0F, -1.0F, 20.0F, 40.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -44.0F, 0.0F, -0.0349F, 0.0F, 0.0F));

        partDefinition.addOrReplaceChild(POLE, CubeListBuilder.create().texOffs(44, 0).addBox(-1.0f, -30.0f, -1.0f, 2.0f, 42.0f, 2.0f), PartPose.ZERO);
        partDefinition.addOrReplaceChild(BAR, CubeListBuilder.create().texOffs(0, 42).addBox(-10.0f, -32.0f, -0.0f, 20.0f, 2.0f, 2.0f), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void render(StandardBlockEntity standard, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        long time;
        float scale = 0.66f;
        boolean inInventory = standard.getLevel() == null;

        poseStack.pushPose();
        if (inInventory)
        {
            time = 0L;
            poseStack.translate(0.5, 0.5, 0.5);
            this.pole.visible = true;
        }
        else
        {
            time = standard.getLevel().getGameTime();
            BlockState blockState = standard.getBlockState();
            float rotation;
            if (!(blockState.getBlock() instanceof AbstractStandardWallBlock))
            {
                poseStack.translate(0.5, 0.5, 0.5);
                rotation = (float)(-blockState.getValue(AbstractStandardBlock.ROTATION) * 360) / 16.0f;
                poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
                this.pole.visible = true;
            }
            else {
                poseStack.translate(0.5, -0.1666666716337204, 0.5);
                rotation = -blockState.getValue(AbstractStandardWallBlock.FACING).toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
                poseStack.translate(0.0, -0.3125, -0.4375);
                this.pole.visible = false;
            }
        }
        poseStack.pushPose();
        poseStack.scale(scale, -scale, -scale);
        VertexConsumer vertexConsumer = ModelBakery.BANNER_BASE.buffer(multiBufferSource, RenderType::entitySolid);
        this.pole.render(poseStack, vertexConsumer, i, j);
        this.bar.render(poseStack, vertexConsumer, i, j);
        BlockPos blockPos = standard.getBlockPos();
        float k = ((float)Math.floorMod(blockPos.getX() * 7L + blockPos.getY() * 9L + blockPos.getZ() * 13L + time, 100L) + f) / 100.0f;
        this.flag.xRot = (-0.0125f + 0.01f * Mth.cos((float)Math.PI * 2 * k)) * (float)Math.PI;
        this.flag.y = -32.0f;
        renderStandard(poseStack, multiBufferSource, i, j, this.flag, standard);
        poseStack.popPose();
        poseStack.popPose();
    }

    public static void renderStandard(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, ModelPart modelPart, StandardBlockEntity
            standard)
    {
        ResourceLocation location = ((AbstractStandardBlock)standard.getBlockState().getBlock()).getRenderTexture();
        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.entitySolid(location));

        modelPart.render(poseStack, vc, i, j);
    }
}
