package daniking.vinery.client;

import daniking.vinery.client.gui.StoveGui;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VineryScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //ObjectRegistry.EMPTY_RED_VINE, ObjectRegistry.RED_VINE, ObjectRegistry.RED_VINE_VARIANT_B, ObjectRegistry.RED_VINE_VARIANT_C
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.RED_GRAPE_BUSH, ObjectRegistry.WHITE_GRAPE_BUSH, ObjectRegistry.CHERRY_DOOR);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.ROCKS, ObjectRegistry.RED_GRASS_FLOWER, ObjectRegistry.RED_GRASS_FLOWER_VARIANT_B, ObjectRegistry.PINK_GRASS_FLOWER, ObjectRegistry.PINK_GRASS_FLOWER_VARIANT_B, ObjectRegistry.WHITE_GRASS_FLOWER, ObjectRegistry.GRAPEVINE_STEM);
        HandledScreens.register(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, StoveGui::new);
    }
}
