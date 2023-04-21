package satisfyu.vinery.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.block.entity.chair.ChairRenderer;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.client.render.entity.WanderingWinemakerRenderer;
import satisfyu.vinery.registry.VineryEntites;

@Mod.EventBusSubscriber(modid = Vinery.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VineryClientForge {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        VineryClient.onInitializeClient();

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(VineryEntites.CHAIR.get(), ChairRenderer::new);

        //event.registerEntityRenderer(VineryEntites.MULE.get(), MuleRenderer::new);
        //EntityModelLayerRegistry.register(VineryClient.LAYER_LOCATION, MuleModel::getTexturedModelData);


        event.registerEntityRenderer(VineryEntites.WANDERING_WINEMAKER.get(), WanderingWinemakerRenderer::new);
        //event.registerEntityRenderer(TerraformBoatInitializer.BOAT.get(), context -> new TerraformBoatEntityRenderer(context, false));
        //event.registerEntityRenderer(TerraformBoatInitializer.CHEST_BOAT.get(), context -> new TerraformBoatEntityRenderer(context, true));

    }


}
