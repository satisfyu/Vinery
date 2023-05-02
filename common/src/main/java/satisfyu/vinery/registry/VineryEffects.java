package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.effect.*;

import java.util.function.Supplier;

public class VineryEffects {

    private static final Registrar<MobEffect> MOB_EFFECTS = DeferredRegister.create(Vinery.MODID, Registry.MOB_EFFECT_REGISTRY).getRegistrar();


    public static final RegistrySupplier<MobEffect> EMPTY;
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


    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect){
        return MOB_EFFECTS.register(new VineryIdentifier(name), effect);
    }

    public static void init(){
        Vinery.LOGGER.debug("Mob effects");
    }

    static {
        EMPTY = registerEffect("empty", EmptyEffect::new);
        JELLIE = registerEffect("jellie", JellieEffect::new);
        MAGNET = registerEffect("magnet", MagnetEffect::new);
        TELEPORT = registerEffect("teleport", TeleportEffect::new);
        IMPROVED_ABSORBTION = registerEffect("improved_absorbtion", ImprovedAbsorbtion::new);

        IMPROVED_SPEED = registerEffect("improved_speed", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0x5783B3)
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, "8614E716-3E4B-D398-9CA2-2F9368FF8635", 0.20000000298023224, AttributeModifier.Operation.MULTIPLY_TOTAL));
        IMPROVED_JUMP_BOOST = registerEffect("improved_jump_boost", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0x90F891));
        IMPROVED_STRENGTH = registerEffect("improved_strength", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0xB87334)
                .addAttributeModifier(Attributes.ATTACK_SPEED, "AF739E3F-3B12-4C0A-ACD6-5BA291ADB183", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
                .addAttributeModifier(Attributes.ATTACK_DAMAGE, "649DC064-6A60-4029-C0BE-CB923001D7A9", 3.0, AttributeModifier.Operation.ADDITION));
        IMPROVED_INSTANT_HEALTH = registerEffect("improved_instant_health", () -> new ImprovedInstantHealth(MobEffectCategory.BENEFICIAL, 0xFA5124));
        IMPROVED_REGENERATION = registerEffect("improved_regeneration", () -> new ImprovedRegeneration(MobEffectCategory.BENEFICIAL, 0xE36C6F)
                .addAttributeModifier(Attributes.MAX_HEALTH, "2DF48BAF-1526-71CC-B896-C6D44CEA0DA1", 4.0, AttributeModifier.Operation.ADDITION));
        IMPROVED_FIRE_RESISTANCE = registerEffect("improved_fire_resistance", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0xBF6130));
        IMPROVED_WATER_BREATHING = registerEffect("improved_water_breathing", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0x787C75));
        IMPROVED_NIGHT_VISION = registerEffect("improved_night_vision", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0x51539D));
        IMPROVED_HASTE = registerEffect("improved_haste", () -> new ImprovedEffect(MobEffectCategory.BENEFICIAL, 0x87AE22)
                .addAttributeModifier(Attributes.LUCK, "0C4EF750-C1D4-11ED-AFA1-02429C120002", 1.0, AttributeModifier.Operation.ADDITION)
                .addAttributeModifier(Attributes.ATTACK_SPEED, "AF81723F-3D12-4C0A-AA36-5BA2BB9DBEF3", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
