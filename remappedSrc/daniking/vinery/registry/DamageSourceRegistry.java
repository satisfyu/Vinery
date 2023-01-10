package daniking.vinery.registry;

import daniking.vinery.Vinery;
import net.minecraft.world.damagesource.DamageSource;

public class DamageSourceRegistry extends DamageSource {

public static final DamageSource STOVE_BLOCK = (new DamageSourceRegistry("stove_block")).setIsFire();

protected DamageSourceRegistry(String name) {
        super(Vinery.MODID + "." + name);
        }
}