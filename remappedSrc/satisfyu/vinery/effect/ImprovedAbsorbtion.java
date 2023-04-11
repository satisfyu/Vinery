package satisfyu.vinery.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;


public class ImprovedAbsorbtion extends StatusEffect {

    public ImprovedAbsorbtion() {
        super(StatusEffectCategory.BENEFICIAL, 0xC0C0C0); //silber
    }

    @Override
    public StatusEffectCategory getCategory() {
        return StatusEffectCategory.BENEFICIAL;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onApplied(net.minecraft.entity.LivingEntity entity, net.minecraft.entity.attribute.AttributeContainer attributes, int amplifier) {
        entity.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.ABSORPTION, 600, amplifier));
        entity.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.RESISTANCE, 600, amplifier));
    }
}