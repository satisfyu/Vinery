package net.satisfy.vinery.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.effect.*;

public class MobEffectRegistry {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Vinery.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> ARMOR_EFFECT = MOB_EFFECTS.register("armor_effect", ArmorEffect::new);
    public static final RegistrySupplier<MobEffect> HEALTH_EFFECT = MOB_EFFECTS.register("health_effect", ImprovedHealthEffect::new);
    public static final RegistrySupplier<MobEffect> LUCK_EFFECT = MOB_EFFECTS.register("luck_effect", LuckEffect::new);
    public static final RegistrySupplier<MobEffect> RESISTANCE_EFFECT = MOB_EFFECTS.register("resistance_effect", ResistanceEffect::new);
    public static final RegistrySupplier<MobEffect> EXPERIENCE_EFFECT = MOB_EFFECTS.register("experience_effect", () -> new ExpandableEffect(MobEffectCategory.BENEFICIAL, 0x00FF00));
    public static final RegistrySupplier<MobEffect> IMPROVED_JUMP_BOOST = MOB_EFFECTS.register("double_jump", () -> new ExpandableEffect(MobEffectCategory.BENEFICIAL, 0x00FF00));
    public static final RegistrySupplier<MobEffect> PARTY_EFFECT = MOB_EFFECTS.register("party_effect", () -> new ExpandableEffect(MobEffectCategory.BENEFICIAL, 0xFF0000));
    public static final RegistrySupplier<MobEffect> TELEPORT = MOB_EFFECTS.register("teleport", TeleportEffect::new);
    public static final RegistrySupplier<MobEffect> CREEPER_EFFECT = MOB_EFFECTS.register("creeper_effect", CreeperEffect::new);
    public static final RegistrySupplier<MobEffect> CLIMBING_EFFECT = MOB_EFFECTS.register("climbing_effect", ClimbingEffect::new);
    public static final RegistrySupplier<MobEffect> FROSTY_ARMOR_EFFECT = MOB_EFFECTS.register("frosty_armor", FrostyArmorEffect::new);
    public static final RegistrySupplier<MobEffect> JELLIE = MOB_EFFECTS.register("jellie", JellieEffect::new);
    public static final RegistrySupplier<MobEffect> LAVA_WALKER = MOB_EFFECTS.register("lava_walker", LavaWalkerEffect::new);
    public static final RegistrySupplier<MobEffect> MAGNET = MOB_EFFECTS.register("magnet", MagnetEffect::new);
    public static final RegistrySupplier<MobEffect> WATER_WALKER = MOB_EFFECTS.register("water_walker", WaterWalkerEffect::new);

    public static void init(){
        MOB_EFFECTS.register();
    }
}