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
		modEventBus.addListener(this::commonSetup);
		Vinery.initForge();
		VineryForgeVillagers.register(modEventBus);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		Vinery.commonForge();
		event.enqueueWork(VineryForgeVillagers::registerPOIs);
	}
}
