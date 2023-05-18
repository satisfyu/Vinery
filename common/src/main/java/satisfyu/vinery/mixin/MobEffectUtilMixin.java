package satisfyu.vinery.mixin;

import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfyu.vinery.registry.VineryEffects;

@Mixin(MobEffectUtil.class)
public class MobEffectUtilMixin {
    @Inject(method = "getDigSpeedAmplification", at = @At(value = "TAIL"), cancellable = true)
    private static void hasImprovedLuck(LivingEntity livingEntity, CallbackInfoReturnable<Integer> cir) {
        if (livingEntity.hasEffect(VineryEffects.IMPROVED_HASTE.get())) {
            int haste = 0;
            if (livingEntity.hasEffect(MobEffects.DIG_SPEED)) {
                haste = livingEntity.getEffect(MobEffects.DIG_SPEED).getAmplifier();
            }

            int conduit = 0;
            if (livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
                conduit = livingEntity.getEffect(MobEffects.CONDUIT_POWER).getAmplifier();
            }

            int impovedHaste = livingEntity.getEffect(VineryEffects.IMPROVED_HASTE.get()).getAmplifier();

            cir.setReturnValue(Math.max(Math.max(haste, conduit), impovedHaste));
        }
    }
}
