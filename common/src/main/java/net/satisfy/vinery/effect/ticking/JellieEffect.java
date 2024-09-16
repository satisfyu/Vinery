package net.satisfy.vinery.effect.ticking;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.satisfy.vinery.effect.TickingEffect;

public class JellieEffect extends TickingEffect {

    public JellieEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x98D982);
    }

    public static void onRemove(LivingEntity livingEntity) {
        livingEntity.setAbsorptionAmount(livingEntity.getAbsorptionAmount() - 4.0f);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 50 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0f);
        }
        return true;
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int i) {
        livingEntity.setAbsorptionAmount(livingEntity.getAbsorptionAmount() + (float)(4 * (i + 1)));
        super.onEffectAdded(livingEntity, i);
    }
}
