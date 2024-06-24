package net.satisfy.vinery.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.registry.MobEffectRegistry;
import net.satisfy.vinery.util.FoodComponent;
import net.satisfy.vinery.util.WineYears;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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
	
	@Inject(method = "addEatEffect", at = @At("HEAD"), cancellable = true)
	private void applyFoodEffects(ItemStack stack, Level world, LivingEntity targetEntity, CallbackInfo ci) {
		if (stack.isEdible() && stack.getItem().getFoodProperties() instanceof FoodComponent) {
			ci.cancel();
			Item item = stack.getItem();
			if (item.isEdible()) {
				List<Pair<MobEffectInstance, Float>> list = item.getFoodProperties().getEffects();
				for (Pair<MobEffectInstance, Float> pair : list) {
					if (world.isClientSide || pair.getFirst() == null || !(world.random.nextFloat() < pair.getSecond())) continue;
					MobEffectInstance statusEffectInstance = new MobEffectInstance(pair.getFirst());
					statusEffectInstance.amplifier = WineYears.getEffectLevel(stack, world);
					if(statusEffectInstance.getEffect().equals(MobEffects.HEAL) || statusEffectInstance.getEffect().equals(MobEffects.HARM)){
						statusEffectInstance.duration = 1;
					}
					targetEntity.addEffect(statusEffectInstance);
				}
			}
		}
	}

	@Redirect(method = "calculateFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getEffect(Lnet/minecraft/world/effect/MobEffect;)Lnet/minecraft/world/effect/MobEffectInstance;"))
	public MobEffectInstance improvedJumpBoostFall(LivingEntity livingEntity, MobEffect effect) {
		return livingEntity.hasEffect(MobEffectRegistry.IMPROVED_JUMP_BOOST.get()) ? livingEntity.getEffect(MobEffectRegistry.IMPROVED_JUMP_BOOST.get()) : livingEntity.getEffect(MobEffects.JUMP);
	}

	@Inject(method = "getJumpBoostPower", at = @At(value = "HEAD"), cancellable = true)
	private void improvedJumpBoost(CallbackInfoReturnable<Float> cir) {
		if (this.hasStatusEffect(MobEffectRegistry.IMPROVED_JUMP_BOOST.get())) {
			cir.setReturnValue((0.1F * (float)(this.activeEffects.get(MobEffectRegistry.IMPROVED_JUMP_BOOST.get()).getAmplifier() + 1)));
		}
	}
}