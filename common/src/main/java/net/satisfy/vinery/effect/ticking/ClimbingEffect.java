package net.satisfy.vinery.effect.ticking;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.satisfy.vinery.effect.TickingEffect;

public class ClimbingEffect extends TickingEffect {
    public ClimbingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xCC3300);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity.horizontalCollision) {
            entity.fallDistance = 0.0F;

            final float velocity = 0.15F;

            Vec3 motion = entity.getDeltaMovement();

            double motionX = Mth.clamp(motion.x, -velocity, velocity);
            double motionY = 0.2;
            double motionZ = Mth.clamp(motion.z, -velocity, velocity);
            if(entity.isSuppressingSlidingDownLadder()) {
                motionY = 0.0;
            }

            entity.setDeltaMovement(motionX, motionY, motionZ);
        }
    }
}