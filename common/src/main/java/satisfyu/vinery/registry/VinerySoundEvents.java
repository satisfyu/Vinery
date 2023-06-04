package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class VinerySoundEvents {
    private static final Registrar<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Vinery.MODID, Registry.SOUND_EVENT_REGISTRY).getRegistrar();

    public static final RegistrySupplier<SoundEvent> BLOCK_GRAPEVINE_POT_SQUEEZE = create("block.grapevine_pot.squeeze");
    public static final RegistrySupplier<SoundEvent> BLOCK_FAUCET = create("block.kitchen_sink.faucet");
    public static final RegistrySupplier<SoundEvent> WINE_RACK_3_OPEN = create("block.wine_rack_3.open");
    public static final RegistrySupplier<SoundEvent> WINE_RACK_3_CLOSE = create("block.wine_rack_3.close");
    public static final RegistrySupplier<SoundEvent> WINE_RACK_5_OPEN = create("block.wine_rack_5.open");
    public static final RegistrySupplier<SoundEvent> WINE_RACK_5_CLOSE = create("block.wine_rack_5.close");


    private static RegistrySupplier<SoundEvent> create(String name) {
        final ResourceLocation id = new VineryIdentifier(name);
        return SOUND_EVENTS.register(id, () -> new SoundEvent(id));
    }

    public static void init() {
        
    }
}
