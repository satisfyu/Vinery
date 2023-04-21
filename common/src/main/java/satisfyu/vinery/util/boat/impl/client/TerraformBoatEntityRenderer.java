package satisfyu.vinery.util.boat.impl.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import satisfyu.vinery.VineryExpectPlatform;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.api.client.TerraformBoatClientHelper;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatHolder;

@Environment(EnvType.CLIENT)
public class TerraformBoatEntityRenderer extends BoatRenderer {
	private final Map<TerraformBoatType, Pair<ResourceLocation, ListModel<Boat>>> texturesAndModels;

	public TerraformBoatEntityRenderer(EntityRendererProvider.Context context, boolean chest) {
		super(context, chest);

		this.texturesAndModels = VineryExpectPlatform.entrySet().stream().collect(ImmutableMap.toImmutableMap(entry -> {
			return entry.getValue();
		}, entry -> {
			boolean raft = entry.getValue().isRaft();
			String prefix = raft ? (chest ? "chest_raft/" : "raft/") : (chest ? "chest_boat/" : "boat/");

			ResourceLocation id = entry.getKey().location();
			ResourceLocation textureId = new ResourceLocation(id.getNamespace(), "textures/entity/" + prefix + id.getPath() + ".png");

			ModelLayerLocation layer = TerraformBoatClientHelper.getLayer(id, raft, chest);
			ListModel<Boat> model = createModel(context.bakeLayer(layer), raft, chest);

			return new Pair<>(textureId, model);
		}));
	}

	@Override
	public ResourceLocation getTextureLocation(Boat entity) {
		if (entity instanceof TerraformBoatHolder) {
			TerraformBoatType boat = ((TerraformBoatHolder) entity).getTerraformBoat();
			return this.texturesAndModels.get(boat).getFirst();
		}
		return super.getTextureLocation(entity);
	}

	public Pair<ResourceLocation, ListModel<Boat>> getTextureAndModel(TerraformBoatHolder holder) {
		return this.texturesAndModels.get(holder.getTerraformBoat());
	}

	private ListModel<Boat> createModel(ModelPart part, boolean raft, boolean chest) {
		if (raft) {
			return chest ? new ChestRaftModel(part) : new RaftModel(part);
		} else {
			return chest ? new ChestBoatModel(part) : new BoatModel(part);
		}
	}
}
