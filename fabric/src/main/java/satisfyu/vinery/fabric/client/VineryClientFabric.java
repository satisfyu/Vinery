package satisfyu.vinery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.registry.ObjectRegistry;

public class VineryClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VineryClient.preInitClient();
        VineryClient.onInitializeClient();

        ArmorRenderer.register(new StrawHatRenderer(), ObjectRegistry.STRAW_HAT.get());
    }
}
