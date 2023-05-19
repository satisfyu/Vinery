package satisfyu.vinery.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryIdentifier;

import java.util.Set;

public class VineryStorageTypes {

    public static final ResourceLocation FOUR_BOTTLE = new VineryIdentifier("four_bottle");

    public static final ResourceLocation NINE_BOTTLE = new VineryIdentifier("nine_bottle");

    public static final ResourceLocation SHELF = new VineryIdentifier("shelf");

    public static final ResourceLocation WINE_BOX = new VineryIdentifier("wine_box");

    public static final ResourceLocation FLOWER_BOX = new VineryIdentifier("flower_box");



    public static void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.WINE_RACK_2.get());
        blocks.add(ObjectRegistry.WINE_RACK_1.get());
        blocks.add(ObjectRegistry.SHELF.get());
        blocks.add(ObjectRegistry.WINE_BOX.get());
        blocks.add(ObjectRegistry.FLOWER_BOX.get());
    }

}
