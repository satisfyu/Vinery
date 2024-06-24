package net.satisfy.vinery.client;

import de.cristelknight.doapi.client.render.block.storage.api.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.api.StorageTypeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.client.render.block.storage.*;
import net.satisfy.vinery.registry.StorageTypeRegistry;

public class ClientStorageTypes {

    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer){
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void init(){
        registerStorageType(StorageTypeRegistry.BIG_BOTTLE, new BigBottleRenderer());
        registerStorageType(StorageTypeRegistry.FOUR_BOTTLE, new FourBottleRenderer());
        registerStorageType(StorageTypeRegistry.NINE_BOTTLE, new NineBottleRenderer());
        registerStorageType(StorageTypeRegistry.SHELF, new ShelfRenderer());
        registerStorageType(StorageTypeRegistry.WINE_BOX, new WineBoxRenderer());
        registerStorageType(StorageTypeRegistry.WINE_BOTTLE, new WineBottleRenderer());
    }

}
