package satisfyu.vinery.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.registry.ObjectRegistry;

public class ModVillagers {

    public static PointOfInterestType VINERY_STAND = registerPOI("vinery_stand_poi", ObjectRegistry.CHAIR);

    public static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(Vinery.MODID, name),
                1, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static void registerVillagers() {
        Vinery.LOGGER.debug("Registering  Villagers for " + Vinery.MODID);
    }
}
