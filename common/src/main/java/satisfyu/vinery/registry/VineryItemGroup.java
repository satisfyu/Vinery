package satisfyu.vinery.registry;

import dev.architectury.registry.CreativeTabRegistry;
import satisfyu.vinery.Vinery;

import static satisfyu.vinery.registry.ObjectRegistry.*;

public class VineryItemGroup {



    public static void addItems(){

        CreativeTabRegistry.append(Vinery.VINERY_TAB,
                CHERRY, ROTTEN_CHERRY, RED_GRAPE_SEEDS, RED_GRAPE, WHITE_GRAPE_SEEDS,
                WHITE_GRAPE, SAVANNA_RED_GRAPE_SEEDS, SAVANNA_RED_GRAPE, SAVANNA_WHITE_GRAPE_SEEDS,
                SAVANNA_WHITE_GRAPE, TAIGA_RED_GRAPE_SEEDS, TAIGA_RED_GRAPE, TAIGA_WHITE_GRAPE_SEEDS,
                TAIGA_WHITE_GRAPE, JUNGLE_RED_GRAPE_SEEDS, JUNGLE_RED_GRAPE, JUNGLE_WHITE_GRAPE_SEEDS,
                JUNGLE_WHITE_GRAPE
        );
         

        CreativeTabRegistry.append(Vinery.VINERY_TAB,
                CHERRY_SAPLING.get(), GRAPEVINE_LEAVES.get(), CHERRY_LEAVES.get(), WHITE_GRAPE_CRATE.get(), RED_GRAPE_CRATE.get(), CHERRY_CRATE.get(), APPLE_CRATE.get(),
                GRAPEVINE_POT.get(), FERMENTATION_BARREL.get(), WINE_PRESS.get(), CHAIR.get(), TABLE.get(), WOOD_FIRED_OVEN.get(), STOVE.get(), KITCHEN_SINK.get(),
                WINE_RACK_1.get(), WINE_RACK_2.get(), WINE_RACK_3.get(), WINE_RACK_5.get(), BARREL.get(), STRIPPED_CHERRY_LOG.get(), CHERRY_LOG.get(), STRIPPED_CHERRY_WOOD.get(),
                CHERRY_WOOD.get(), STRIPPED_OLD_CHERRY_LOG.get(), OLD_CHERRY_LOG.get(), STRIPPED_OLD_CHERRY_WOOD.get(), OLD_CHERRY_WOOD.get(), CHERRY_BEAM.get(),
                CHERRY_PLANKS.get(), CHERRY_FLOORBOARD.get(), CHERRY_STAIRS.get(), CHERRY_SLAB.get(), CHERRY_FENCE.get(), CHERRY_FENCE_GATE.get(), CHERRY_BUTTON.get(),
                CHERRY_PRESSURE_PLATE.get(), CHERRY_DOOR.get(), CHERRY_TRAPDOOR.get(), CHERRY_SIGN_ITEM.get(), WINDOW.get(), GRAPEVINE_LATTICE.get(), LOAM.get(), LOAM_STAIRS.get(),
                CHORUS_WINE.get(), CHERRY_WINE.get(), MAGNETIC_WINE.get(), NOIR_WINE.get(), KING_DANIS_WINE.get(), MELLOHI_WINE.get(), STAL_WINE.get(), STRAD_WINE.get(),
                SOLARIS_WINE.get(), BOLVAR_WINE.get(), AEGIS_WINE.get(), CLARK_WINE.get(), CHENET_WINE.get(), KELP_CIDER.get(), APPLE_WINE.get(), APPLE_CIDER.get(), JELLIE_WINE.get(),
                CHERRY_JAR.get(), CHERRY_JAM.get(), APPLE_JAM.get(), SWEETBERRY_JAM.get(), GRAPE_JAM.get(), GRAPEVINE_STEM.get(), SHELF.get(), FLOWER_BOX.get(), FLOWER_POT.get(),
                BASKET.get(), COOKING_POT.get(), STACKABLE_LOG.get()
        );


        CreativeTabRegistry.append(Vinery.VINERY_TAB,
                FAUCET.get(), STRAW_HAT.get(), VINEMAKER_APRON.get(), VINEMAKER_LEGGINGS.get(), VINEMAKER_BOOTS.get(), GLOVES.get(), DOUGH.get(), CHOCOLATE_BREAD.get(),
                TOAST.get(), DONUT.get(), MILK_BREAD.get(), CRUSTY_BREAD.get(), BREAD_SLICE.get(), APPLE_CUPCAKE.get(), APPLE_PIE_SLICE.get(), APPLE_PIE.get().asItem(),
                APPLE_MASH.get(), TOMATO_CROP.get().asItem(), TOMATO_SEEDS.get(), APPLESAUCE.get(), MULE_SPAWN_EGG.get(), WANDERING_WINEMAKER_SPAWN_EGG.get(), TOMATO.get()
        );

    }
}
