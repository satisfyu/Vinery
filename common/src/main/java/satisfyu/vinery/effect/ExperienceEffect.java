package satisfyu.vinery.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ExperienceEffect extends MobEffect {
    public ExperienceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            float experienceMultiplier = 1.0f + (0.1f * amplifier);

            int currentExperience = player.totalExperience;
            int experienceIncrease = (int) (currentExperience * experienceMultiplier);
            player.giveExperienceLevels(experienceIncrease);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
