package satisfyu.vinery.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class ImprovedInstantHealth extends InstantenousMobEffect {
    public ImprovedInstantHealth(MobEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isInvertedHealAndHarm()) {
            entity.hurt(entity.level.damageSources().magic(), (float)(6 << amplifier));
        } else {
            entity.heal((float)Math.max(6 << amplifier, 0));
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (target.isInvertedHealAndHarm()) {
            target.hurt(target.level.damageSources().magic(), (float)(6 << amplifier));
        } else {
            int i = (int)(proximity * (double)(4 << amplifier) + 0.5);
            target.heal((float) i);
        }
    }
}
