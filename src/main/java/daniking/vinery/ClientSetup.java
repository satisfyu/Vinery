package daniking.vinery;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.RED_GRAPE_BUSH, ObjectRegistry.WHITE_GRAPE_BUSH);
        BlockRenderLayerMap.INSTANCE.putBlock(ObjectRegistry.ROCKS, RenderLayer.getCutout());
    }
}
