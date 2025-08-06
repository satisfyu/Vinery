package net.satisfy.vinery.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.satisfy.vinery.core.Vinery;

public class SoundEventRegistry {
    private static final Registrar<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Vinery.MOD_ID, Registries.SOUND_EVENT).getRegistrar();

    public static final RegistrySupplier<SoundEvent> BLOCK_GRAPEVINE_POT_SQUEEZE = create();
    public static final RegistrySupplier<SoundEvent> CABINET_OPEN = create("cabinet_open");
    public static final RegistrySupplier<SoundEvent> CABINET_CLOSE = create("cabinet_close");
    public static final RegistrySupplier<SoundEvent> DRAWER_OPEN = create("drawer_open");
    public static final RegistrySupplier<SoundEvent> DRAWER_CLOSE = create("drawer_close");

    private static RegistrySupplier<SoundEvent> create() {
        final ResourceLocation id = Vinery.identifier("block.grapevine_pot.squeeze");
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    private static RegistrySupplier<SoundEvent> create(String name) {
        ResourceLocation id = Vinery.identifier(name);
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {
    }
}
