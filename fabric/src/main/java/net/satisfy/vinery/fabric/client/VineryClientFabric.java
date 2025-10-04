package net.satisfy.vinery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.client.VineryClient;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.entity.DarkCherryBoatEntity;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.fabric.client.renderer.StrawHatRenderer;
import net.satisfy.vinery.fabric.client.renderer.WinemakerBootsRenderer;
import net.satisfy.vinery.fabric.client.renderer.WinemakerChestplateRenderer;
import net.satisfy.vinery.fabric.client.renderer.WinemakerLeggingsRenderer;

public class VineryClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VineryClient.preInitClient();
        VineryClient.onInitializeClient();
        registerBoatModels();

        ArmorRenderer.register(new StrawHatRenderer(), ObjectRegistry.STRAW_HAT.get());
        ArmorRenderer.register(new WinemakerChestplateRenderer(), ObjectRegistry.WINEMAKER_APRON.get());
        ArmorRenderer.register(new WinemakerLeggingsRenderer(), ObjectRegistry.WINEMAKER_LEGGINGS.get());
        ArmorRenderer.register(new WinemakerBootsRenderer(), ObjectRegistry.WINEMAKER_BOOTS.get());
    }

    private void registerBoatModels() {
        for (DarkCherryBoatEntity.Type type : DarkCherryBoatEntity.Type.values()) {
            String modId = Vinery.MOD_ID;
            EntityModelLayerRegistry.registerModelLayer(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(modId, type.getModelLocation()), "main"), BoatModel::createBodyModel);
            EntityModelLayerRegistry.registerModelLayer(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(modId, type.getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
        }
    }
}
