package satisfyu.vinery.fabriclike;


import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.fabriclike.world.VineryBiomeModification;
import satisfyu.vinery.registry.VineryBoatTypes;

public class VineryFabricLike {
    public static void init() {

        Vinery.init();
        VineryBiomeModification.init();
        VineryBoatTypes.init();
        Vinery.commonSetup();

        FabricLoader.getInstance().getModContainer(Vinery.MODID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("bushy_leaves"), container, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("apple_leaves"), container, ResourcePackActivationType.NORMAL);
        });



    }
}
