package satisfyu.vinery.effect;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ClimbingEffect extends MobEffect {
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

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}