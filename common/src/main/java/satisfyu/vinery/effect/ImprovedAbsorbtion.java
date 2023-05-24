package satisfyu.vinery.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ImprovedAbsorbtion extends MobEffect {
	public ImprovedAbsorbtion() {
		super(MobEffectCategory.BENEFICIAL, 0xC0C0C0); //silber
	}

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
		entity.addEffect(
				new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.ABSORPTION, 600,
						amplifier));
		entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
				net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 600, amplifier));
	}
}