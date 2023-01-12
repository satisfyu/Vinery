package daniking.vinery.client.render.feature;


import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import daniking.vinery.item.CustomModelArmorItem;
import daniking.vinery.registry.CustomArmorRegistry;
import daniking.vinery.registry.ObjectRegistry;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CustomArmorFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

	public Map<Item, EntityModel<T>> MODELS = Maps.newHashMap();

	private final EntityModelSet modelLoader;
	private final float yOffset;

	public CustomArmorFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet modelSet) {
		this(context, modelSet, 0);
	}

	public CustomArmorFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet modelLoader, float yOffset) {
		super(context);
		this.yOffset = yOffset;
		this.modelLoader = modelLoader;
	}

	public EntityModel<T> getHatModel(T entity, EquipmentSlot slot) {
		Item hatItem = getHatItem(entity, slot);
		if(hatItem != null) {
			if(MODELS.isEmpty()) {
				CustomArmorRegistry.registerArmor(MODELS, modelLoader);
			}
			return MODELS.get(hatItem);
		}
		return null;
	}

	public CustomModelArmorItem getHatItem(T entity, EquipmentSlot slot)
	{
		ItemStack headSlot = entity.getItemBySlot(slot);
		if(headSlot.getItem() instanceof CustomModelArmorItem hat && !headSlot.isEmpty())
			return hat;
		return null;
	}



	@Override
	public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
		for(EquipmentSlot slot : slots){
			EntityModel<T> headModel = getHatModel(entity, slot);
			ItemStack headSlot = entity.getItemBySlot(slot);
			if(headModel != null && headSlot.getItem() instanceof CustomModelArmorItem armorItem){

				matrices.pushPose();
				setupHat(matrices, slot, armorItem.getOffset());

				VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(this.getTexture(entity, slot)), false, headSlot.hasFoil());
				headModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
				matrices.popPose();
			}
		}
	}

	public void setupHat(PoseStack matrices, EquipmentSlot slot, float extraYOffset) {
		if(slot.equals(EquipmentSlot.HEAD)){
			((HeadedModel) this.getParentModel()).getHead().translateAndRotate(matrices);
		}
		matrices.scale(1F,1F,1F);
		matrices.translate(0, yOffset + extraYOffset, 0);
	}


	protected ResourceLocation getTexture(T entity, EquipmentSlot slot) {
		CustomModelArmorItem customItem = getHatItem(entity, slot);
		if(customItem != null) return customItem.getTexture();
		return super.getTextureLocation(entity);
	}
}