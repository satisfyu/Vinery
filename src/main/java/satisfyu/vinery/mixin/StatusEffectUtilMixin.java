package satisfyu.vinery.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import satisfyu.vinery.registry.VineryEffects;

@Mixin(StatusEffectUtil.class)
public class StatusEffectUtilMixin {
    @Inject(method = "hasHaste", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void hasImprovedLuck(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(entity.hasStatusEffect(StatusEffects.HASTE) || entity.hasStatusEffect(StatusEffects.CONDUIT_POWER) || entity.hasStatusEffect(VineryEffects.IMPROVED_LUCK));
    }

    @Inject(method = "getHasteAmplifier", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void getImprovedLuckAmplifier(LivingEntity entity, CallbackInfoReturnable<Integer> cir, int i, int j) {
        int k = 0;
        if (entity.hasStatusEffect(VineryEffects.IMPROVED_LUCK)) {
            k = entity.getStatusEffect(VineryEffects.IMPROVED_LUCK).getAmplifier();
        }
        cir.setReturnValue(Math.max(Math.max(i, j), k));
    }

    @Inject(method = "hasWaterBreathing", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void hasImprovedWaterBreathing(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(entity.hasStatusEffect(StatusEffects.WATER_BREATHING) || entity.hasStatusEffect(StatusEffects.CONDUIT_POWER) || entity.hasStatusEffect(VineryEffects.IMPROVED_WATER_BREATHING));
    }


}
