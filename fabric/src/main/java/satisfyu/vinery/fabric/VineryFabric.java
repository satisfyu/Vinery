package satisfyu.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.fabric.registry.VineryFabricVillagers;
import satisfyu.vinery.fabric.world.VineryBiomeModification;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Vinery.init();
        VineryFabricVillagers.init();
        VineryBiomeModification.init();
        Vinery.commonSetup();

        FabricLoader.getInstance().getModContainer(Vinery.MODID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("bushy_leaves"), container, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("apple_leaves"), container, ResourcePackActivationType.NORMAL);
        });
    }
}
