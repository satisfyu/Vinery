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
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GrassColor;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.entity.chair.ChairRenderer;
import satisfyu.vinery.client.gui.CookingPotGui;
import satisfyu.vinery.client.gui.FermentationBarrelGui;
import satisfyu.vinery.client.gui.StoveGui;
import satisfyu.vinery.client.gui.WinePressGui;
import satisfyu.vinery.client.model.MuleModel;
import satisfyu.vinery.client.render.block.FlowerPotBlockEntityRenderer;
import satisfyu.vinery.client.render.block.storage.api.StorageBlockEntityRenderer;
import satisfyu.vinery.client.render.block.WineBottleRenderer;
import satisfyu.vinery.client.render.entity.MuleRenderer;
import satisfyu.vinery.client.render.entity.WanderingWinemakerRenderer;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.util.boat.api.client.TerraformBoatClientHelper;
import satisfyu.vinery.util.boat.impl.client.TerraformBoatClientInitializer;
import satisfyu.vinery.util.networking.VineryMessages;
import satisfyu.vinery.util.sign.SpriteIdentifierRegistry;

import java.util.Map;
import java.util.function.Supplier;

import static satisfyu.vinery.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class VineryClient {

    public static boolean rememberedRecipeBookOpen = false;
    public static boolean rememberedCraftableToggle = true;



    public static void onInitializeClient() {
        VineryMessages.registerS2CPackets();



        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new Material(Sheets.SIGN_SHEET, CHERRY_SIGN.get().getTexture()));
        RenderTypeRegistry.register(RenderType.cutout(),
                RED_GRAPE_BUSH.get(), WHITE_GRAPE_BUSH.get(), CHERRY_DOOR.get(), COOKING_POT.get(),
                SWEETBERRY_JAM.get(), CHERRY_JAM.get(), CHERRY_JAR.get(), FERMENTATION_BARREL.get(),
                MELLOHI_WINE.get(), CLARK_WINE.get(), BOLVAR_WINE.get(), CHERRY_WINE.get(),
                KING_DANIS_WINE.get(), CHENET_WINE.get(), NOIR_WINE.get(), TABLE.get(), APPLE_CIDER.get(),
                APPLE_JAM.get(), APPLE_WINE.get(), SOLARIS_WINE.get(), JELLIE_WINE.get(), AEGIS_WINE.get(),
                GRAPE_JAM.get(), KELP_CIDER.get(), SAVANNA_RED_GRAPE_BUSH.get(), SAVANNA_WHITE_GRAPE_BUSH.get(),
                CHORUS_WINE.get(), STAL_WINE.get(), MAGNETIC_WINE.get(), STRAD_WINE.get(), JUNGLE_WHITE_GRAPE_BUSH.get(),
                JUNGLE_RED_GRAPE_BUSH.get(), TAIGA_RED_GRAPE_BUSH.get(), TAIGA_WHITE_GRAPE_BUSH.get(),
                GRAPEVINE_STEM.get(), GRAPEVINE_LATTICE.get(), WINE_BOX.get(), FLOWER_POT.get(), CHAIR.get(),
                WINE_PRESS.get(), GRASS_SLAB.get(), CHERRY_JAR.get(), CHERRY_SAPLING.get(), OLD_CHERRY_SAPLING.get(),
                KITCHEN_SINK.get(), STACKABLE_LOG.get()
        );

        RenderTypeRegistry.register(RenderType.translucent(), WINDOW.get());



        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> BiomeColors.getAverageGrassColor(world, pos), GRASS_SLAB, TAIGA_WHITE_GRAPE_BUSH, TAIGA_RED_GRAPE_BUSH);
        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> BiomeColors.getAverageFoliageColor(world, pos), SAVANNA_RED_GRAPE_BUSH, SAVANNA_WHITE_GRAPE_BUSH, JUNGLE_RED_GRAPE_BUSH, JUNGLE_WHITE_GRAPE_BUSH, GRAPEVINE_STEM, GRAPEVINE_LATTICE);
        /*ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return -1;
            }
            return BiomeColors.getAverageWaterColor(world, pos);
            }, KITCHEN_SINK);

         */
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> GrassColor.get(0.5, 1.0), GRASS_SLAB);



        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.STOVE_GUI_HANDLER.get(), StoveGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.FERMENTATION_BARREL_GUI_HANDLER.get(), FermentationBarrelGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.COOKING_POT_SCREEN_HANDLER.get(), CookingPotGui::new);
        MenuRegistry.registerScreenFactory(VineryScreenHandlerTypes.WINE_PRESS_SCREEN_HANDLER.get(), WinePressGui::new);



        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.FLOWER_POT_ENTITY.get(), FlowerPotBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.WINE_BOTTLE_ENTITY.get(), WineBottleRenderer::new);
        BlockEntityRendererRegistry.register(VineryBlockEntityTypes.STORAGE_ENTITY.get(), StorageBlockEntityRenderer::new);
    }



    public static void getEntityModelLayers(Map<ModelLayerLocation, Supplier<LayerDefinition>> map){
        map.put(MuleModel.LAYER_LOCATION, MuleModel::getTexturedModelData);
        TerraformBoatClientHelper.registerModelLayers(map, new VineryIdentifier("cherry"));

        //"API"
        CustomArmorRegistry.registerArmorModelLayers(map);
    }

    public static void getEntityEntityRenderers(Map<Supplier<EntityType<?>>, EntityRendererProvider<?>> map){
        registerEntityRenderer(map, VineryEntites.MULE, MuleRenderer::new);
        registerEntityRenderer(map, VineryEntites.WANDERING_WINEMAKER, WanderingWinemakerRenderer::new);
        registerEntityRenderer(map, VineryEntites.CHAIR, ChairRenderer::new);

        //"API"
        TerraformBoatClientInitializer.init(map);
    }



    
    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static <T extends Entity> void registerEntityRenderer(Map<Supplier<EntityType<?>>, EntityRendererProvider<?>> map, Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> factory) {
        map.put((Supplier<EntityType<?>>) (Supplier<? extends EntityType<?>>) type, factory);
    }
    
}