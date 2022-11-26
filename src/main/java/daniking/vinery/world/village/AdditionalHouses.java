package daniking.vinery.world.village;

import daniking.vinery.VineryIdentifier;
import fzzyhmstrs.structurized_reborn.impl.FabricStructurePoolRegistry;
import net.minecraft.util.Identifier;

public class AdditionalHouses {

    public static void registerNewVillageStructures(){
        FabricStructurePoolRegistry.registerSimple(
                new Identifier("minecraft:village/plains/houses"),
                new VineryIdentifier("plains_vinemaker_house_1"),
                 150
        );
    }
}
