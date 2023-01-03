package daniking.vinery.client.render.block;

import daniking.vinery.block.StorageBlock;
import daniking.vinery.block.WineBoxBlock;
import daniking.vinery.block.entity.GeckoStorageBlockEntity;
import daniking.vinery.item.DrinkBlockItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public class WineRackRenderer extends GeoBlockRenderer<GeckoStorageBlockEntity> {
    GeckoStorageBlockEntity entity;
    VertexConsumerProvider renderTypeBuffer;

    public WineRackRenderer() {
        super(new WineRackGeckoModel());
    }

    @Override
    public RenderLayer getRenderType(GeckoStorageBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        entity = animatable;
        this.renderTypeBuffer = renderTypeBuffer;
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack matrixStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("ref_points")) {
            matrixStack.push();
            matrixStack.translate(-0.46f, -0.455f, 0.035f);
            for (GeoBone b : bone.childBones) {
                matrixStack.push();
                RenderUtils.translate(b, matrixStack);
                RenderUtils.moveToPivot(b, matrixStack);
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));
                matrixStack.scale(0.9f, 0.9f, 0.9f); // Some mess
                if (entity.getCachedState().getBlock() instanceof WineBoxBlock) {
                    matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
                    matrixStack.translate(-0.43f, -0.92f, -0.1f);
                    matrixStack.scale(0.87f, 0.87f, 0.87f);
                } else if (entity.getCachedState().getBlock() instanceof StorageBlock) {
                    matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                    matrixStack.translate(0.56f, 0.45f, 0f);
                } // some more mess
                ItemStack itemStack = entity.getStack(Integer.parseInt(String.valueOf(b.getName().charAt(4))) - 1);
                if (!itemStack.isEmpty()) {
                    if (itemStack.getItem() instanceof  DrinkBlockItem) {
                        if (entity.getCachedState().getBlock() instanceof StorageBlock) {
                            matrixStack.translate(-0.5f, -0.1f, -0.6f);
                        }
                        BlockState bs = ((DrinkBlockItem) itemStack.getItem()).getBlock().getDefaultState();
                        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(bs, matrixStack, renderTypeBuffer, packedLightIn, packedOverlayIn);
                    } else {
                        if (entity.getCachedState().getBlock() instanceof StorageBlock && itemStack.getItem() instanceof BlockItem) {
                            matrixStack.translate(0f, -0.14f, 0f);
                        }
                        MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.GROUND, packedLightIn, packedOverlayIn, matrixStack, renderTypeBuffer, 0);
                    }
                }
                // Restart render buffer to continue rendering the rest of the wine rack
                renderTypeBuffer.getBuffer(RenderLayer.getEntityTranslucent(getTextureLocation(entity)));
                matrixStack.pop();
            }
            matrixStack.pop();
            return;
        }
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}