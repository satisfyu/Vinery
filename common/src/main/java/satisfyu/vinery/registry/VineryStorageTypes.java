package satisfyu.vinery.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryIdentifier;

import java.util.List;
import java.util.Set;

public class VineryStorageTypes {

    public static final ResourceLocation FOUR_BOTTLE = new VineryIdentifier("four_bottle");
    public static final ResourceLocation NINE_BOTTLE = new VineryIdentifier("nine_bottle");
    public static final ResourceLocation SHELF = new VineryIdentifier("shelf");
    public static final ResourceLocation WINE_BOX = new VineryIdentifier("wine_box");
    public static final ResourceLocation FLOWER_BOX = new VineryIdentifier("flower_box");
    public static final ResourceLocation WINE_BOTTLE = new VineryIdentifier("wine_bottle");



    public static void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.CHERRY_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.CHERRY_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.SHELF.get());
        blocks.add(ObjectRegistry.WINE_BOX.get());
        blocks.add(ObjectRegistry.FLOWER_BOX.get());
        blocks.add(ObjectRegistry.OAK_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.OAK_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.BIRCH_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.BIRCH_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.SPRUCE_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.SPRUCE_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.DARK_OAK_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.DARK_OAK_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.JUNGLE_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.JUNGLE_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.MANGROVE_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.MANGROVE_WINE_RACK_SMALL.get());
        blocks.add(ObjectRegistry.ACACIA_WINE_RACK_BIG.get());
        blocks.add(ObjectRegistry.ACACIA_WINE_RACK_SMALL.get());
        blocks.addAll(List.of(
                ObjectRegistry.NOIR_WINE.get(), ObjectRegistry.CLARK_WINE.get(), ObjectRegistry.BOLVAR_WINE.get(), ObjectRegistry.STAL_WINE.get(), ObjectRegistry.CHERRY_WINE.get(), ObjectRegistry.KELP_CIDER.get(),
                ObjectRegistry.SOLARIS_WINE.get(), ObjectRegistry.APPLE_WINE.get(), ObjectRegistry.APPLE_CIDER.get(), ObjectRegistry.STRAD_WINE.get(), ObjectRegistry.CHENET_WINE.get(), ObjectRegistry.MELLOHI_WINE.get(),
                ObjectRegistry.KING_DANIS_WINE.get(), ObjectRegistry.MAGNETIC_WINE.get(), ObjectRegistry.CHORUS_WINE.get(), ObjectRegistry.JELLIE_WINE.get(), ObjectRegistry.AEGIS_WINE.get(), ObjectRegistry.RED_WINE.get(),
                ObjectRegistry.PRAETORIAN_WINE.get(), ObjectRegistry.CRISTEL_WINE.get(), ObjectRegistry.JO_SPECIAL_MIXTURE.get(), ObjectRegistry.GLOWING_WINE.get(), ObjectRegistry.CREEPERS_CRUSH.get(),
                ObjectRegistry.BOTTLE_MOJANG_NOIR.get(), ObjectRegistry.VILLAGERS_FRIGHT.get(), ObjectRegistry.MEAD.get(), ObjectRegistry.EISWEIN.get()
        ));

    }
}
