package satisfyu.vinery.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import satisfyu.vinery.util.VineryIdentifier;

public class StrawHatModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new VineryIdentifier("straw_hat"), "main");
    private final ModelPart top_part;

    public StrawHatModel(ModelPart root) {
        this.top_part = root.getChild("top_part");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition brewfest_hat = partdefinition.addOrReplaceChild("brewfest_hat", CubeListBuilder.create().texOffs(-14, 15).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -4.2F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(-1, 4).addBox(-4.05F, -2.21F, -4.05F, 8.1F, 2.0F, 8.1F, new CubeDeformation(0.2F))
                .texOffs(22, 22).addBox(4.21F, -7.4F, -1.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(1.05F, 1.05F, 1.05F);
        top_part.render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(T entity, float f, float g, float h, float i, float j) {

    }

    public void copyHead(ModelPart model) {
        top_part.copyFrom(model);
    }
}
