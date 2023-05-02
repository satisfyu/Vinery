package satisfyu.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import satisfyu.vinery.fabriclike.VineryFabricLike;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VineryFabricLike.init();
    }
}
