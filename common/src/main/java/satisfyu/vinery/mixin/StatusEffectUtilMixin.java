package satisfyu.vinery.mixin;

import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import satisfyu.vinery.registry.VineryEffects;

@Mixin(MobEffectUtil.class)
public abstract class StatusEffectUtilMixin {

    @Inject(method = "hasDigSpeed", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void hasImprovedLuck(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(entity.hasEffect(MobEffects.DIG_SPEED) || entity.hasEffect(MobEffects.CONDUIT_POWER) || entity.hasEffect(VineryEffects.IMPROVED_HASTE.get()));
    }

    @Inject(method = "getDigSpeedAmplification", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void getImprovedLuckAmplifier(LivingEntity entity, CallbackInfoReturnable<Integer> cir, int i, int j) {
        int k = 0;
        if (entity.hasEffect(VineryEffects.IMPROVED_HASTE.get())) {
            k = entity.getEffect(VineryEffects.IMPROVED_HASTE.get()).getAmplifier();
        }
        cir.setReturnValue(Math.max(Math.max(i, j), k));
    }

    @Inject(method = "hasWaterBreathing", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void hasImprovedWaterBreathing(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(entity.hasEffect(MobEffects.WATER_BREATHING) || entity.hasEffect(MobEffects.CONDUIT_POWER) || entity.hasEffect(VineryEffects.IMPROVED_WATER_BREATHING.get()));
    }


}
