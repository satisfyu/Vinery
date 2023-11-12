package satisfyu.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.fabric.registry.VineryFabricVillagers;
import satisfyu.vinery.fabric.world.VineryBiomeModification;
import satisfyu.vinery.registry.VineryCompostables;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Vinery.init();
        VineryCompostables.registerCompostable();
        VineryFabricVillagers.init();
        VineryBiomeModification.init();
        Vinery.commonSetup();
    }
}
