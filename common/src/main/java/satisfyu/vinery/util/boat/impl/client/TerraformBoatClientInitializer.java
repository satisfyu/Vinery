package satisfyu.vinery.util.boat.impl.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import satisfyu.vinery.util.boat.impl.TerraformBoatInitializer;

@Environment(EnvType.CLIENT)
public final class TerraformBoatClientInitializer  {

	public static void init() {
		EntityRendererRegistry.register(TerraformBoatInitializer.BOAT, context -> new TerraformBoatEntityRenderer(context, false));
		EntityRendererRegistry.register(TerraformBoatInitializer.CHEST_BOAT, context -> new TerraformBoatEntityRenderer(context, true));
	}
}
