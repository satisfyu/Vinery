package daniking.vinery.registry;

import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.StorageBlock;
import daniking.vinery.client.render.block.*;
import daniking.vinery.util.StorageTypeApi;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.Set;

public class StorageTypes implements StorageTypeApi {

    public static final Identifier FOUR_BOTTLE = registerStorageType("four_bottle", new FourBottleRenderer());

    public static final Identifier NINE_BOTTLE = registerStorageType("nine_bottle", new NineBottleRenderer());

    public static final Identifier SHELF = registerStorageType("shelf", new ShelfRenderer());

    public static final Identifier WINE_BOX = registerStorageType("wine_box", new WineBoxRenderer());

    public static void init(){
        Vinery.LOGGER.debug("Registering Storage Block Renderers!");
    }

    public static Identifier registerStorageType(String string, StorageTypeRenderer renderer){
         return StorageBlock.registerStorageType(new VineryIdentifier(string), renderer);
    }

    @Override
    public void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.WINE_RACK_2);
        blocks.add(ObjectRegistry.WINE_RACK_1);
        blocks.add(ObjectRegistry.SHELF);
        blocks.add(ObjectRegistry.WINE_BOX);
    }
}
