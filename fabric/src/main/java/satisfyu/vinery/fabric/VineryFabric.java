package satisfyu.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.fabric.client.StrawHatRenderer;
import satisfyu.vinery.fabric.registry.VineryFabricVillagers;
import satisfyu.vinery.fabric.world.VineryBiomeModification;
import satisfyu.vinery.registry.CompostableRegistry;
import satisfyu.vinery.registry.ObjectRegistry;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Vinery.init();
        CompostableRegistry.registerCompostable();
        VineryFabricVillagers.init();
        VineryBiomeModification.init();
        Vinery.commonSetup();

        ArmorRenderer.register(new StrawHatRenderer(), ObjectRegistry.STRAW_HAT.get());

    }
}
