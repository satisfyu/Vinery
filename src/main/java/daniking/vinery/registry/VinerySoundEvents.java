package daniking.vinery.registry;

import daniking.vinery.VineryIdentifier;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class VinerySoundEvents {

    private static final Map<Identifier, SoundEvent> SOUND_EVENTS = new LinkedHashMap<>();

    public static final SoundEvent BLOCK_GRAPEVINE_POT_SQUEEZE = create("block.grapevine_pot.squeeze");
    public static final SoundEvent BLOCK_COOKING_POT_JUICE_BOILING = create("block.cooking_pot.juice_boiling");
    public static final SoundEvent BLOCK_FAUCET = create("block.kitchen_sink.faucet");
    public static final SoundEvent WINE_RACK_3_OPEN = create("block.wine_rack_3.open");
    public static final SoundEvent WINE_RACK_3_CLOSE = create("block.wine_rack_3.close");
    public static final SoundEvent WINE_RACK_5_OPEN = create("block.wine_rack_5.open");
    public static final SoundEvent WINE_RACK_5_CLOSE = create("block.wine_rack_5.close");

    private static SoundEvent create(String name) {
        final Identifier id = new VineryIdentifier(name);
        final SoundEvent event = new SoundEvent(id);
        SOUND_EVENTS.put(id, event);
        return event;
    }

    public static void init() {
        SOUND_EVENTS.keySet().forEach(soundEvent -> Registry.register(Registry.SOUND_EVENT, soundEvent, SOUND_EVENTS.get(soundEvent)));
    }
}
