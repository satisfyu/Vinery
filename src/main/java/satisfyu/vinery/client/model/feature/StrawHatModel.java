package satisfyu.vinery.client.model.feature;

import satisfyu.vinery.VineryIdentifier;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class StrawHatModel<T extends Entity> extends EntityModel<T> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new VineryIdentifier("straw_hat"), "main");

	private final ModelPart top_part;
	public StrawHatModel(ModelPart root) {
		this.top_part = root.getChild("top_part");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData top_part = modelPartData.addChild("top_part", ModelPartBuilder.create().uv(0, 1).cuboid(-4.0F, -4.01F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		ModelPartData lower_part = top_part.addChild("lower_part", ModelPartBuilder.create().uv(0, 13).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 1.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		top_part.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}