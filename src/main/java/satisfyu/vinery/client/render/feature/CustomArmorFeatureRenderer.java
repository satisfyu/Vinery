package satisfyu.vinery.client.render.feature;


import com.google.common.collect.Maps;
import net.fabricmc.loader.api.FabricLoader;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.item.CustomModelArmorItem;
import satisfyu.vinery.registry.CustomArmorRegistry;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import satisfyu.vinery.util.VineryApi;

import java.util.Map;

public class CustomArmorFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

	public Map<Item, EntityModel<T>> MODELS = Maps.newHashMap();

	private final EntityModelLoader modelLoader;
	private final float yOffset;

	public CustomArmorFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader modelSet) {
		this(context, modelSet, 0);
	}

	public CustomArmorFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader modelLoader, float yOffset) {
		super(context);
		this.yOffset = yOffset;
		this.modelLoader = modelLoader;
	}

	public EntityModel<T> getHatModel(T entity, EquipmentSlot slot) {
		Item hatItem = getHatItem(entity, slot);
		if(hatItem != null) {
			if(MODELS.isEmpty()) {

				FabricLoader.getInstance().getEntrypointContainers("vinery", VineryApi.class).forEach(entrypoint -> {
					String modId = entrypoint.getProvider().getMetadata().getId();
					try {
						VineryApi api = entrypoint.getEntrypoint();
						api.registerArmor(MODELS, modelLoader);
					} catch (Throwable e) {
						Vinery.LOGGER.error("Mod {} provides a broken implementation of VineryApi, therefore couldn't register custom models", modId, e);
					}
				});

			}
			return MODELS.get(hatItem);
		}
		return null;
	}

	public CustomModelArmorItem getHatItem(T entity, EquipmentSlot slot)
	{
		ItemStack headSlot = entity.getEquippedStack(slot);
		if(headSlot.getItem() instanceof CustomModelArmorItem hat && !headSlot.isEmpty())
			return hat;
		return null;
	}



	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
		for(EquipmentSlot slot : slots){
			EntityModel<T> headModel = getHatModel(entity, slot);
			ItemStack headSlot = entity.getEquippedStack(slot);
			if(headModel != null && headSlot.getItem() instanceof CustomModelArmorItem armorItem){

				matrices.push();
				setupHat(matrices, slot, armorItem.getOffset());

				VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(this.getTexture(entity, slot)), false, headSlot.hasGlint());
				headModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
				matrices.pop();
			}
		}
	}

	public void setupHat(MatrixStack matrices, EquipmentSlot slot, float extraYOffset) {
		if(slot.equals(EquipmentSlot.HEAD)){
			((ModelWithHead) this.getContextModel()).getHead().rotate(matrices);
		}
		matrices.scale(1F,1F,1F);
		matrices.translate(0, yOffset + extraYOffset, 0);
	}


	protected Identifier getTexture(T entity, EquipmentSlot slot) {
		CustomModelArmorItem customItem = getHatItem(entity, slot);
		if(customItem != null) return customItem.getTexture();
		return super.getTexture(entity);
	}
}