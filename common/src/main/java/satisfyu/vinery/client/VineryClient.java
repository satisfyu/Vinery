package satisfyu.vinery.client;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GrassColor;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.entity.chair.ChairRenderer;
import satisfyu.vinery.client.gui.CookingPotGui;
import satisfyu.vinery.client.gui.FermentationBarrelGui;
import satisfyu.vinery.client.gui.StoveGui;
import satisfyu.vinery.client.gui.WinePressGui;
import satisfyu.vinery.client.model.MuleModel;
import satisfyu.vinery.client.render.block.FlowerBoxBlockRenderer;
import satisfyu.vinery.client.render.block.FlowerPotBlockEntityRenderer;
import satisfyu.vinery.client.render.block.StorageBlockEntityRenderer;
import satisfyu.vinery.client.render.block.WineBottleRenderer;
import satisfyu.vinery.client.render.entity.MuleRenderer;
import satisfyu.vinery.client.render.entity.WanderingWinemakerRenderer;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.util.boat.api.client.TerraformBoatClientHelper;
import satisfyu.vinery.util.boat.impl.client.TerraformBoatClientInitializer;
import satisfyu.vinery.util.networking.VineryMessages;
import satisfyu.vinery.util.sign.SpriteIdentifierRegistry;

@Environment(EnvType.CLIENT)
public class VineryClient {

    public static boolean rememberedRecipeBookOpen = false;
    public static boolean rememberedCraftableToggle = true;



    public static void onInitializeClient() {

        VineryMessages.registerS2CPackets();
        if(!Platform.isForge()) registerEntityRenderers();



        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new Material(Sheets.SIGN_SHEET, ObjectRegistry.CHERRY_SIGN.get().getTexture()));

        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.RED_GRAPE_BUSH.get(), ObjectRegistry.WHITE_GRAPE_BUSH.get(),
                                                ObjectRegistry.CHERRY_DOOR.get(), ObjectRegistry.COOKING_POT.get(),
                                                ObjectRegistry.SWEETBERRY_JAM.get(), ObjectRegistry.CHERRY_JAM.get(), ObjectRegistry.CHERRY_JAR.get(), ObjectRegistry.FERMENTATION_BARREL.get(),
                                                ObjectRegistry.MELLOHI_WINE.get(), ObjectRegistry.CLARK_WINE.get(), ObjectRegistry.BOLVAR_WINE.get(), ObjectRegistry.CHERRY_WINE.get(),
                                                ObjectRegistry.KING_DANIS_WINE.get(), ObjectRegistry.CHERRY_JAR.get(), ObjectRegistry.CHENET_WINE.get(), ObjectRegistry.MELLOHI_WINE.get(),
                                                ObjectRegistry.NOIR_WINE.get(), ObjectRegistry.WINE_BOTTLE.get(), ObjectRegistry.TABLE.get(), ObjectRegistry.APPLE_CIDER.get(),
                                                ObjectRegistry.APPLE_JAM.get(), ObjectRegistry.APPLE_WINE.get(), ObjectRegistry.SOLARIS_WINE.get(), ObjectRegistry.JELLIE_WINE.get(),
                                                ObjectRegistry.AEGIS_WINE.get(), ObjectRegistry.GRAPE_JAM.get(), ObjectRegistry.KELP_CIDER.get(), ObjectRegistry.SAVANNA_RED_GRAPE_BUSH.get(),
                                                ObjectRegistry.SAVANNA_WHITE_GRAPE_BUSH.get(), ObjectRegistry.CHORUS_WINE.get(), ObjectRegistry.STAL_WINE.get(), ObjectRegistry.MAGNETIC_WINE.get(), ObjectRegistry.STRAD_WINE.get(),
                                                ObjectRegistry.JUNGLE_WHITE_GRAPE_BUSH.get(), ObjectRegistry.JUNGLE_RED_GRAPE_BUSH.get(), ObjectRegistry.TAIGA_RED_GRAPE_BUSH.get(), ObjectRegistry.TAIGA_WHITE_GRAPE_BUSH.get(),
                                                ObjectRegistry.TOMATO_CROP.get()
        );
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.PALE_STEM.get(), ObjectRegistry.GRAPEVINE_LATTICE.get(), ObjectRegistry.WINE_BOTTLE.get(),

                                               ObjectRegistry.WINE_BOX.get(), ObjectRegistry.FLOWER_POT.get(),
                                               ObjectRegistry.CHAIR.get(),
                                               ObjectRegistry.WINE_PRESS.get(), ObjectRegistry.GRASS_SLAB.get(), ObjectRegistry.CHERRY_JAR.get(),
                                               ObjectRegistry.CHERRY_SAPLING.get(), ObjectRegistry.OLD_CHERRY_SAPLING.get(), ObjectRegistry.KITCHEN_SINK.get(), ObjectRegistry.STACKABLE_LOG.get(),
                                               ObjectRegistry.JUNGLE_RED_GRAPE_BUSH.get(), ObjectRegistry.JUNGLE_WHITE_GRAPE_BUSH.get()
                                              );

        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.WINDOW.get());


        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> BiomeColors.getAverageGrassColor(world, pos), ObjectRegistry.GRASS_SLAB, ObjectRegistry.TAIGA_WHITE_GRAPE_BUSH, ObjectRegistry.TAIGA_RED_GRAPE_BUSH);
        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> BiomeColors.getAverageFoliageColor(world, pos), ObjectRegistry.SAVANNA_RED_GRAPE_BUSH, ObjectRegistry.SAVANNA_WHITE_GRAPE_BUSH, ObjectRegistry.JUNGLE_RED_GRAPE_BUSH, ObjectRegistry.JUNGLE_WHITE_GRAPE_BUSH/*, ObjectRegistry.PALE_STEM_BLOCK, ObjectRegistry.GRAPEVINE_LATTICE*/);
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> GrassColor.get(1.0, 0.5), ObjectRegistry.GRASS_SLAB);
        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return -1;
            }
            return BiomeColors.getAverageWaterColor(world, pos);
            }, ObjectRegistry.KITCHEN_SINK);






        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.STOVE_GUI_HANDLER.get(), StoveGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.FERMENTATION_BARREL_GUI_HANDLER.get(), FermentationBarrelGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.COOKING_POT_SCREEN_HANDLER.get(), CookingPotGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.WINE_PRESS_SCREEN_HANDLER.get(), WinePressGui::new);

        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.FLOWER_POT_ENTITY.get(), FlowerPotBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.WINE_BOTTLE_ENTITY.get(), WineBottleRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.STORAGE_ENTITY.get(), StorageBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.FLOWER_BOX_ENTITY.get(), FlowerBoxBlockRenderer::new);

    }

    private static void registerEntityRenderers(){
        //renderers
        TerraformBoatClientInitializer.init();
        EntityRendererRegistry.register(VineryEntites.MULE, MuleRenderer::new);
        EntityRendererRegistry.register(VineryEntites.WANDERING_WINEMAKER, WanderingWinemakerRenderer::new);
        EntityRendererRegistry.register(VineryEntites.CHAIR, ChairRenderer::new);

        //layers
        EntityModelLayerRegistry.register(MuleModel.LAYER_LOCATION, MuleModel::getTexturedModelData);
        CustomArmorRegistry.registerArmorModelLayers();
        TerraformBoatClientHelper.registerModelLayers(VineryBoatTypes.CHERRY_BOAT_ID, false);
    }

    
    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
    
}