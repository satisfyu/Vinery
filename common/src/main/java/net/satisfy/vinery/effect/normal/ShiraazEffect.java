package net.satisfy.vinery.effect.normal;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ShiraazEffect extends MobEffect {

    public ShiraazEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            entity.setAbsorptionAmount(entity.getAbsorptionAmount() + 4.0F);  // Absorption provides 2 hearts
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return i % 20 == 0;
    }
}
