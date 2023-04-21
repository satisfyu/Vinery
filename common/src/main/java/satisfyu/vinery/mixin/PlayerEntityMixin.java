package satisfyu.vinery.mixin;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import satisfyu.vinery.registry.VineryEffects;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity{

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "getDestroySpeed", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void hasImprovedLuck(BlockState block, CallbackInfoReturnable<Float> cir, float f) {
        if (this.hasEffect(VineryEffects.IMPROVED_HASTE.get())) {
            f *= 1.0F + (float)(getImprovedLuckAmplifier() + 1) * 0.2F;
        }
        cir.setReturnValue(f);
    }

    private int getImprovedLuckAmplifier() {
        int i = 0;
        int j = 0;
        if (this.hasEffect(VineryEffects.IMPROVED_HASTE.get())) {
            i = this.getEffect(VineryEffects.IMPROVED_HASTE.get()).getAmplifier();
        }

        if (this.hasEffect(MobEffects.CONDUIT_POWER)) {
            j = this.getEffect(MobEffects.CONDUIT_POWER).getAmplifier();
        }
        return Math.max(i, j);
    }
}
