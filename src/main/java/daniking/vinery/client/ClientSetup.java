package daniking.vinery.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.StoveGui;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VineryScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ObjectRegistry.CHERRY_SIGN.getTexture()));
        //ObjectRegistry.EMPTY_RED_VINE, ObjectRegistry.RED_VINE, ObjectRegistry.RED_VINE_VARIANT_B, ObjectRegistry.RED_VINE_VARIANT_C
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.RED_GRAPE_BUSH, ObjectRegistry.WHITE_GRAPE_BUSH, ObjectRegistry.CHERRY_DOOR, ObjectRegistry.STACKABLE_LOG);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.ROCKS, ObjectRegistry.RED_GRASS_FLOWER, ObjectRegistry.RED_GRASS_FLOWER_VARIANT_B, ObjectRegistry.PINK_GRASS_FLOWER, ObjectRegistry.PINK_GRASS_FLOWER_VARIANT_B, ObjectRegistry.WHITE_GRASS_FLOWER, ObjectRegistry.GRAPEVINE_STEM);
        HandledScreens.register(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, StoveGui::new);
        TerraformBoatClientHelper.registerModelLayer(new VineryIdentifier("cherry"));
    }
}
