package net.satisfy.vinery.fabric.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.satisfy.vinery.core.registry.MobEffectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Objects;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {
    @ModifyArgs(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;repairPlayerItems(Lnet/minecraft/server/level/ServerPlayer;I)I"))
    public void modifyExperienceRepair(Args args) {
        System.out.println("Args length: " + args.size());
        for(int i = 0; i < args.size(); i++) {
            System.out.println("Arg " + i + ": " + args.get(i).getClass().getSimpleName());
        }

        Player player = null;
        int xpIndex = -1;

        for(int i = 0; i < args.size(); i++) {
            if(args.get(i) instanceof Player) {
                player = (Player) args.get(i);
            } else if(args.get(i) instanceof Integer) {
                xpIndex = i;
            }
        }

        if(player != null && xpIndex != -1 && player.hasEffect(MobEffectRegistry.getHolder(MobEffectRegistry.EXPERIENCE_EFFECT))) {
            MobEffectInstance effect = player.getEffect(MobEffectRegistry.getHolder(MobEffectRegistry.EXPERIENCE_EFFECT));
            if(effect != null) {
                int amplifier = effect.getAmplifier();
                int originalXp = (Integer) args.get(xpIndex);
                int modifiedXp = (int) (originalXp + (originalXp * (1 + amplifier) * 0.5));
                args.set(xpIndex, modifiedXp);
            }
        }
    }
}
