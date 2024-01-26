package satisfyu.vinery.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
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
    public static final RegistrySupplier<MobEffect> IMPROVED_JUMP_BOOST;
    public static final RegistrySupplier<MobEffect> WATER_WALKER;
    public static final RegistrySupplier<MobEffect> CREEPER_EFFECT;
    public static final RegistrySupplier<MobEffect> EXPERIENCE_EFFECT;
    public static final RegistrySupplier<MobEffect> LAVA_WALKER;
    public static final RegistrySupplier<MobEffect> STAGGER_EFFECT;
    public static final RegistrySupplier<MobEffect> FROSTY_ARMOR_EFFECT;
    public static final RegistrySupplier<MobEffect> PARTY_EFFECT;
    public static final RegistrySupplier<MobEffect> CLIMBING_EFFECT;
    public static final RegistrySupplier<MobEffect> LUCK_EFFECT;
    public static final RegistrySupplier<MobEffect> HEALTH_EFFECT;
    public static final RegistrySupplier<MobEffect> RESISTANCE_EFFECT;
    public static final RegistrySupplier<MobEffect> ARMOR_EFFECT;

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
        LAVA_WALKER = registerEffect("lava_walker", LavaWalkerEffect::new);
        JELLIE = registerEffect("jellie", JellieEffect::new);
        MAGNET = registerEffect("magnet", MagnetEffect::new);
        TELEPORT = registerEffect("teleport", TeleportEffect::new);
        CREEPER_EFFECT = registerEffect("creeper_effect", CreeperEffect::new);
        IMPROVED_JUMP_BOOST = registerEffect("double_jump", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0x90F891));
        WATER_WALKER = registerEffect("water_walker", WaterWalkerEffect::new);
        STAGGER_EFFECT = registerEffect("staggering", StaggerEffect::new);
        FROSTY_ARMOR_EFFECT = registerEffect("frosty_armor", FrostyArmorEffect::new);
        PARTY_EFFECT = registerEffect("party_effect", PartyEffect::new);
        CLIMBING_EFFECT = registerEffect("climbing_effect", ClimbingEffect::new);
        LUCK_EFFECT = registerEffect("luck_effect", LuckEffect::new);
        HEALTH_EFFECT = registerEffect("health_effect", ImprovedHealthEffect::new);
        RESISTANCE_EFFECT = registerEffect("resistance_effect", ResistanceEffect::new);
        ARMOR_EFFECT = registerEffect("armor_effect", ArmorEffect::new);
    }
}
