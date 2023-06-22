package satisfyu.vinery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import satisfyu.vinery.client.VineryClient;

public class VineryClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VineryClient.preInitClient();
        VineryClient.onInitializeClient();
    }
}
