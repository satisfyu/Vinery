package satisfyu.vinery.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;

@Mod(Vinery.MODID)
public class VineryForge {
    public VineryForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Vinery.MODID, modEventBus);
        Vinery.init();

        modEventBus.addListener(this::commonSetup);

        DeferredRegister<TerraformBoatType> terraformBoatTypeDeferredRegister = DeferredRegister.create(ResourceKey.createRegistryKey(TerraformBoatTypeRegistry.REGISTRY_ID), "boat");

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Vinery.commonSetup();
    }
}
