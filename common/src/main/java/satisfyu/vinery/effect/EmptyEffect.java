package satisfyu.vinery.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EmptyEffect extends MobEffect {
	public EmptyEffect() {
		super(MobEffectCategory.NEUTRAL, 0x98D982);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		// In our case, we just make it return true so that it applies the status effect every tick.
		return true;
	}

	// This method is called when it applies the status effect. We implement custom functionality here.
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
	}
}
