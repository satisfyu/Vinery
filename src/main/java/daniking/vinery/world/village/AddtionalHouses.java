package daniking.vinery.world.village;

import daniking.vinery.Vinery;
import fzzyhmstrs.structurized_reborn.api.FabricStructurePool;
import fzzyhmstrs.structurized_reborn.impl.FabricStructurePoolRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.util.Identifier;

public class AddtionalHouses {

    public static void registerNewVillageStructures(){
        FabricStructurePoolRegistry.registerSimple(
                new Identifier("minecraft:village/plains/houses"),
                new Identifier(Vinery.MODID, "plains_vinemaker_house_1"), 
                 150
        );
    }
}
