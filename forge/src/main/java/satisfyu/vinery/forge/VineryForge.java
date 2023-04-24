package satisfyu.vinery.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.forge.registry.VineryForgeVillagers;
import satisfyu.vinery.registry.VineryBoatTypes;
import satisfyu.vinery.registry.VineryVillagers;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;

import java.util.Map;
import java.util.function.Supplier;

@Mod(Vinery.MODID)
public class VineryForge {

    public VineryForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Vinery.MODID, modEventBus);
        VineryForgeVillagers.register(modEventBus);

        Vinery.init();


        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(VineryForgeVillagers::registerPOIs);
        Vinery.commonSetup();
    }



}
