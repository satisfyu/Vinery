package satisfyu.vinery.util.boat.api.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public final class CustomBoatClientHelper {
	private CustomBoatClientHelper() {
		return;
	}

	private static ResourceLocation getLayerId(ResourceLocation boatId, boolean raft, boolean chest) {
		String prefix = raft ? (chest ? "chest_raft/" : "raft/") : (chest ? "chest_boat/" : "boat/");
		return new ResourceLocation(boatId.getNamespace(), prefix + boatId.getPath());
	}

	public static ModelLayerLocation getLayer(ResourceLocation boatId, boolean raft, boolean chest) {
		return new ModelLayerLocation(getLayerId(boatId, raft, chest), "main");
	}

	public static Supplier<LayerDefinition> getTexturedModelDataProvider(boolean raft, boolean chest) {
		if (raft) {
			return chest ? ChestRaftModel::createBodyModel : RaftModel::createBodyModel;
		} else {
			return chest ? ChestBoatModel::createBodyModel : BoatModel::createBodyModel;
		}
	}

	private static void registerModelLayer(ResourceLocation boatId, boolean raft, boolean chest) {
		EntityModelLayerRegistry.register(getLayer(boatId, raft, chest), getTexturedModelDataProvider(raft, chest));
	}

	public static void registerModelLayers(ResourceLocation boatId, boolean raft) {
		registerModelLayer(boatId, raft, false);
		registerModelLayer(boatId, raft, true);
	}
}
