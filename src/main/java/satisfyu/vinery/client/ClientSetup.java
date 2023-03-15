package satisfyu.vinery.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.entity.chair.ChairRenderer;
import satisfyu.vinery.client.gui.*;
import satisfyu.vinery.client.render.block.FlowerBoxBlockRenderer;
import satisfyu.vinery.client.render.block.StorageBlockEntityRenderer;
import satisfyu.vinery.client.model.MuleModel;
import satisfyu.vinery.client.render.entity.MuleRenderer;
import satisfyu.vinery.client.render.entity.WanderingWinemakerRenderer;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.render.FlowerPotBlockEntityRenderer;
import satisfyu.vinery.util.networking.VineryMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new VineryIdentifier("trader_mule"), "main");

    @Override
    public void onInitializeClient() {
        VineryMessages.registerS2CPackets();
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ObjectRegistry.CHERRY_SIGN.getTexture()));
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.RED_GRAPE_BUSH, ObjectRegistry.WHITE_GRAPE_BUSH,
                                                ObjectRegistry.CHERRY_DOOR, ObjectRegistry.COOKING_POT,
                                                ObjectRegistry.CHERRY_JAM, ObjectRegistry.CHERRY_JAR, ObjectRegistry.FERMENTATION_BARREL,
                                                ObjectRegistry.MELLOHI_WINE, ObjectRegistry.CLARK_WINE, ObjectRegistry.BOLVAR_WINE, ObjectRegistry.CHERRY_WINE,
                                                ObjectRegistry.KING_DANIS_WINE, ObjectRegistry.CHERRY_JAR, ObjectRegistry.CHENET_WINE, ObjectRegistry.MELLOHI_WINE,
                                                ObjectRegistry.NOIR_WINE, ObjectRegistry.WINE_BOTTLE, ObjectRegistry.TABLE, ObjectRegistry.APPLE_CIDER,
                                                ObjectRegistry.APPLE_JAM, ObjectRegistry.APPLE_JUICE, ObjectRegistry.APPLE_WINE, ObjectRegistry.SOLARIS_WINE, ObjectRegistry.JELLIE_WINE,
                                                ObjectRegistry.AEGIS_WINE, ObjectRegistry.SWEETBERRY_JAM, ObjectRegistry.GRAPE_JAM, ObjectRegistry.KELP_CIDER, ObjectRegistry.SAVANNA_RED_GRAPE_BUSH,
                                                ObjectRegistry.SAVANNA_WHITE_GRAPE_BUSH, ObjectRegistry.CHORUS_WINE, ObjectRegistry.STAL_WINE, ObjectRegistry.MAGNETIC_WINE, ObjectRegistry.STRAD_WINE


        );
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ObjectRegistry.GRAPEVINE_STEM, ObjectRegistry.WINE_BOTTLE,
                                               ObjectRegistry.RED_GRAPEJUICE_WINE_BOTTLE, ObjectRegistry.WHITE_GRAPEJUICE_WINE_BOTTLE,
                                               ObjectRegistry.WINE_BOX, ObjectRegistry.FLOWER_POT,
                                               ObjectRegistry.CHAIR,
                                               ObjectRegistry.WINE_PRESS, ObjectRegistry.GRASS_SLAB, ObjectRegistry.CHERRY_JAR,
                                               ObjectRegistry.CHERRY_SAPLING, ObjectRegistry.OLD_CHERRY_SAPLING, ObjectRegistry.KITCHEN_SINK, ObjectRegistry.STACKABLE_LOG
                                              );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ObjectRegistry.WINDOW);
        
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> BiomeColors.getGrassColor(world, pos), ObjectRegistry.GRASS_SLAB);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> BiomeColors.getFoliageColor(world, pos), ObjectRegistry.SAVANNA_RED_GRAPE_BUSH, ObjectRegistry.SAVANNA_WHITE_GRAPE_BUSH, ObjectRegistry.TAIGA_RED_GRAPE_BUSH, ObjectRegistry.TAIGA_WHITE_GRAPE_BUSH);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(1.0, 0.5), ObjectRegistry.GRASS_SLAB);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return -1;
            }
            return BiomeColors.getWaterColor(world, pos);
            }, ObjectRegistry.KITCHEN_SINK);

        HandledScreens.register(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, StoveGui::new);
        HandledScreens.register(VineryScreenHandlerTypes.FERMENTATION_BARREL_GUI_HANDLER, FermentationBarrelGui::new);
        HandledScreens.register(VineryScreenHandlerTypes.COOKING_POT_SCREEN_HANDLER, CookingPotGui::new);
        HandledScreens.register(VineryScreenHandlerTypes.WINE_PRESS_SCREEN_HANDLER, WinePressGui::new);

        TerraformBoatClientHelper.registerModelLayers(new VineryIdentifier("cherry"));


        EntityRendererRegistry.register(VineryEntites.MULE, MuleRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(LAYER_LOCATION, MuleModel::getTexturedModelData);

        EntityRendererRegistry.register(VineryEntites.WANDERING_WINEMAKER, WanderingWinemakerRenderer::new);
        EntityRendererRegistry.register(VineryBlockEntityTypes.CHAIR, ChairRenderer::new);

        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.FLOWER_POT_ENTITY, FlowerPotBlockEntityRenderer::new);

        CustomArmorRegistry.registerModels();

        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.STORAGE_ENTITY, StorageBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.FLOWER_BOX_ENTITY, FlowerBoxBlockRenderer::new);
    }
    
    public static PlayerEntity getClientPlayer() {
        return MinecraftClient.getInstance().player;
    }
    
}