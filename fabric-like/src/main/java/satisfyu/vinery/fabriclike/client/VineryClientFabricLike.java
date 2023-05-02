package satisfyu.vinery.fabriclike.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import satisfyu.vinery.client.VineryClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class VineryClientFabricLike implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VineryClient.onInitializeClient();
        registerEntityRenderers();
    }

    public static void registerEntityRenderers(){
        //renderers
        Map<Supplier<EntityType<?>>, EntityRendererProvider<?>> entityRenderers = new ConcurrentHashMap<>();
        VineryClient.getEntityEntityRenderers(entityRenderers);
        for (Map.Entry<Supplier<EntityType<?>>, EntityRendererProvider<?>> entry : entityRenderers.entrySet()) {
            EntityRendererRegistry.register(entry.getKey().get(), (EntityRendererProvider<Entity>) entry.getValue());
        }

        //layers
        Map<ModelLayerLocation, Supplier<LayerDefinition>> modelLayers = new ConcurrentHashMap<>();
        VineryClient.getEntityModelLayers(modelLayers);
        modelLayers.forEach(EntityModelLayerRegistry::register);
    }
}
