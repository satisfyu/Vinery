package satisfyu.vinery.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.forge.registry.VineryForgeVillagers;

@Mod(Vinery.MODID)
public class VineryForge {

    public VineryForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Vinery.MODID, modEventBus);

        Vinery.init();
        VineryForgeVillagers.register(modEventBus);




        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(VineryForgeVillagers::registerPOIs);
        Vinery.commonSetup();
    }



}
