package satisfyu.vinery.fabriclike.client;

import net.fabricmc.api.ClientModInitializer;
import satisfyu.vinery.client.VineryClient;

public class VineryClientFabricLike implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VineryClient.onInitializeClient();
    }
}
