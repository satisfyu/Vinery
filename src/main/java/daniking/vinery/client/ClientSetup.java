package daniking.vinery.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.FermentationBarrelGui;
import daniking.vinery.client.gui.StoveGui;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VineryScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.world.BiomeColorCache;
import net.minecraft.world.biome.BiomeEffects;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ObjectRegistry.CHERRY_SIGN.getTexture()));
        //ObjectRegistry.EMPTY_RED_VINE, ObjectRegistry.RED_VINE, ObjectRegistry.RED_VINE_VARIANT_B, ObjectRegistry.RED_VINE_VARIANT_C
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.RED_GRAPE_BUSH, ObjectRegistry.WHITE_GRAPE_BUSH, ObjectRegistry.CHERRY_DOOR, ObjectRegistry.STACKABLE_LOG, ObjectRegistry.COOKING_POT, ObjectRegistry.CHERRY_JAM, ObjectRegistry.CHERRY_JAR, ObjectRegistry.FERMENTATION_BARREL);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.ROCKS, ObjectRegistry.RED_GRASS_FLOWER, ObjectRegistry.RED_GRASS_FLOWER_VARIANT_B, ObjectRegistry.PINK_GRASS_FLOWER, ObjectRegistry.PINK_GRASS_FLOWER_VARIANT_B, ObjectRegistry.WHITE_GRASS_FLOWER, ObjectRegistry.GRAPEVINE_STEM, ObjectRegistry.WINE_BOTTLE, ObjectRegistry.RED_GRAPEJUICE_WINE_BOTTLE, ObjectRegistry.WHITE_GRAPEJUICE_WINE_BOTTLE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> BiomeColors.getGrassColor(world, pos), ObjectRegistry.PINK_GRASS_FLOWER, ObjectRegistry.PINK_GRASS_FLOWER_VARIANT_B, ObjectRegistry.RED_GRASS_FLOWER, ObjectRegistry.RED_GRASS_FLOWER_VARIANT_B, ObjectRegistry.WHITE_GRASS_FLOWER);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(1.0, 0.5), ObjectRegistry.PINK_GRASS_FLOWER, ObjectRegistry.PINK_GRASS_FLOWER_VARIANT_B, ObjectRegistry.RED_GRASS_FLOWER, ObjectRegistry.RED_GRASS_FLOWER_VARIANT_B, ObjectRegistry.WHITE_GRASS_FLOWER);
        HandledScreens.register(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, StoveGui::new);
        HandledScreens.register(VineryScreenHandlerTypes.FERMENTATION_BARREL_GUI_HANDLER, FermentationBarrelGui::new);
        TerraformBoatClientHelper.registerModelLayer(new VineryIdentifier("cherry"));
    }
}
