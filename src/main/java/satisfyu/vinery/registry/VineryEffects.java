package satisfyu.vinery.registry;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.effect.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class VineryEffects {


    public static final StatusEffect EMPTY;
    public static final StatusEffect JELLIE;
    public static final StatusEffect MAGNET;
    public static final StatusEffect TELEPORT;

    public static final StatusEffect IMPROVED_SPEED;
    public static final StatusEffect IMPROVED_JUMP_BOOST;
    public static final StatusEffect IMPROVED_STRENGTH;
    public static final StatusEffect IMPROVED_INSTANT_HEALTH;
    public static final StatusEffect IMPROVED_REGENERATION;
    public static final StatusEffect IMPROVED_FIRE_RESISTANCE;
    public static final StatusEffect IMPROVED_WATER_BREATHING;
    public static final StatusEffect IMPROVED_NIGHT_VISION;
    public static final StatusEffect IMPROVED_LUCK;

    private static StatusEffect registerEffect(String name, StatusEffect effect){
        return Registry.register(Registry.STATUS_EFFECT, new VineryIdentifier(name), effect);
    }

    public static void init(){
        Vinery.LOGGER.debug("Mob effects");
    }

    static {
        EMPTY = registerEffect("empty", new EmptyEffect());
        JELLIE = registerEffect("jellie", new JellieEffect());
        MAGNET = registerEffect("magnet", new MagnetEffect());
        TELEPORT = registerEffect("teleport", new TeleportEffect());

        IMPROVED_SPEED = registerEffect("improved_speed", new ImprovedEffect(StatusEffectCategory.BENEFICIAL, 0x5783B3)
                .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "8614E716-3E4B-D398-9CA2-2F9368FF8635", 0.20000000298023224, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)); //TODO FreeFall not
        IMPROVED_JUMP_BOOST = registerEffect("improved_jump_boost", new ImprovedEffect(StatusEffectCategory.BENEFICIAL, 0x90F891));
        IMPROVED_STRENGTH = registerEffect("improved_strength", new ImprovedEffect(StatusEffectCategory.BENEFICIAL, 0xB87334)
                .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "AF739E3F-3B12-4C0A-ACD6-5BA291ADB183", 0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
                .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "649DC064-6A60-4029-C0BE-CB923001D7A9", 3.0, EntityAttributeModifier.Operation.ADDITION));
        IMPROVED_INSTANT_HEALTH = registerEffect("improved_instant_health", new ImprovedInstantHealth(StatusEffectCategory.BENEFICIAL, 0xFA5124));
        IMPROVED_REGENERATION = registerEffect("improved_regeneration", new ImprovedRegeneration(StatusEffectCategory.BENEFICIAL, 0xE36C6F)
                .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "2DF48BAF-1526-71CC-B896-C6D44CEA0DA1", 4.0, EntityAttributeModifier.Operation.ADDITION));
        IMPROVED_FIRE_RESISTANCE = registerEffect("improved_fire_resistance", new ImprovedEffect(StatusEffectCategory.BENEFICIAL, 0xBF6130));
        IMPROVED_WATER_BREATHING = registerEffect("improved_water_breathing", new ImprovedEffect(StatusEffectCategory.BENEFICIAL, 0x787C75));
        IMPROVED_NIGHT_VISION = registerEffect("improved_night_vision", new ImprovedEffect(StatusEffectCategory.BENEFICIAL, 0x51539D));//TODO NIGHT_VISION
        IMPROVED_LUCK = registerEffect("improved_luck", new ImprovedEffect(StatusEffectCategory.BENEFICIAL, 0x87AE22)
                .addAttributeModifier(EntityAttributes.GENERIC_LUCK, "0C4EF750-C1D4-11ED-AFA1-02429C120002", 1.0, EntityAttributeModifier.Operation.ADDITION)
                .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "AF81723F-3D12-4C0A-AA36-5BA2BB9DBEF3", 0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
