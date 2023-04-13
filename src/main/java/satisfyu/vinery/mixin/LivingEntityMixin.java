package satisfyu.vinery.mixin;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import satisfyu.vinery.registry.VineryEffects;
import satisfyu.vinery.util.VineryFoodComponent;
import satisfyu.vinery.util.WineYears;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Final
	@Shadow
	private final Map<StatusEffect, StatusEffectInstance> activeStatusEffects = Maps.newHashMap();

	protected LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	public boolean hasStatusEffect(StatusEffect effect) {
		return activeStatusEffects.containsKey(effect);
	}
	
	@Inject(method = "applyFoodEffects", at = @At("HEAD"), cancellable = true)
	private void applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci) {
		if (stack.isFood() && stack.getItem().getFoodComponent() instanceof VineryFoodComponent) {
			ci.cancel();
			Item item = stack.getItem();
			if (item.isFood()) {
				List<Pair<StatusEffectInstance, Float>> list = item.getFoodComponent().getStatusEffects();
				for (Pair<StatusEffectInstance, Float> pair : list) {
					if (world.isClient || pair.getFirst() == null || !(world.random.nextFloat() < pair.getSecond())) continue;
					StatusEffectInstance statusEffectInstance = new StatusEffectInstance(pair.getFirst());
					statusEffectInstance.amplifier = WineYears.getEffectLevel(stack, world);
					if(statusEffectInstance.getEffectType().equals(StatusEffects.INSTANT_HEALTH) || statusEffectInstance.getEffectType().equals(StatusEffects.INSTANT_DAMAGE)){
						statusEffectInstance.duration = 1;
					}
					targetEntity.addStatusEffect(statusEffectInstance);
				}
			}
		}
	}

	@Inject(method = "canWalkOnFluid", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void canWalkOnWater(FluidState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.getFluid() == Fluids.WATER && !this.isSubmergedInWater() && !this.isSwimming() && !this.isInSwimmingPose()) {
			cir.setReturnValue(this.hasStatusEffect(VineryEffects.IMPROVED_SPEED));
		}
	}

	@Inject(method = "onAttacking", at = @At(value = "HEAD"))
	private void setTargetOnFire(Entity target, CallbackInfo ci) {
		if (target instanceof LivingEntity targetEntity && hasStatusEffect(VineryEffects.IMPROVED_FIRE_RESISTANCE)) {
			targetEntity.setOnFireFor(5);
		}
	}

	@Redirect(method = "computeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Lnet/minecraft/entity/effect/StatusEffectInstance;"))
	public StatusEffectInstance improvedJumpBoostFall(LivingEntity livingEntity, StatusEffect effect) {
		return livingEntity.hasStatusEffect(VineryEffects.IMPROVED_JUMP_BOOST) ? livingEntity.getStatusEffect(VineryEffects.IMPROVED_JUMP_BOOST) : livingEntity.getStatusEffect(StatusEffects.JUMP_BOOST);
	}

	@Inject(method = "getJumpBoostVelocityModifier", at = @At(value = "HEAD"), cancellable = true)
	private void improvedJumpBoost(CallbackInfoReturnable<Double> cir) {
		if (this.hasStatusEffect(VineryEffects.IMPROVED_JUMP_BOOST)) {
			cir.setReturnValue((double)(0.1F * (float)(this.activeStatusEffects.get(VineryEffects.IMPROVED_JUMP_BOOST).getAmplifier() + 1)));
		}
	}

	@Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
	private void hasImprovedFireResistance(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (source.isIn(DamageTypeTags.IS_FIRE) && hasStatusEffect(VineryEffects.IMPROVED_FIRE_RESISTANCE)) {
			cir.setReturnValue(false);
		}
	}

	@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", ordinal = 1))
	public boolean improvedWaterBreathingSpeed(LivingEntity livingEntity, StatusEffect effect) {
		return livingEntity.hasStatusEffect(StatusEffects.DOLPHINS_GRACE) || livingEntity.hasStatusEffect(VineryEffects.IMPROVED_WATER_BREATHING);
	}

	/*
	@ModifyConstant(method = "travel", constant = @Constant(floatValue = 0.96F))
	public float improvedWaterBreathingSpeed(float constant) {
		if (this.hasStatusEffect(VineryEffects.IMPROVED_WATER_BREATHING)) {
			int amplifier = this.getStatusEffect(VineryEffects.IMPROVED_WATER_BREATHING).getAmplifier();
			return 0.9F + (amplifier + 1f) / 10;
		}
		return 0.95F;
	}

	public StatusEffectInstance getStatusEffect(StatusEffect effect) {
		return activeStatusEffects.get(effect);
	}
	 */

	@Redirect(method = "updatePotionVisibility", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
	public boolean improvedNightVision(LivingEntity livingEntity, StatusEffect effect) {
		return livingEntity.hasStatusEffect(StatusEffects.INVISIBILITY) || livingEntity.hasStatusEffect(VineryEffects.IMPROVED_NIGHT_VISION);
	}



}