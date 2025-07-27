package net.satisfy.vinery.fabric.world;

import fzzyhmstrs.structurized_reborn.impl.FabricStructurePoolRegistry;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.util.VineryIdentifier;


public class VillageAddition {
    // Using https://github.com/fzzyhmstrs/structurized-reborn (Under MIT License)
    public static void registerNewVillageStructures() {
        FabricStructurePoolRegistry.registerSimple(
                ResourceLocation.parse("minecraft:village/plains/houses"),
               VineryIdentifier.of("vinery_plains"),
                150
        );
        FabricStructurePoolRegistry.registerSimple(
                ResourceLocation.parse("minecraft:village/taiga/houses"),
                VineryIdentifier.of("vinery_taiga"),
                150
        );
        FabricStructurePoolRegistry.registerSimple(
                ResourceLocation.parse("minecraft:village/savanna/houses"),
                VineryIdentifier.of("vinery_savanna"),
                150
        );
        FabricStructurePoolRegistry.registerSimple(
                ResourceLocation.parse("minecraft:village/desert/houses"),
                VineryIdentifier.of("vinery_desert"),
                150
        );
    }
}