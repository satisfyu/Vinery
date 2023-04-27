package satisfyu.vinery.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.storage.api.StorageBlock;
import satisfyu.vinery.client.render.block.storage.*;
import satisfyu.vinery.client.render.block.storage.api.StorageTypeRenderer;

import java.util.Set;

public class VineryStorageTypes {

    public static final ResourceLocation FOUR_BOTTLE = registerStorageType("four_bottle", new FourBottleRenderer());

    public static final ResourceLocation NINE_BOTTLE = registerStorageType("nine_bottle", new NineBottleRenderer());

    public static final ResourceLocation SHELF = registerStorageType("shelf", new ShelfRenderer());

    public static final ResourceLocation WINE_BOX = registerStorageType("wine_box", new WineBoxRenderer());

    public static final ResourceLocation FLOWER_BOX = registerStorageType("flower_box", new FlowerBoxRenderer());


    public static ResourceLocation registerStorageType(String string, StorageTypeRenderer renderer){
        return StorageBlock.registerStorageType(new VineryIdentifier(string), renderer);
    }

    public static void init(){

    }

    public static void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.WINE_RACK_2.get());
        blocks.add(ObjectRegistry.WINE_RACK_1.get());
        blocks.add(ObjectRegistry.SHELF.get());
        blocks.add(ObjectRegistry.WINE_BOX.get());

        blocks.add(ObjectRegistry.FLOWER_BOX.get());
    }

}
