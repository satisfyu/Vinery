package satisfyu.vinery.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import satisfyu.vinery.fabriclike.VineryFabricLike;
import satisfyu.vinery.util.boat.impl.TerraformBoatInitializer;
import satisfyu.vinery.util.boat.impl.client.TerraformBoatEntityRenderer;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VineryFabricLike.init();
        EntityRendererRegistry.register(TerraformBoatInitializer.BOAT, context -> new TerraformBoatEntityRenderer(context, false));
    }
}
