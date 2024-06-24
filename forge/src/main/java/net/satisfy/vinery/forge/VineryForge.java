package net.satisfy.vinery.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.client.gui.config.ClothConfigScreen;
import net.satisfy.vinery.forge.registry.VineryForgeVillagers;
import net.satisfy.vinery.registry.CompostableRegistry;
import net.satisfy.vinery.util.PreInit;


@Mod(Vinery.MOD_ID)
public class VineryForge {

    public VineryForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Vinery.MOD_ID, modEventBus);
        PreInit.preInit();
        Vinery.init();
        VineryForgeVillagers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        if(isClothConfigLoaded()) ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> ClothConfigScreen.create(parent)));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
        Vinery.commonSetup();
    }
    public static boolean isClothConfigLoaded(){
        return ModList.get().isLoaded("cloth_config");
    }
}
