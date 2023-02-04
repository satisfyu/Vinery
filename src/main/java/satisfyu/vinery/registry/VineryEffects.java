package satisfyu.vinery.registry;

import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.effect.EmptyEffect;
import satisfyu.vinery.effect.JellieEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class VineryEffects {


    public static final StatusEffect EMPTY = registerEffekt("empty", new EmptyEffect());

    public static final StatusEffect JELLIE = registerEffekt("jellie", new JellieEffect());

    public static void init(){
        Vinery.LOGGER.debug("Mob effects");
    }

    private static StatusEffect registerEffekt(String name, StatusEffect effect){
        return Registry.register(Registry.STATUS_EFFECT, new VineryIdentifier(name), effect);
    }
}
