package satisfyu.vinery.fabriclike;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.fabriclike.registry.VineryFabricVillagers;
import satisfyu.vinery.fabriclike.world.VineryBiomeModification;

public class VineryFabricLike {
	public static void init() {
		Vinery.init();
		VineryFabricVillagers.init();
		VineryBiomeModification.init();
		FabricLoader.getInstance().getModContainer(Vinery.MODID).ifPresent(container -> {
			ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("bushy_leaves"), container,
					ResourcePackActivationType.NORMAL);
			ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("apple_leaves"), container,
					ResourcePackActivationType.NORMAL);
		});
	}
}
