package daniking.vinery.client.render.feature;

import daniking.vinery.VineryIdentifier;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class StrawHatModel<T extends Entity> extends EntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new VineryIdentifier("straw_hat"), "main");
	private final ModelPart root;

	public StrawHatModel(ModelPart root) {
		this.root = root.getChild(EntityModelPartNames.HAT);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData meshdefinition = new ModelData();
		ModelPartData modelPartData = meshdefinition.getRoot();

		ModelPartData top_part = modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 1).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData lower_part = top_part.addChild(EntityModelPartNames.HAT_RIM, ModelPartBuilder.create().uv(-16, 13).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		//partdefinition.addChild("head", ModelPartBuilder.create().uv(-16, 14).cuboid(-16.0F, 0.0F, 0.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)).uv(2, 2).mirrored().cuboid(-12.0F, -4.01F, 4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(8.0F, 24.0F, -8.0F));

		//modelPartData.addChild("head", ModelPartBuilder.create().uv(-16, 14).cuboid(-8.0F, -7.0F, -8.0F, 16.0F, 0.0F, 16.0F).uv(2, 1).cuboid(-4.5F, -11.001F, -4.5F, 9.0F, 4.0F, 9.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		return TexturedModelData.of(meshdefinition, 64, 64);
	}


	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}