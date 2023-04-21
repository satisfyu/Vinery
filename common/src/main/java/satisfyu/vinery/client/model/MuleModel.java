package satisfyu.vinery.client.model;

import satisfyu.vinery.entity.TraderMuleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class MuleModel extends EntityModel<TraderMuleEntity> {

	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart head;

	private final ModelPart Neck;
	private final ModelPart Bag1;
	private final ModelPart Bag2;
	private final ModelPart Saddle;
	private final ModelPart bb_main;

	public MuleModel(ModelPart root) {
		this.body = root.getChild("Body");
		this.tail = root.getChild("TailA");
		this.leftFrontLeg = root.getChild("Leg1A");
		this.rightFrontLeg = root.getChild("Leg2A");
		this.leftHindLeg = root.getChild("Leg3A");
		this.rightHindLeg = root.getChild("Leg4A");
		this.head = root.getChild("Head");
		this.Neck = root.getChild("Neck");
		this.Bag1 = root.getChild("Bag1");
		this.Bag2 = root.getChild("Bag2");
		this.Saddle = root.getChild("Saddle");
		this.bb_main = root.getChild("bb_main");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition Body = modelPartData.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.0F, -20.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 9.0F));

		PartDefinition TailA = modelPartData.addOrReplaceChild("TailA", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 11.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition Leg1A = modelPartData.addOrReplaceChild("Leg1A", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 13.0F, 9.0F));

		PartDefinition Leg2A = modelPartData.addOrReplaceChild("Leg2A", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 13.0F, 9.0F));

		PartDefinition Leg3A = modelPartData.addOrReplaceChild("Leg3A", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 13.0F, -9.0F));

		PartDefinition Leg4A = modelPartData.addOrReplaceChild("Leg4A", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 13.0F, -9.0F));

		PartDefinition Head = modelPartData.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 25).addBox(-2.0F, -5.0F, -11.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -11.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition headstuff = Head.addOrReplaceChild("headstuff", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition Ear1 = headstuff.addOrReplaceChild("Ear1", CubeListBuilder.create().texOffs(19, 16).mirror().addBox(-0.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.0873F));

		PartDefinition Ear2 = headstuff.addOrReplaceChild("Ear2", CubeListBuilder.create().texOffs(19, 16).addBox(-1.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, -0.0873F));

		PartDefinition MuleEarL = headstuff.addOrReplaceChild("MuleEarL", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-3.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.2618F));

		PartDefinition MuleEarR = headstuff.addOrReplaceChild("MuleEarR", CubeListBuilder.create().texOffs(0, 12).addBox(1.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, -0.2618F));

		PartDefinition SaddleMouthL = headstuff.addOrReplaceChild("SaddleMouthL", CubeListBuilder.create().texOffs(29, 5).addBox(2.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition SaddleMouthR = headstuff.addOrReplaceChild("SaddleMouthR", CubeListBuilder.create().texOffs(29, 5).addBox(-3.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition HeadSaddle = headstuff.addOrReplaceChild("HeadSaddle", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -16.0F, -5.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(0, 0).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition Neck = modelPartData.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -11.0F, -3.0F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition Bag1 = modelPartData.addOrReplaceChild("Bag1", CubeListBuilder.create().texOffs(26, 21).addBox(-9.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, 11.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Bag2 = modelPartData.addOrReplaceChild("Bag2", CubeListBuilder.create().texOffs(26, 21).mirror().addBox(1.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 3.0F, 11.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Saddle = modelPartData.addOrReplaceChild("Saddle", CubeListBuilder.create().texOffs(26, 0).addBox(-5.0F, 1.0F, -5.5F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 2.0F, 2.0F));

		PartDefinition bb_main = modelPartData.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(32, 64).addBox(5.0F, -20.0F, -4.0F, 6.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 84).addBox(-8.0F, -27.0F, 6.0F, 16.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bag_side_1_r1 = bb_main.addOrReplaceChild("bag_side_1_r1", CubeListBuilder.create().texOffs(0, 64).addBox(5.0F, -20.0F, -6.0F, 6.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
		return LayerDefinition.create(modelData, 128, 128);
	}



	@Override
	public void setupAnim(TraderMuleEntity entity, float f, float g, float h, float i, float j) {

	}

	@Override
	public void prepareMobModel(TraderMuleEntity entity, float f, float g, float h) {
		float i = Mth.rotLerp(h, entity.yBodyRotO, entity.yBodyRot);
		float j = Mth.rotLerp(h, entity.yHeadRotO, entity.yHeadRot);
		float k = Mth.lerp(h, entity.xRotO, entity.getXRot());
		float l = j - i;
		float m = k * ((float)Math.PI / 180);
		if (l > 20.0f) {
			l = 20.0f;
		}
		if (l < -20.0f) {
			l = -20.0f;
		}
		if (g > 0.2f) {
			m += Mth.cos(f * 0.4f) * 0.15f * g;
		}
		float n = entity.getEatAnim(h);
		float o = entity.getStandAnim(h);
		float p = 1.0f - o;
		float q = entity.getMouthAnim(h);
		boolean bl = entity.tailCounter != 0;
		float r = (float) entity.tickCount + h;
		this.body.xRot = 0.0f;
		this.head.xRot = 0.5235988f + m;
		this.head.yRot = l * ((float)Math.PI / 180);
		float s = entity.isInWater() ? 0.2f : 1.0f;
		float t = Mth.cos(s * f * 0.6662f + (float)Math.PI);
		float u = t * 0.8f * g;
		float v = (1.0f - Math.max(o, n)) * (0.5235988f + m + q * Mth.sin(r) * 0.05f);
		this.head.xRot = o * (0.2617994f + m) + n * (2.1816616f + Mth.sin(r) * 0.05f) + v;
		this.head.yRot = o * l * ((float)Math.PI / 180) + (1.0f - Math.max(o, n)) * this.head.yRot;
		this.head.y = o * -4.0f + n * 11.0f + (1.0f - Math.max(o, n)) * this.head.y;
		this.head.z = o * -4.0f + n * -12.0f + (1.0f - Math.max(o, n)) * this.head.z;
		this.body.xRot = o * -0.7853982f + p * this.body.xRot;
		float w = 0.2617994f * o;
		float x = Mth.cos(r * 0.6f + (float)Math.PI);


		float y = (-1.0471976f + x) * o + u * p;
		float z = (-1.0471976f - x) * o - u * p;
		this.leftHindLeg.xRot = w - t * 0.5f * g * p;
		this.rightHindLeg.xRot = w + t * 0.5f * g * p;
		this.leftFrontLeg.xRot = y;
		this.rightFrontLeg.xRot = z;
		this.tail.xRot = 0.5235988f + g * 0.75f;

		this.tail.yRot = bl ? Mth.cos(r * 0.7f) : 0.0f;
	}


	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftFrontLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightFrontLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftHindLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightHindLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Neck.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Bag1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Bag2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Saddle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	
}