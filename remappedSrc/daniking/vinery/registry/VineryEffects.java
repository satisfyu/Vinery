package daniking.vinery.registry;

import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.effect.EmptyEffect;
import daniking.vinery.effect.JellieEffect;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;

public class VineryEffects {


    public static final MobEffect EMPTY = registerEffekt("empty", new EmptyEffect());

    public static final MobEffect JELLIE = registerEffekt("jellie", new JellieEffect());

    public static void init(){
        Vinery.LOGGER.debug("Mob effects");
    }

    private static MobEffect registerEffekt(String name, MobEffect effect){
        return Registry.register(Registry.MOB_EFFECT, new VineryIdentifier(name), effect);
    }
}
