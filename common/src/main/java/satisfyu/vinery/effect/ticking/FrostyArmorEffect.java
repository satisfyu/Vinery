package satisfyu.vinery.effect.ticking;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import satisfyu.vinery.effect.TickingEffect;

import java.util.UUID;

public class FrostyArmorEffect extends TickingEffect {
    private static final String ARMOR_UUID = "710D4861-7021-47DE-9F52-62F48D2B61EB";
    private static final String DAMAGE_UUID = "CE752B4A-A279-452D-853A-73C26FB4BA46";
    private static final String MOVEMENT_SPEED_MODIFIER_UUID = "CE9DBC2A-EE3F-43F5-9DF7-F7F1EE4915A9";

    public static final double FROST_MULTIPLIER = -0.05D;

    public FrostyArmorEffect() {
        super(MobEffectCategory.NEUTRAL, 0x56CBFD);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, FrostyArmorEffect.MOVEMENT_SPEED_MODIFIER_UUID, FROST_MULTIPLIER, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, DAMAGE_UUID, 4.0F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_UUID, 6.0F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        if (modifier.getId().equals(UUID.fromString(DAMAGE_UUID)))
            return (amplifier + 1) * 2.0F;
        if (modifier.getId().equals(UUID.fromString(ARMOR_UUID)))
            return (amplifier + 1) * 4.0F;
        if (modifier.getId().equals(UUID.fromString(MOVEMENT_SPEED_MODIFIER_UUID)))
            return FROST_MULTIPLIER;
        return amplifier + 1;
    }


    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        living.setIsInPowderSnow(true);
        if (amplifier > 0 && living.canFreeze()) {
            living.setTicksFrozen(Math.min(living.getTicksRequiredToFreeze(), living.getTicksFrozen() + amplifier));
        }
    }
}