package net.satisfy.vinery.effect.ticking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.effect.TickingEffect;
import net.satisfy.vinery.util.VineryIdentifier;

public class FrostyArmorEffect extends TickingEffect {
    public static final ResourceLocation ARMOR_ID = VineryIdentifier.of("frosty_armor");
    public static final ResourceLocation DAMAGE_ID = VineryIdentifier.of("frosty_damage");
    public static final ResourceLocation MOVEMENT_SPEED_MODIFIER_ID = VineryIdentifier.of("frosty_movement_speed");


    public static final double FROST_MULTIPLIER = -0.05D;

    public FrostyArmorEffect() {
        super(MobEffectCategory.NEUTRAL, 0x56CBFD);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, FrostyArmorEffect.MOVEMENT_SPEED_MODIFIER_ID, FROST_MULTIPLIER, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, DAMAGE_ID, 4.0F, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_ID, 6.0F, AttributeModifier.Operation.ADD_VALUE);
    }


    @Override
    public boolean applyEffectTick(LivingEntity living, int amplifier) {
        living.setIsInPowderSnow(true);
        if (amplifier > 0 && living.canFreeze()) {
            living.setTicksFrozen(Math.min(living.getTicksRequiredToFreeze(), living.getTicksFrozen() + amplifier));
        }
        return true;
    }
}