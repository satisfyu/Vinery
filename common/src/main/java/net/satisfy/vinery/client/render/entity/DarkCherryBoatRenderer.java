package net.satisfy.vinery.client.render.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.entity.DarkCherryBoatEntity;
import net.satisfy.vinery.core.entity.DarkCherryChestBoatEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.Map;
import java.util.stream.Stream;

public class DarkCherryBoatRenderer<T extends DarkCherryBoatEntity> extends BoatRenderer {
    private final Map<DarkCherryBoatEntity.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public DarkCherryBoatRenderer(EntityRendererProvider.Context context, boolean hasChest) {
        super(context,hasChest);
        this.shadowRadius = 0.8f;
        this.boatResources = Stream.of(DarkCherryBoatEntity.Type.values()).collect(ImmutableMap.toImmutableMap((type) -> type,
                (type) -> Pair.of(ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, getTextureLocation(type, hasChest)), this.createBoatModel(context, type, hasChest))));
    }

    private static String getTextureLocation(DarkCherryBoatEntity.Type pType, boolean pChestBoat) {
        return pChestBoat ? "textures/entity/chest_boat/" + pType.getName() + ".png" : "textures/entity/boat/" + pType.getName() + ".png";
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context pContext, DarkCherryBoatEntity.Type pType, boolean pChestBoat) {
        ModelLayerLocation modellayerlocation = pChestBoat ? DarkCherryBoatRenderer.createChestBoatModelName(pType) : DarkCherryBoatRenderer.createBoatModelName(pType);
        ModelPart modelpart = pContext.bakeLayer(modellayerlocation);
        return pChestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
    }

    public static ModelLayerLocation createBoatModelName(DarkCherryBoatEntity.Type pType) {
        return createLocation("boat/" + pType.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(DarkCherryBoatEntity.Type pType) {
        return createLocation("chest_boat/" + pType.getName(), "main");
    }

    private static ModelLayerLocation createLocation(String pPath, String pModel) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, pPath), pModel);
    }

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        if(boat instanceof DarkCherryBoatEntity modBoat) {
            return this.boatResources.get(modBoat.getWoodType());
        } else if(boat instanceof DarkCherryChestBoatEntity modChestBoatEntity) {
            return this.boatResources.get(modChestBoatEntity.getModVariant());
        } else {
            return null;
        }
    }
}