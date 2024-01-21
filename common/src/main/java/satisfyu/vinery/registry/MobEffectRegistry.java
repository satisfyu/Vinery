package satisfyu.vinery.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.effect.*;

import java.util.function.Supplier;

public class MobEffectRegistry {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Vinery.MOD_ID, Registries.MOB_EFFECT);
    private static final Registrar<MobEffect> MOB_EFFECTS_REGISTRAR = MOB_EFFECTS.getRegistrar();

    public static final RegistrySupplier<MobEffect> TRIPPY;
    public static final RegistrySupplier<MobEffect> JELLIE;
    public static final RegistrySupplier<MobEffect> MAGNET;
    public static final RegistrySupplier<MobEffect> TELEPORT;
    public static final RegistrySupplier<MobEffect> IMPROVED_SPEED;
    public static final RegistrySupplier<MobEffect> IMPROVED_ABSORBTION;
    public static final RegistrySupplier<MobEffect> IMPROVED_JUMP_BOOST;
    public static final RegistrySupplier<MobEffect> IMPROVED_STRENGTH;
    public static final RegistrySupplier<MobEffect> IMPROVED_INSTANT_HEALTH;
    public static final RegistrySupplier<MobEffect> IMPROVED_REGENERATION;
    public static final RegistrySupplier<MobEffect> IMPROVED_FIRE_RESISTANCE;
    public static final RegistrySupplier<MobEffect> IMPROVED_WATER_BREATHING;
    public static final RegistrySupplier<MobEffect> IMPROVED_NIGHT_VISION;
    public static final RegistrySupplier<MobEffect> IMPROVED_HASTE;
    public static final RegistrySupplier<MobEffect> CREEPER_EFFECT;
    public static final RegistrySupplier<MobEffect> EXPERIENCE_EFFECT;


    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect){
        if(Platform.isForge()){
            return MOB_EFFECTS.register(name, effect);
        }
        return MOB_EFFECTS_REGISTRAR.register(new VineryIdentifier(name), effect);
    }

    public static void init(){
        Vinery.LOGGER.debug("Mob effects");
        MOB_EFFECTS.register();
    }

    static {
        EXPERIENCE_EFFECT = registerEffect("experience_effect", ExperienceEffect::new);
        TRIPPY = registerEffect("trippy", TrippyEffect::new);
        JELLIE = registerEffect("jellie", JellieEffect::new);
        MAGNET = registerEffect("magnet", MagnetEffect::new);
        TELEPORT = registerEffect("teleport", TeleportEffect::new);
        CREEPER_EFFECT = registerEffect("creeper_effect", CreeperEffect::new);
        IMPROVED_JUMP_BOOST = registerEffect("improved_jump_boost", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0x90F891));
         }
}
