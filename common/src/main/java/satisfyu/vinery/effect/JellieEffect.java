package satisfyu.vinery.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class JellieEffect extends MobEffect {
	public JellieEffect() {
		super(MobEffectCategory.BENEFICIAL, 0x98D982);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		int i = 50 >> amplifier;
		if (i > 0) {
			return duration % i == 0;
		}
		else {
			return true;
		}
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.getHealth() < entity.getMaxHealth()) {
			entity.heal(1.0f);
		}
	}

	@Override
	public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
		entity.setAbsorptionAmount(entity.getAbsorptionAmount() - (float) (4 * (amplifier + 1)));
		super.removeAttributeModifiers(entity, attributes, amplifier);
	}

	@Override
	public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
		entity.setAbsorptionAmount(entity.getAbsorptionAmount() + (float) (4 * (amplifier + 1)));
		super.addAttributeModifiers(entity, attributes, amplifier);
	}
}
