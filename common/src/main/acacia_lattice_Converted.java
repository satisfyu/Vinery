// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class acacia_lattice_Converted<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "acacia_lattice_converted"), "main");
	private final ModelPart lattice_wall;
	private final ModelPart grape_cluster;
	private final ModelPart growing_red;
	private final ModelPart sprout;
	private final ModelPart growing_white;
	private final ModelPart mesh;
	private final ModelPart supportright;
	private final ModelPart corner_braces_right;
	private final ModelPart supportleft;
	private final ModelPart corner_braces_left;
	private final ModelPart lattice_floor;
	private final ModelPart grape_cluster_floor;
	private final ModelPart growing_red_floor;
	private final ModelPart sprout_floor;
	private final ModelPart growing_white_floor;
	private final ModelPart lattice_parts;

	public acacia_lattice_Converted(ModelPart root) {
		this.lattice_wall = root.getChild("lattice_wall");
		this.grape_cluster = this.lattice_wall.getChild("grape_cluster");
		this.growing_red = this.grape_cluster.getChild("growing_red");
		this.sprout = this.grape_cluster.getChild("sprout");
		this.growing_white = this.grape_cluster.getChild("growing_white");
		this.mesh = this.lattice_wall.getChild("mesh");
		this.supportright = this.lattice_wall.getChild("supportright");
		this.corner_braces_right = this.lattice_wall.getChild("corner_braces_right");
		this.supportleft = this.lattice_wall.getChild("supportleft");
		this.corner_braces_left = this.lattice_wall.getChild("corner_braces_left");
		this.lattice_floor = root.getChild("lattice_floor");
		this.grape_cluster_floor = this.lattice_floor.getChild("grape_cluster_floor");
		this.growing_red_floor = this.grape_cluster_floor.getChild("growing_red_floor");
		this.sprout_floor = this.grape_cluster_floor.getChild("sprout_floor");
		this.growing_white_floor = this.grape_cluster_floor.getChild("growing_white_floor");
		this.lattice_parts = this.lattice_floor.getChild("lattice_parts");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition lattice_wall = partdefinition.addOrReplaceChild("lattice_wall", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition grape_cluster = lattice_wall.addOrReplaceChild("grape_cluster", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -10.0F));

		PartDefinition growing_red = grape_cluster.addOrReplaceChild("growing_red", CubeListBuilder.create().texOffs(46, 17).addBox(-15.0F, -16.0F, -0.5F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 17.0F));

		PartDefinition sprout = grape_cluster.addOrReplaceChild("sprout", CubeListBuilder.create().texOffs(46, 0).addBox(-15.0F, -16.0F, -0.5F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 17.0F));

		PartDefinition growing_white = grape_cluster.addOrReplaceChild("growing_white", CubeListBuilder.create().texOffs(46, 34).addBox(-15.0F, -16.0F, -0.5F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 17.0F));

		PartDefinition mesh = lattice_wall.addOrReplaceChild("mesh", CubeListBuilder.create().texOffs(48, 64).addBox(-30.0F, -12.0F, 2.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(22.0F, -4.0F, 5.0F));

		PartDefinition supportright = lattice_wall.addOrReplaceChild("supportright", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, 7.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, -7.0F, -1.0F));

		PartDefinition corner_braces_right = lattice_wall.addOrReplaceChild("corner_braces_right", CubeListBuilder.create().texOffs(8, 0).addBox(6.0F, -16.0F, -2.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition corner_braces_right_bottom_r1 = corner_braces_right.addOrReplaceChild("corner_braces_right_bottom_r1", CubeListBuilder.create().texOffs(8, 10).addBox(-7.99F, -4.0F, 6.0F, 1.98F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -7.0F, -1.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition corner_braces_right_top_r1 = corner_braces_right.addOrReplaceChild("corner_braces_right_top_r1", CubeListBuilder.create().texOffs(3, 10).addBox(-7.99F, -6.0F, -5.5F, 1.98F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -7.0F, -1.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition supportleft = lattice_wall.addOrReplaceChild("supportleft", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -16.0F, 14.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, -8.0F));

		PartDefinition corner_braces_left = lattice_wall.addOrReplaceChild("corner_braces_left", CubeListBuilder.create().texOffs(8, 0).addBox(-8.0F, -16.0F, -2.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition corner_braces_left_top_r1 = corner_braces_left.addOrReplaceChild("corner_braces_left_top_r1", CubeListBuilder.create().texOffs(8, 10).addBox(-7.99F, -4.0F, 6.0F, 1.98F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -1.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition corner_braces_left_bottom_r1 = corner_braces_left.addOrReplaceChild("corner_braces_left_bottom_r1", CubeListBuilder.create().texOffs(3, 10).addBox(-7.99F, -6.0F, -5.5F, 1.98F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -1.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition lattice_floor = partdefinition.addOrReplaceChild("lattice_floor", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition grape_cluster_floor = lattice_floor.addOrReplaceChild("grape_cluster_floor", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 3.0F));

		PartDefinition hanging_2_r1 = grape_cluster_floor.addOrReplaceChild("hanging_2_r1", CubeListBuilder.create().texOffs(32, 14).addBox(-7.0F, -11.5F, -1.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8284F, 18.0F, -4.4142F, 0.0F, 0.7854F, 0.0F));

		PartDefinition hanging_1_r1 = grape_cluster_floor.addOrReplaceChild("hanging_1_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-7.0F, -9.5F, -1.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4142F, 16.0F, -0.1716F, 0.0F, -0.7854F, 0.0F));

		PartDefinition growing_red_floor = grape_cluster_floor.addOrReplaceChild("growing_red_floor", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition growing_red_floor_r1 = growing_red_floor.addOrReplaceChild("growing_red_floor_r1", CubeListBuilder.create().texOffs(2, 52).addBox(-18.0F, -30.5F, -3.0F, 16.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 36.0F, 7.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition sprout_floor = grape_cluster_floor.addOrReplaceChild("sprout_floor", CubeListBuilder.create(), PartPose.offset(3.0F, 36.0F, 7.0F));

		PartDefinition sprouting_grapes_floor_r1 = sprout_floor.addOrReplaceChild("sprouting_grapes_floor_r1", CubeListBuilder.create().texOffs(2, 39).addBox(-18.0F, -30.5F, -3.0F, 16.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition growing_white_floor = grape_cluster_floor.addOrReplaceChild("growing_white_floor", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition growing_white_floor_r1 = growing_white_floor.addOrReplaceChild("growing_white_floor_r1", CubeListBuilder.create().texOffs(2, 65).addBox(-18.0F, -30.5F, -3.0F, 16.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 36.0F, 7.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition lattice_parts = lattice_floor.addOrReplaceChild("lattice_parts", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cross_brace_r1 = lattice_parts.addOrReplaceChild("cross_brace_r1", CubeListBuilder.create().texOffs(-12, 24).addBox(-18.0F, -29.0F, -3.0F, 16.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 28.0F, 10.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition support_floor_left_r1 = lattice_parts.addOrReplaceChild("support_floor_left_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -16.0F, 14.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-30.0F, -16.0F, 14.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(22.0F, -16.0F, -8.0F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 80, 80);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		lattice_wall.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		lattice_floor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}