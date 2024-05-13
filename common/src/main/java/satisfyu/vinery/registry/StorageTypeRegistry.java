package satisfyu.vinery.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.util.VineryIdentifier;

import java.util.List;
import java.util.Set;

import static satisfyu.vinery.registry.ObjectRegistry.*;

public class StorageTypeRegistry {
    public static final ResourceLocation BIG_BOTTLE = new VineryIdentifier("big_bottle");
    public static final ResourceLocation FOUR_BOTTLE = new VineryIdentifier("four_bottle");
    public static final ResourceLocation NINE_BOTTLE = new VineryIdentifier("nine_bottle");
    public static final ResourceLocation SHELF = new VineryIdentifier("shelf");
    public static final ResourceLocation WINE_BOX = new VineryIdentifier("wine_box");

    public static final ResourceLocation WINE_BOTTLE = new VineryIdentifier("wine_bottle");

    public static void registerBlocks(Set<Block> blocks) {
        blocks.add(DARK_CHERRY_WINE_RACK_SMALL.get());
        blocks.add(DARK_CHERRY_WINE_RACK_BIG.get());
        blocks.add(DARK_CHERRY_WINE_RACK_MID.get());
        blocks.add(DARK_CHERRY_SHELF.get());
        blocks.add(ObjectRegistry.WINE_BOX.get());
        blocks.add(FLOWER_BOX.get());
        blocks.add(FLOWER_POT_BIG.get());
        blocks.add(OAK_WINE_RACK_BIG.get());
        blocks.add(OAK_WINE_RACK_SMALL.get());
        blocks.add(OAK_WINE_RACK_MID.get());
        blocks.add(BIRCH_WINE_RACK_BIG.get());
        blocks.add(BIRCH_WINE_RACK_MID.get());
        blocks.add(BIRCH_WINE_RACK_SMALL.get());
        blocks.add(SPRUCE_WINE_RACK_BIG.get());
        blocks.add(SPRUCE_WINE_RACK_MID.get());
        blocks.add(SPRUCE_WINE_RACK_SMALL.get());
        blocks.add(DARK_OAK_WINE_RACK_BIG.get());
        blocks.add(DARK_OAK_WINE_RACK_MID.get());
        blocks.add(DARK_OAK_WINE_RACK_SMALL.get());
        blocks.add(JUNGLE_WINE_RACK_SMALL.get());
        blocks.add(JUNGLE_WINE_RACK_MID.get());
        blocks.add(JUNGLE_WINE_RACK_BIG.get());
        blocks.add(MANGROVE_WINE_RACK_BIG.get());
        blocks.add(MANGROVE_WINE_RACK_MID.get());
        blocks.add(MANGROVE_WINE_RACK_SMALL.get());
        blocks.add(ACACIA_WINE_RACK_BIG.get());
        blocks.add(ACACIA_WINE_RACK_MID.get());
        blocks.add(ACACIA_WINE_RACK_SMALL.get());
        blocks.add(BAMBOO_WINE_RACK_BIG.get());
        blocks.add(BAMBOO_WINE_RACK_MID.get());
        blocks.add(BAMBOO_WINE_RACK_SMALL.get());
        blocks.add(CHERRY_WINE_RACK_BIG.get());
        blocks.add(CHERRY_WINE_RACK_MID.get());
        blocks.add(CHERRY_WINE_RACK_SMALL.get());
        blocks.addAll(List.of(
                NOIR_WINE.get(), CLARK_WINE.get(), BOLVAR_WINE.get(), STAL_WINE.get(), CHERRY_WINE.get(), KELP_CIDER.get(),
                SOLARIS_WINE.get(), APPLE_WINE.get(), APPLE_CIDER.get(), STRAD_WINE.get(), CHENET_WINE.get(), MELLOHI_WINE.get(),
                LILITU_WINE.get(), MAGNETIC_WINE.get(), CHORUS_WINE.get(), JELLIE_WINE.get(), AEGIS_WINE.get(), RED_WINE.get(),
                KNULP_WINE.get(), CRISTEL_WINE.get(), JO_SPECIAL_MIXTURE.get(), GLOWING_WINE.get(), CREEPERS_CRUSH.get(),
                BOTTLE_MOJANG_NOIR.get(), VILLAGERS_FRIGHT.get(), MEAD.get(), EISWEIN.get(), LAMROC_WINE.get(), COUNT_ROLEESTER_SHIRAZ_WINE.get()
        ));

    }
}
