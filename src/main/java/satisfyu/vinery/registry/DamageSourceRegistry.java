package satisfyu.vinery.registry;

import satisfyu.vinery.Vinery;
import net.minecraft.entity.damage.DamageSource;

public class DamageSourceRegistry extends DamageSource {

public static final DamageSource STOVE_BLOCK = (new DamageSourceRegistry("stove_block")).setFire();

protected DamageSourceRegistry(String name) {
        super(Vinery.MODID + "." + name);
        }
}