package satisfyu.vinery.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;


public class JoEffect extends JellieEffect {

    @Override
    public MobEffectCategory getCategory() {
        return MobEffectCategory.BENEFICIAL;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void addAttributeModifiers(net.minecraft.world.entity.LivingEntity entity, net.minecraft.world.entity.ai.attributes.AttributeMap attributes, int amplifier) {
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.ABSORPTION, 600, amplifier));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 600, amplifier));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, amplifier));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.NIGHT_VISION, 600, amplifier));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.WATER_BREATHING, 600, amplifier));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.DOLPHINS_GRACE, 600, amplifier));

    }
}