package net.satisfy.vinery.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.client.VineryClient;

@Mod.EventBusSubscriber(modid = Vinery.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VineryClientForge {

    @SubscribeEvent
    public static void onClientSetup(RegisterEvent event) {
        VineryClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        VineryClient.onInitializeClient();
    }

}
