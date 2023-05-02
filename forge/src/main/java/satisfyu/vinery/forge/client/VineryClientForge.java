package satisfyu.vinery.forge.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.block.entity.chair.ChairRenderer;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.client.model.MuleModel;
import satisfyu.vinery.client.model.feature.StrawHatModel;
import satisfyu.vinery.client.render.entity.MuleRenderer;
import satisfyu.vinery.client.render.entity.WanderingWinemakerRenderer;
import satisfyu.vinery.registry.VineryBoatTypes;
import satisfyu.vinery.registry.VineryEntites;
import satisfyu.vinery.util.boat.api.client.CustomBoatClientHelper;
import satisfyu.vinery.util.boat.impl.TerraformBoatInitializer;
import satisfyu.vinery.util.boat.impl.client.CustomBoatEntityRenderer;

@Mod.EventBusSubscriber(modid = Vinery.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VineryClientForge {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        VineryClient.onInitializeClient();
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(VineryEntites.MULE.get(), MuleRenderer::new);
        event.registerEntityRenderer(VineryEntites.CHAIR.get(), ChairRenderer::new);
        event.registerEntityRenderer(VineryEntites.WANDERING_WINEMAKER.get(), WanderingWinemakerRenderer::new);
        event.registerEntityRenderer(TerraformBoatInitializer.BOAT.get(), context -> new CustomBoatEntityRenderer(context, false));
        event.registerEntityRenderer(TerraformBoatInitializer.CHEST_BOAT.get(), context -> new CustomBoatEntityRenderer(context, true));
    }

    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MuleModel.LAYER_LOCATION, MuleModel::getTexturedModelData);
        registerModelLayers(event, VineryBoatTypes.CHERRY_BOAT_ID, false);
        registerArmorModelLayers(event);
    }

    public static void registerArmorModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(StrawHatModel.LAYER_LOCATION, StrawHatModel::getTexturedModelData);
    }


    private static void registerModelLayer(EntityRenderersEvent.RegisterLayerDefinitions event, ResourceLocation boatId, boolean raft, boolean chest) {
        event.registerLayerDefinition(CustomBoatClientHelper.getLayer(boatId, raft, chest), CustomBoatClientHelper.getTexturedModelDataProvider(raft, chest));
    }
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event, ResourceLocation boatId, boolean raft) {
        registerModelLayer(event, boatId, raft, false);
        registerModelLayer(event, boatId, raft, true);
    }
}
