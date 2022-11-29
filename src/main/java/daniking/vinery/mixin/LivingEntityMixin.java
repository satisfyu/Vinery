package daniking.vinery.mixin;

import com.mojang.datafixers.util.Pair;
import daniking.vinery.util.VineryFoodComponent;
import daniking.vinery.util.WineYears;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	protected LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@Inject(method = "applyFoodEffects", at = @At("HEAD"), cancellable = true)
	private void applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci) {
		if (stack.isFood() && stack.getItem().getFoodComponent() instanceof VineryFoodComponent) {
			ci.cancel();
			Item item = stack.getItem();
			if (item.isFood()) {
				List<Pair<StatusEffectInstance, Float>> list = item.getFoodComponent().getStatusEffects();
				for (Pair<StatusEffectInstance, Float> pair : list) {
					if (world.isClient || pair.getFirst() == null || !(world.random.nextFloat() < pair.getSecond().floatValue())) continue;
					StatusEffectInstance statusEffectInstance = new StatusEffectInstance(pair.getFirst());
					statusEffectInstance.amplifier = WineYears.getEffectLevel(stack, world);
					targetEntity.addStatusEffect(statusEffectInstance);
				}
			}
		}
	}
	
}