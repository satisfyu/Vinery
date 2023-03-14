package satisfyu.vinery.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.registry.VineryEffects;

public class ImprovedInstantEffect extends InstantStatusEffect {
    public ImprovedInstantEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == VineryEffects.IMPROVED_INSTANT_HEALTH) {
            if (entity.isUndead()) {
                entity.damage(DamageSource.MAGIC, (float)(6 << amplifier));
            } else {
                entity.heal((float)Math.max(6 << amplifier, 0));
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        int i;
        if (this == VineryEffects.IMPROVED_INSTANT_HEALTH) {
            if (target.isUndead()) {
                target.damage(DamageSource.MAGIC, (float)(6 << amplifier));
            } else {
                i = (int)(proximity * (double)(4 << amplifier) + 0.5);
                target.heal((float) i);
            }
        }
        super.applyInstantEffect(source, attacker, target, amplifier, proximity);
    }
}
