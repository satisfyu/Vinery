package net.satisfy.vinery.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.client.gui.config.ClothConfigScreen;
import net.satisfy.vinery.neoforge.registry.VineryForgeVillagers;
import net.satisfy.vinery.registry.CompostableRegistry;
import net.satisfy.vinery.util.PreInit;


@Mod(Vinery.MOD_ID)
public class VineryNeoForge {

    public VineryNeoForge(IEventBus modEventBus) {
        PreInit.preInit();
        Vinery.init();
        VineryForgeVillagers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        if (isClothConfigLoaded())
            ModLoadingContext.get().registerExtensionPoint(
                    IConfigScreenFactory.class,
                    () -> (minecraft, screen) -> ClothConfigScreen.create(screen)
            );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
        Vinery.commonSetup();
    }

    public static boolean isClothConfigLoaded() {
        return ModList.get().isLoaded("cloth_config");
    }
}
