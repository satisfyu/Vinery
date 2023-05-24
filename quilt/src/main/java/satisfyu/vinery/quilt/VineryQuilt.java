package satisfyu.vinery.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import satisfyu.vinery.fabriclike.VineryFabricLike;

public class VineryQuilt implements ModInitializer {
	@Override
	public void onInitialize(ModContainer mod) {
		VineryFabricLike.init();
	}
}
