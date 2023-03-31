package satisfyu.vinery.client.model;

import satisfyu.vinery.entity.TraderMuleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

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
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Body = modelPartData.addChild("Body", ModelPartBuilder.create().uv(0, 32).cuboid(-5.0F, -8.0F, -20.0F, 10.0F, 10.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 11.0F, 9.0F));

		ModelPartData TailA = modelPartData.addChild("TailA", ModelPartBuilder.create().uv(42, 36).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.0F, 11.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData Leg1A = modelPartData.addChild("Leg1A", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, 13.0F, 9.0F));

		ModelPartData Leg2A = modelPartData.addChild("Leg2A", ModelPartBuilder.create().uv(48, 21).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 13.0F, 9.0F));

		ModelPartData Leg3A = modelPartData.addChild("Leg3A", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, 13.0F, -9.0F));

		ModelPartData Leg4A = modelPartData.addChild("Leg4A", ModelPartBuilder.create().uv(48, 21).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 13.0F, -9.0F));

		ModelPartData Head = modelPartData.addChild("Head", ModelPartBuilder.create().uv(0, 13).cuboid(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F))
				.uv(0, 25).cuboid(-2.0F, -5.0F, -11.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, -11.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData headstuff = Head.addChild("headstuff", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData Ear1 = headstuff.addChild("Ear1", ModelPartBuilder.create().uv(19, 16).mirrored().cuboid(-0.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.0873F));

		ModelPartData Ear2 = headstuff.addChild("Ear2", ModelPartBuilder.create().uv(19, 16).cuboid(-1.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, -0.0873F));

		ModelPartData MuleEarL = headstuff.addChild("MuleEarL", ModelPartBuilder.create().uv(0, 12).mirrored().cuboid(-3.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.2618F));

		ModelPartData MuleEarR = headstuff.addChild("MuleEarR", ModelPartBuilder.create().uv(0, 12).cuboid(1.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, -0.2618F));

		ModelPartData SaddleMouthL = headstuff.addChild("SaddleMouthL", ModelPartBuilder.create().uv(29, 5).cuboid(2.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData SaddleMouthR = headstuff.addChild("SaddleMouthR", ModelPartBuilder.create().uv(29, 5).cuboid(-3.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData HeadSaddle = headstuff.addChild("HeadSaddle", ModelPartBuilder.create().uv(19, 0).cuboid(-2.0F, -16.0F, -5.0F, 4.0F, 5.0F, 2.0F, new Dilation(0.25F))
				.uv(0, 0).cuboid(-3.0F, -16.0F, -3.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.25F)), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData Neck = modelPartData.addChild("Neck", ModelPartBuilder.create().uv(0, 35).cuboid(-2.0F, -11.0F, -3.0F, 4.0F, 12.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData Bag1 = modelPartData.addChild("Bag1", ModelPartBuilder.create().uv(26, 21).cuboid(-9.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 3.0F, 11.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData Bag2 = modelPartData.addChild("Bag2", ModelPartBuilder.create().uv(26, 21).mirrored().cuboid(1.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, 3.0F, 11.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData Saddle = modelPartData.addChild("Saddle", ModelPartBuilder.create().uv(26, 0).cuboid(-5.0F, 1.0F, -5.5F, 10.0F, 9.0F, 9.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 2.0F, 2.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(32, 64).cuboid(5.0F, -20.0F, -4.0F, 6.0F, 10.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 84).cuboid(-8.0F, -27.0F, 6.0F, 16.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData bag_side_1_r1 = bb_main.addChild("bag_side_1_r1", ModelPartBuilder.create().uv(0, 64).cuboid(5.0F, -20.0F, -6.0F, 6.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(TraderMuleEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void animateModel(TraderMuleEntity abstractHorseEntity, float f, float g, float h) {
		float i = MathHelper.lerpAngle(abstractHorseEntity.prevBodyYaw, abstractHorseEntity.bodyYaw, h);
		float j = MathHelper.lerpAngle(abstractHorseEntity.prevHeadYaw, abstractHorseEntity.headYaw, h);
		float k = MathHelper.lerp(h, abstractHorseEntity.prevPitch, abstractHorseEntity.getPitch());
		float l = j - i;
		float m = k * ((float)Math.PI / 180);
		if (l > 20.0f) {
			l = 20.0f;
		}
		if (l < -20.0f) {
			l = -20.0f;
		}
		if (g > 0.2f) {
			m += MathHelper.cos(f * 0.4f) * 0.15f * g;
		}
		float n = abstractHorseEntity.getEatingGrassAnimationProgress(h);
		float o = abstractHorseEntity.getAngryAnimationProgress(h);
		float p = 1.0f - o;
		float q = abstractHorseEntity.getEatingAnimationProgress(h);
		boolean bl = abstractHorseEntity.tailWagTicks != 0;
		float r = (float) abstractHorseEntity.age + h;
		this.body.pitch = 0.0f;
		this.head.pitch = 0.5235988f + m;
		this.head.yaw = l * ((float)Math.PI / 180);
		float s = abstractHorseEntity.isTouchingWater() ? 0.2f : 1.0f;
		float t = MathHelper.cos(s * f * 0.6662f + (float)Math.PI);
		float u = t * 0.8f * g;
		float v = (1.0f - Math.max(o, n)) * (0.5235988f + m + q * MathHelper.sin(r) * 0.05f);
		this.head.pitch = o * (0.2617994f + m) + n * (2.1816616f + MathHelper.sin(r) * 0.05f) + v;
		this.head.yaw = o * l * ((float)Math.PI / 180) + (1.0f - Math.max(o, n)) * this.head.yaw;
		this.head.pivotY = o * -4.0f + n * 11.0f + (1.0f - Math.max(o, n)) * this.head.pivotY;
		this.head.pivotZ = o * -4.0f + n * -12.0f + (1.0f - Math.max(o, n)) * this.head.pivotZ;
		this.body.pitch = o * -0.7853982f + p * this.body.pitch;
		float w = 0.2617994f * o;
		float x = MathHelper.cos(r * 0.6f + (float)Math.PI);


		float y = (-1.0471976f + x) * o + u * p;
		float z = (-1.0471976f - x) * o - u * p;
		this.leftHindLeg.pitch = w - t * 0.5f * g * p;
		this.rightHindLeg.pitch = w + t * 0.5f * g * p;
		this.leftFrontLeg.pitch = y;
		this.rightFrontLeg.pitch = z;
		this.tail.pitch = 0.5235988f + g * 0.75f;

		this.tail.yaw = bl ? MathHelper.cos(r * 0.7f) : 0.0f;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
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