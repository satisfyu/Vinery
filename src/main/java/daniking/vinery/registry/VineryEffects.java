package daniking.vinery.registry;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.effect.EmptyEffect;
import daniking.vinery.effect.JellieEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class VineryEffects {

    private static final Map<Identifier, StatusEffect> EFFECTS = new LinkedHashMap<>();

    public static final StatusEffect EMPTY = registerEffekt("empty", new EmptyEffect());

    public static final StatusEffect JELLIE = registerEffekt("jellie", new JellieEffect());

    public static void init(){
        for (Map.Entry<Identifier, StatusEffect> entry : EFFECTS.entrySet()) {
            Registry.register(Registry.STATUS_EFFECT, entry.getKey(), entry.getValue());
        }
    }

    private static StatusEffect registerEffekt(String name, StatusEffect effect){
        return EFFECTS.put(new VineryIdentifier(name), effect);
    }
}
