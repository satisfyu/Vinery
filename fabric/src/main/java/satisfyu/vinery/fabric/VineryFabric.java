package satisfyu.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import satisfyu.vinery.fabriclike.VineryFabricLike;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VineryFabricLike.init();
    }
}
