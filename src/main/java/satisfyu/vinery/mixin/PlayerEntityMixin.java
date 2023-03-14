package satisfyu.vinery.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import satisfyu.vinery.registry.VineryEffects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity{

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void hasImprovedLuck(BlockState block, CallbackInfoReturnable<Float> cir, float f) {
        if (this.hasStatusEffect(VineryEffects.IMPROVED_LUCK)) {
            f *= 1.0F + (float)(getImprovedLuckAmplifier() + 1) * 0.2F;
        }
        cir.setReturnValue(f);
    }

    private int getImprovedLuckAmplifier() {
        int i = 0;
        int j = 0;
        if (this.hasStatusEffect(VineryEffects.IMPROVED_LUCK)) {
            i = this.getStatusEffect(VineryEffects.IMPROVED_LUCK).getAmplifier();
        }

        if (this.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
            j = this.getStatusEffect(StatusEffects.CONDUIT_POWER).getAmplifier();
        }
        return Math.max(i, j);
    }
}
