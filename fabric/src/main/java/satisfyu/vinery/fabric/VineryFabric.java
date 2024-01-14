package satisfyu.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.fabric.registry.VineryFabricVillagers;
import satisfyu.vinery.fabric.world.VineryBiomeModification;
import satisfyu.vinery.registry.CompostableRegistry;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Vinery.init();
        CompostableRegistry.registerCompostable();
        VineryFabricVillagers.init();
        VineryBiomeModification.init();
        Vinery.commonSetup();
    }
}
