package satisfyu.vinery.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.registry.VineryEffects;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayer {
    private int jumpCount = 0;
    private boolean jumpedLastTick = false;

    public ClientPlayerEntityMixin(ClientLevel world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        if(this.hasEffect(VineryEffects.IMPROVED_JUMP_BOOST)) {
            LocalPlayer player = (LocalPlayer) (Object) this;
            if (player.isOnGround() || player.onClimbable()) {
                jumpCount = 1;
            } else if (!jumpedLastTick && jumpCount > 0 && player.getDeltaMovement().y < 0) {
                if (player.input.jumping && !player.getAbilities().flying) {
                    if (canJump(player)) {
                        --jumpCount;
                        player.jumpFromGround();
                    }
                }
            }
            jumpedLastTick = player.input.jumping;
        }
    }

    @Redirect(method = "autoJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    public boolean improvedJumpBoost(LocalPlayer livingEntity, MobEffect statusEffect) {
        return livingEntity.hasEffect(MobEffects.JUMP) || livingEntity.hasEffect(VineryEffects.IMPROVED_JUMP_BOOST);
    }

    @Redirect(method = "autoJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Lnet/minecraft/entity/effect/StatusEffectInstance;"))
    public MobEffectInstance improvedJumpBoostAmplifier(LocalPlayer livingEntity, MobEffect statusEffect) {
        return livingEntity.hasEffect(VineryEffects.IMPROVED_JUMP_BOOST) ?  livingEntity.getEffect(VineryEffects.IMPROVED_JUMP_BOOST) : livingEntity.getEffect(MobEffects.JUMP);
    }

    private boolean wearingUsableElytra(LocalPlayer player) {
        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isFlyEnabled(chestItemStack);
    }

    private boolean canJump(LocalPlayer player) {
        return !wearingUsableElytra(player) && !player.isFallFlying() && !player.isPassenger()
                && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION);
    }
}
