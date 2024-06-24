package net.satisfy.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.fabric.registry.VineryFabricVillagers;
import net.satisfy.vinery.fabric.world.VineryBiomeModification;
import net.satisfy.vinery.registry.CompostableRegistry;

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
