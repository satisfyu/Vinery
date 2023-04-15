package satisfyu.vinery.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarBuilder;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.forge.RegistrarManagerImpl;
import dev.architectury.registry.registries.options.RegistrarOption;
import dev.architectury.registry.registries.options.StandardRegistrarOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.util.boat.api.TerraformBoatType;

@Mod(Vinery.MODID)
public class ExampleModForge {
    public ExampleModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Vinery.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        Vinery.init();
    }
}
