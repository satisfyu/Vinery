package daniking.vinery.client.render.entity;

import daniking.vinery.client.model.SimpleGeoModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SimpleGeoRenderer<T extends LivingEntity & IAnimatable> extends GeoEntityRenderer<T> {
	public SimpleGeoRenderer(EntityRendererFactory.Context mgr, String modId, String modelName) {
		super(mgr, new SimpleGeoModel<>(modId, modelName));
	}
}