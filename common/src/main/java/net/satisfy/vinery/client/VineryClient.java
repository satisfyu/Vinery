package net.satisfy.vinery.client;

import de.cristelknight.doapi.terraform.sign.TerraformSignHelper;
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
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GrassColor;
import net.satisfy.vinery.client.gui.ApplePressGui;
import net.satisfy.vinery.client.gui.BasketGui;
import net.satisfy.vinery.client.gui.FermentationBarrelGui;
import net.satisfy.vinery.client.model.MuleModel;
import net.satisfy.vinery.client.model.StrawHatModel;
import net.satisfy.vinery.client.render.block.BasketRenderer;
import net.satisfy.vinery.client.render.entity.MuleRenderer;
import net.satisfy.vinery.client.render.entity.WanderingWinemakerRenderer;
import net.satisfy.vinery.network.VineryNetwork;
import net.satisfy.vinery.registry.*;

import static net.satisfy.vinery.Vinery.LOGGER;
import static net.satisfy.vinery.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class VineryClient {
    public static void onInitializeClient() {

        VineryNetwork.registerS2CPackets();

        RenderTypeRegistry.register(RenderType.cutout(),
                RED_GRAPE_BUSH.get(), WHITE_GRAPE_BUSH.get(), DARK_CHERRY_DOOR.get(), FERMENTATION_BARREL.get(),
                MELLOHI_WINE.get(), CLARK_WINE.get(), BOLVAR_WINE.get(), CHERRY_WINE.get(),
                LILITU_WINE.get(), CHENET_WINE.get(), NOIR_WINE.get(), APPLE_CIDER.get(),
                APPLE_WINE.get(), SOLARIS_WINE.get(), JELLIE_WINE.get(), AEGIS_WINE.get(), KELP_CIDER.get(),
                SAVANNA_RED_GRAPE_BUSH.get(), SAVANNA_WHITE_GRAPE_BUSH.get(),
                CHORUS_WINE.get(), STAL_WINE.get(), MAGNETIC_WINE.get(), STRAD_WINE.get(), JUNGLE_WHITE_GRAPE_BUSH.get(),
                JUNGLE_RED_GRAPE_BUSH.get(), TAIGA_RED_GRAPE_BUSH.get(), TAIGA_WHITE_GRAPE_BUSH.get(),
                GRAPEVINE_STEM.get(), WINE_BOX.get(), DARK_CHERRY_WINE_RACK_MID.get(), DARK_CHERRY_WINE_RACK_BIG.get(),
                APPLE_PRESS.get(), GRASS_SLAB.get(), DARK_CHERRY_SAPLING.get(), APPLE_TREE_SAPLING.get(),
                STACKABLE_LOG.get(), APPLE_LEAVES.get(), POTTED_APPLE_TREE_SAPLING.get(), DARK_CHERRY_WINE_RACK_SMALL.get(),
                POTTED_DARK_CHERRY_TREE_SAPLING.get(), RED_WINE.get(), KNULP_WINE.get(),
                DARK_CHERRY_CHAIR.get(), CRISTEL_WINE.get(), VILLAGERS_FRIGHT.get(), EISWEIN.get(), CREEPERS_CRUSH.get(),
                GLOWING_WINE.get(), JO_SPECIAL_MIXTURE.get(), MEAD.get(), BOTTLE_MOJANG_NOIR.get(),
                DARK_CHERRY_TABLE.get(), OAK_WINE_RACK_MID.get(), DARK_OAK_WINE_RACK_MID.get(), BIRCH_WINE_RACK_MID.get(),
                SPRUCE_WINE_RACK_MID.get(), JUNGLE_WINE_RACK_MID.get(), MANGROVE_WINE_RACK_MID.get(), BAMBOO_WINE_RACK_MID.get(),
                ACACIA_WINE_RACK_MID.get(), OAK_LATTICE.get(), SPRUCE_LATTICE.get(),
                BIRCH_LATTICE.get(), DARK_OAK_LATTICE.get(), CHERRY_LATTICE.get(), BAMBOO_LATTICE.get(), ACACIA_LATTICE.get(), JUNGLE_LATTICE.get(),
                MANGROVE_LATTICE.get(), LAMROC_WINE.get(), COUNT_ROLEESTER_SHIRAZ_WINE.get()
        );

        ClientStorageTypes.init();
        RenderTypeRegistry.register(RenderType.translucent(), WINDOW.get());

        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> GrassColor.get(0.5, 1.0), GRASS_SLAB);
        ColorHandlerRegistry.registerBlockColors((state,world,pos,tintIndex)->{
                    if(world== null || pos == null){
                        return -1;
                    }
                    return BiomeColors.getAverageGrassColor(world,pos);
                },  GRASS_SLAB.get()
        );

        BlockEntityRendererRegistry.register(BlockEntityTypeRegistry.BASKET_ENTITY.get(), BasketRenderer::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.FERMENTATION_BARREL_GUI_HANDLER.get(), FermentationBarrelGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.APPLE_PRESS_GUI_HANDLER.get(), ApplePressGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.BASKET_GUI_HANDLER.get(), BasketGui::new);

    }


    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityRegistry.MULE, MuleRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.WANDERING_WINEMAKER, WanderingWinemakerRenderer::new);
    }


    public static void preInitClient(){
        registerEntityRenderers();
        TerraformSignHelper.regsterSignSprite(BoatAndSignRegistry.DARK_CHERRY_SIGN_TEXTURE);
        EntityModelLayerRegistry.register(StrawHatModel.LAYER_LOCATION, StrawHatModel::createBodyLayer);
        EntityModelLayerRegistry.register(MuleModel.LAYER_LOCATION, MuleModel::getTexturedModelData);
        EntityModelLayerRegistry.register(BasketRenderer.LAYER_LOCATION, BasketRenderer::getTexturedModelData);

        ArmorRegistry.registerArmorModelLayers();

        LOGGER.info("Resource provider initialized, side is {}", Platform.getEnvironment().toPlatform().toString());
    }
    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}