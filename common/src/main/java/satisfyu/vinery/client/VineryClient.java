package satisfyu.vinery.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.entity.chair.ChairRenderer;
import satisfyu.vinery.client.gui.*;
import satisfyu.vinery.client.render.block.FlowerBoxBlockRenderer;
import satisfyu.vinery.client.render.block.StorageBlockEntityRenderer;
import satisfyu.vinery.client.model.MuleModel;
import satisfyu.vinery.client.render.entity.MuleRenderer;
import satisfyu.vinery.client.render.entity.WanderingWinemakerRenderer;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.client.render.block.FlowerPotBlockEntityRenderer;
import satisfyu.vinery.client.render.block.WineBottleRenderer;
import satisfyu.vinery.util.boat.api.client.TerraformBoatClientHelper;
import satisfyu.vinery.util.boat.impl.client.TerraformBoatClientInitializer;
import satisfyu.vinery.util.networking.VineryMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GrassColor;

@Environment(EnvType.CLIENT)
public class VineryClient implements ClientModInitializer {

    public static boolean rememberedRecipeBookOpen = false;
    public static boolean rememberedCraftableToggle = true;

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new VineryIdentifier("trader_mule"), "main");

    @Override
    public void onInitializeClient() {
        TerraformBoatClientInitializer.init();
        VineryMessages.registerS2CPackets();
        CustomArmorRegistry.registerModels();
        TerraformBoatClientHelper.registerModelLayers(new VineryIdentifier("cherry"), false);


        /*
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new Material(Sheets.SIGN_SHEET, ObjectRegistry.CHERRY_SIGN.getTexture()));
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), ObjectRegistry.RED_GRAPE_BUSH, ObjectRegistry.WHITE_GRAPE_BUSH,
                                                ObjectRegistry.CHERRY_DOOR, ObjectRegistry.COOKING_POT,
                                                ObjectRegistry.CHERRY_JAM, ObjectRegistry.CHERRY_JAR, ObjectRegistry.FERMENTATION_BARREL,
                                                ObjectRegistry.MELLOHI_WINE, ObjectRegistry.CLARK_WINE, ObjectRegistry.BOLVAR_WINE, ObjectRegistry.CHERRY_WINE,
                                                ObjectRegistry.KING_DANIS_WINE, ObjectRegistry.CHERRY_JAR, ObjectRegistry.CHENET_WINE, ObjectRegistry.MELLOHI_WINE,
                                                ObjectRegistry.NOIR_WINE, ObjectRegistry.WINE_BOTTLE, ObjectRegistry.TABLE, ObjectRegistry.APPLE_CIDER,
                                                ObjectRegistry.APPLE_JAM, ObjectRegistry.APPLE_WINE, ObjectRegistry.SOLARIS_WINE, ObjectRegistry.JELLIE_WINE,
                                                ObjectRegistry.AEGIS_WINE, ObjectRegistry.SWEETBERRY_JAM, ObjectRegistry.GRAPE_JAM, ObjectRegistry.KELP_CIDER, ObjectRegistry.SAVANNA_RED_GRAPE_BUSH,
                                                ObjectRegistry.SAVANNA_WHITE_GRAPE_BUSH, ObjectRegistry.CHORUS_WINE, ObjectRegistry.STAL_WINE, ObjectRegistry.MAGNETIC_WINE, ObjectRegistry.STRAD_WINE,
                                                ObjectRegistry.JUNGLE_WHITE_GRAPE_BUSH, ObjectRegistry.JUNGLE_RED_GRAPE_BUSH, ObjectRegistry.TAIGA_RED_GRAPE_BUSH, ObjectRegistry.TAIGA_WHITE_GRAPE_BUSH,
                                                ObjectRegistry.TOMATO_CROP


        );
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), ObjectRegistry.PALE_STEM_BLOCK, ObjectRegistry.GRAPEVINE_LATTICE, ObjectRegistry.WINE_BOTTLE,

                                               ObjectRegistry.WINE_BOX, ObjectRegistry.FLOWER_POT,
                                               ObjectRegistry.CHAIR,
                                               ObjectRegistry.WINE_PRESS, ObjectRegistry.GRASS_SLAB, ObjectRegistry.CHERRY_JAR,
                                               ObjectRegistry.CHERRY_SAPLING, ObjectRegistry.OLD_CHERRY_SAPLING, ObjectRegistry.KITCHEN_SINK, ObjectRegistry.STACKABLE_LOG,
                                               ObjectRegistry.JUNGLE_RED_GRAPE_BUSH, ObjectRegistry.JUNGLE_WHITE_GRAPE_BUSH
                                              );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(), ObjectRegistry.WINDOW);

         */
        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> BiomeColors.getAverageGrassColor(world, pos), ObjectRegistry.GRASS_SLAB, ObjectRegistry.TAIGA_WHITE_GRAPE_BUSH, ObjectRegistry.TAIGA_RED_GRAPE_BUSH);
        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> BiomeColors.getAverageFoliageColor(world, pos), ObjectRegistry.SAVANNA_RED_GRAPE_BUSH, ObjectRegistry.SAVANNA_WHITE_GRAPE_BUSH, ObjectRegistry.JUNGLE_RED_GRAPE_BUSH, ObjectRegistry.JUNGLE_WHITE_GRAPE_BUSH, ObjectRegistry.PALE_STEM_BLOCK, ObjectRegistry.GRAPEVINE_LATTICE);
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> GrassColor.get(1.0, 0.5), ObjectRegistry.GRASS_SLAB);
        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return -1;
            }
            return BiomeColors.getAverageWaterColor(world, pos);
            }, ObjectRegistry.KITCHEN_SINK);



        EntityRendererRegistry.register(VineryEntites.MULE, MuleRenderer::new);
        EntityRendererRegistry.register(VineryEntites.WANDERING_WINEMAKER, WanderingWinemakerRenderer::new);
        EntityRendererRegistry.register(VineryEntites.CHAIR, ChairRenderer::new);
        EntityModelLayerRegistry.register(LAYER_LOCATION, MuleModel::getTexturedModelData);


        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.STOVE_GUI_HANDLER, StoveGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.FERMENTATION_BARREL_GUI_HANDLER, FermentationBarrelGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.COOKING_POT_SCREEN_HANDLER, CookingPotGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.WINE_PRESS_SCREEN_HANDLER, WinePressGui::new);

        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.FLOWER_POT_ENTITY.get(), FlowerPotBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.WINE_BOTTLE_ENTITY.get(), WineBottleRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.STORAGE_ENTITY.get(), StorageBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.FLOWER_BOX_ENTITY.get(), FlowerBoxBlockRenderer::new);

    }
    
    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
    
}