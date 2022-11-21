package daniking.vinery.world.village;

import daniking.vinery.Vinery;
import fzzyhmstrs.structurized_reborn.impl.FabricStructurePoolRegistry;
import net.minecraft.util.Identifier;

public class AdditionalHouses {

    public static void registerNewVillageStructures(){
        FabricStructurePoolRegistry.registerSimple(
                new Identifier("minecraft:village/plains/houses"),
                new Identifier(Vinery.MODID, "plains_vinemaker_house_1"), 
                 150
        );
    }
}
