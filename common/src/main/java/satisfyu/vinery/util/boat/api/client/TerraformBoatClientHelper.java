package satisfyu.vinery.util.boat.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public final class TerraformBoatClientHelper {
	private TerraformBoatClientHelper() {
		return;
	}

	public static ModelLayerLocation getLayer(ResourceLocation id) {
		return new ModelLayerLocation(new ResourceLocation(id.getNamespace(), "boat/" + id.getPath()), "main");
	}

	private static void registerModelLayer(Map<ModelLayerLocation, Supplier<LayerDefinition>> map, ResourceLocation boatId, boolean chest) {
		map.put(getLayer(boatId), BoatModel::createBodyModel);
	}

	public static void registerModelLayers(Map<ModelLayerLocation, Supplier<LayerDefinition>> map, ResourceLocation boatId) {
		registerModelLayer(map, boatId, false);
		registerModelLayer(map, boatId, true);
	}
}
