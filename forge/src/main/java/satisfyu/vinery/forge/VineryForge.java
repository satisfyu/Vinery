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
import satisfyu.vinery.registry.VineryBoatTypes;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;

import java.util.Map;
import java.util.function.Supplier;

@Mod(Vinery.MODID)
public class VineryForge {

    public static IForgeRegistry<TerraformBoatType> terraformBoatTypeDeferredRegister;

    public static Supplier<IForgeRegistry<TerraformBoatType>> typeSupplier;



    //public static DeferredRegister<TerraformBoatType> terraformBoatTypeDeferredRegister = DeferredRegister.create(ResourceKey.createRegistryKey(TerraformBoatTypeRegistry.REGISTRY_ID), Vinery.MODID);
    public VineryForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Vinery.MODID, modEventBus);
        Vinery.init();

        //terraformBoatTypeDeferredRegister.makeRegistry(() -> new RegistryBuilder<TerraformBoatType>().legacyName("boat").allowModification());

        DeferredRegister<TerraformBoatType> deferredRegister = DeferredRegister.create(TerraformBoatTypeRegistry.createRegistryKey(TerraformBoatTypeRegistry.REGISTRY_ID), Vinery.MODID);
        typeSupplier = deferredRegister.makeRegistry(RegistryBuilder::new);
        VineryBoatTypes.init();
        for(Map.Entry<ResourceLocation, Supplier<TerraformBoatType>> type : VineryExpectPlatformImpl.INSTANCE.entrySet()) deferredRegister.register(type.getKey().getPath(), type.getValue());
        deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        terraformBoatTypeDeferredRegister = typeSupplier.get();
        Vinery.commonSetup();
    }



}
