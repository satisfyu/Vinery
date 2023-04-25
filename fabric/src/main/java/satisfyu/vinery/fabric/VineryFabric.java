package satisfyu.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.mixin.itemgroup.ItemGroupAccessor;
import satisfyu.vinery.fabriclike.VineryFabricLike;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VineryFabricLike.init();
    }
}
