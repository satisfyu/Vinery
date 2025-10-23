package net.satisfy.vinery.neoforge.client;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.vinery.client.VineryClient;
import net.satisfy.vinery.client.gui.ApplePressGui;
import net.satisfy.vinery.client.gui.FermentationBarrelGui;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.block.state.properties.VineryWoodType;
import net.satisfy.vinery.core.entity.DarkCherryBoatEntity;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import net.satisfy.vinery.core.registry.ScreenhandlerTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

@EventBusSubscriber(modid = Vinery.MOD_ID, value = Dist.CLIENT)
public class VineryClientNeoForge {

    @SubscribeEvent
    public static void onClientSetup(RegisterEvent event) {
        VineryClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        VineryClient.onInitializeClient();
        Sheets.addWoodType(VineryWoodType.DARK_CHERRY);
        BlockEntityRenderers.register(EntityTypeRegistry.MOD_SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register(EntityTypeRegistry.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path packPath = ModList.get().getModFileById(Vinery.MOD_ID)
                    .getFile()
                    .findResource("resourcepacks/bushy_leaves");

            event.addRepositorySource(consumer -> {
                PackLocationInfo packLocationInfo = new PackLocationInfo(
                        ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "bushy_leaves").toString(),
                        Component.literal("Bushy Leaves for Vinery"),
                        PackSource.BUILT_IN,
                        Optional.empty()
                );

                Pack.ResourcesSupplier resourcesSupplier = new Pack.ResourcesSupplier() {
                    @Override
                    public @NotNull PathPackResources openPrimary(PackLocationInfo info) {
                        return new PathPackResources(info, packPath);
                    }

                    @Override
                    public @NotNull PackResources openFull(PackLocationInfo info, Pack.Metadata metadata) {
                        return new PathPackResources(info, packPath);
                    }
                };

                Pack pack = Pack.readMetaAndCreate(
                        packLocationInfo,
                        resourcesSupplier,
                        PackType.CLIENT_RESOURCES,
                        new PackSelectionConfig(false, Pack.Position.TOP, false)
                );

                if (pack != null) {
                    consumer.accept(pack);
                }
            });
        }
    }


    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (DarkCherryBoatEntity.Type type : DarkCherryBoatEntity.Type.values()) {
            event.registerLayerDefinition(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, type.getModelLocation()), "main"), BoatModel::createBodyModel);
            event.registerLayerDefinition(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, type.getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
        }
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ScreenhandlerTypeRegistry.APPLE_PRESS_GUI_HANDLER.get(), ApplePressGui::new);
        event.register(ScreenhandlerTypeRegistry.FERMENTATION_BARREL_GUI_HANDLER.get(), FermentationBarrelGui::new);
    }
}
