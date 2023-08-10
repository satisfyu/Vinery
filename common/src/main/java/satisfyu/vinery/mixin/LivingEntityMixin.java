package satisfyu.vinery.mixin;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfyu.vinery.registry.VineryEffects;
import satisfyu.vinery.util.VineryFoodComponent;
import satisfyu.vinery.util.WineYears;

import java.util.List;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Final
	@Shadow
	private final Map<MobEffect, MobEffectInstance> activeEffects = Maps.newHashMap();

	protected LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	public boolean hasStatusEffect(MobEffect effect) {
		return activeEffects.containsKey(effect);
	}
	
	@Inject(method = "addEatEffect", at = @At("HEAD"), cancellable = true)
	private void applyFoodEffects(ItemStack stack, Level world, LivingEntity targetEntity, CallbackInfo ci) {
		if (stack.isEdible() && stack.getItem().getFoodProperties() instanceof VineryFoodComponent) {
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

	@Inject(method = "canStandOnFluid", at = @At(value = "TAIL"), cancellable = true)
	private void canWalkOnWater(FluidState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.getType() == Fluids.WATER && !this.isUnderWater() && !this.isSwimming() && !this.isVisuallySwimming()) {
			cir.setReturnValue(this.hasStatusEffect(VineryEffects.IMPROVED_SPEED.get()));
		}
	}

	@Inject(method = "setLastHurtMob", at = @At(value = "HEAD"))
	private void setTargetOnFire(Entity target, CallbackInfo ci) {
		if (target instanceof LivingEntity targetEntity && hasStatusEffect(VineryEffects.IMPROVED_FIRE_RESISTANCE.get())) {
			targetEntity.setSecondsOnFire(5);
		}
	}

	@Redirect(method = "calculateFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getEffect(Lnet/minecraft/world/effect/MobEffect;)Lnet/minecraft/world/effect/MobEffectInstance;"))
	public MobEffectInstance improvedJumpBoostFall(LivingEntity livingEntity, MobEffect effect) {
		return livingEntity.hasEffect(VineryEffects.IMPROVED_JUMP_BOOST.get()) ? livingEntity.getEffect(VineryEffects.IMPROVED_JUMP_BOOST.get()) : livingEntity.getEffect(MobEffects.JUMP);
	}

	@Inject(method = "getJumpBoostPower", at = @At(value = "HEAD"), cancellable = true)
	private void improvedJumpBoost(CallbackInfoReturnable<Float> cir) {
		if (this.hasStatusEffect(VineryEffects.IMPROVED_JUMP_BOOST.get())) {
			cir.setReturnValue((0.1F * (float)(this.activeEffects.get(VineryEffects.IMPROVED_JUMP_BOOST.get()).getAmplifier() + 1)));
		}
	}

	@Inject(method = "hurt", at = @At(value = "HEAD"), cancellable = true)
	private void hasImprovedFireResistance(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (source.isFire() && hasStatusEffect(VineryEffects.IMPROVED_FIRE_RESISTANCE.get())) {
			cir.setReturnValue(false);
		}
	}


	@ModifyVariable(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z", ordinal = 1), ordinal = 0)
	public float applyWaterCreatureSwimSpeedBoost(float j) {
		if ((Object) this instanceof LivingEntity entity) {

			// Apply 'Dolphin's Grace' status effect benefits if the player's Identity is a water creature
			if (entity.hasEffect(VineryEffects.IMPROVED_WATER_BREATHING.get())) {
				return .96f;
			}
		}

		return j;
	}

	@Redirect(method = "updateInvisibilityStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"))
	public boolean improvedNightVision(LivingEntity livingEntity, MobEffect effect) {
		return livingEntity.hasEffect(MobEffects.INVISIBILITY) || livingEntity.hasEffect(VineryEffects.IMPROVED_NIGHT_VISION.get());
	}



}