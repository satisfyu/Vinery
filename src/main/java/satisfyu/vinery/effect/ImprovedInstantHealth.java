package satisfyu.vinery.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

public class ImprovedInstantHealth extends InstantStatusEffect {
    public ImprovedInstantHealth(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.isUndead()) {
            entity.damage(entity.world.getDamageSources().magic(), (float)(6 << amplifier));
        } else {
            entity.heal((float)Math.max(6 << amplifier, 0));
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (target.isUndead()) {
            target.damage(target.world.getDamageSources().magic(), (float)(6 << amplifier));
        } else {
            int i = (int)(proximity * (double)(4 << amplifier) + 0.5);
            target.heal((float) i);
        }
    }
}
