package satisfyu.vinery.client;

import de.cristelknight.doapi.client.render.block.storage.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import net.minecraft.resources.ResourceLocation;
import satisfyu.vinery.client.render.block.storage.*;
import satisfyu.vinery.registry.StorageTypeRegistry;

public class ClientStorageTypes {

    public static ResourceLocation registerStorageType(ResourceLocation location, StorageTypeRenderer renderer){
        return StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void init(){
        registerStorageType(StorageTypeRegistry.BIG_BOTTLE, new BigBottleRenderer());
        registerStorageType(StorageTypeRegistry.FOUR_BOTTLE, new FourBottleRenderer());
        registerStorageType(StorageTypeRegistry.NINE_BOTTLE, new NineBottleRenderer());
        registerStorageType(StorageTypeRegistry.SHELF, new ShelfRenderer());
        registerStorageType(StorageTypeRegistry.WINE_BOX, new WineBoxRenderer());
        registerStorageType(StorageTypeRegistry.FLOWER_BOX, new FlowerBoxRenderer());
        registerStorageType(StorageTypeRegistry.WINE_BOTTLE, new WineBottleRenderer());
    }

}
