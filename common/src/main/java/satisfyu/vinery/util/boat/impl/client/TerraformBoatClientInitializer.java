package satisfyu.vinery.util.boat.impl.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.util.boat.impl.TerraformBoatInitializer;

import java.util.Map;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public final class TerraformBoatClientInitializer {
	public static void init(Map<Supplier<EntityType<?>>, EntityRendererProvider<?>> map) {
		VineryClient.registerEntityRenderer(map, TerraformBoatInitializer.BOAT, TerraformBoatEntityRenderer::new);
	}
}
