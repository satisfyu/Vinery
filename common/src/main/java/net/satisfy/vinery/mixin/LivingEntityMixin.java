package net.satisfy.vinery.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.effect.normal.TrippyEffect;
import net.satisfy.vinery.effect.ticking.JellieEffect;
import net.satisfy.vinery.registry.MobEffectRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow @Final private Map<MobEffect, MobEffectInstance> activeEffects;

	protected LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Unique
	private boolean hasStatusEffect(MobEffect effect) {
		return activeEffects.containsKey(effect);
	}
	
	@Inject(method = "addEatEffect", at = @At("HEAD"))
	private void applyFoodEffects(FoodProperties foodProperties, CallbackInfo ci) {
		List<FoodProperties.PossibleEffect> list = foodProperties.effects();

		for (FoodProperties.PossibleEffect effect : list) {
			if (this.level().isClientSide || !(level().random.nextFloat() < effect.probability())) continue;
			MobEffectInstance statusEffectInstance = effect.effect();
			if(statusEffectInstance.getEffect().equals(MobEffects.HEAL) || statusEffectInstance.getEffect().equals(MobEffects.HARM)){
				statusEffectInstance.duration = 1;
			}
		}
	}

	@Inject(method = "removeEffect", at = @At("HEAD"))
	public void brewery$removeEffect(Holder<MobEffect> holder, CallbackInfoReturnable<Boolean> cir) {
		if (holder == MobEffectRegistry.TRIPPY) {
			TrippyEffect.onRemove((LivingEntity) (Object) this);
		} else if(holder == MobEffectRegistry.JELLIE){
			JellieEffect.onRemove((LivingEntity) (Object) this);
		}
	}

	@Inject(method = "getJumpBoostPower", at = @At(value = "HEAD"), cancellable = true)
	private void improvedJumpBoost(CallbackInfoReturnable<Float> cir) {
		if (this.hasStatusEffect(MobEffectRegistry.IMPROVED_JUMP_BOOST.get())) {
			cir.setReturnValue((0.1F * (float)(this.activeEffects.get(MobEffectRegistry.IMPROVED_JUMP_BOOST.get()).getAmplifier() + 1)));
		}
	}
}