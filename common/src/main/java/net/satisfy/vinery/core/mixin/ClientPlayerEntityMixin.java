package net.satisfy.vinery.core.mixin;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.satisfy.vinery.core.registry.MobEffectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayer {
    @Unique
    private int jumpCount = 0;
    @Unique
    private boolean jumpedLastTick = false;

    public ClientPlayerEntityMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

//    @Inject(method = "aiStep", at = @At("HEAD"))
//    private void tickMovement(CallbackInfo info) {
//        LocalPlayer player = (LocalPlayer) (Object) this;
//
//        if (!player.hasEffect(MobEffectRegistry.IMPROVED_JUMP_BOOST)) {
//            return;
//        }
//
//        if (player.onGround() || player.onClimbable()) {
//            jumpCount = 1;
//        } else if (!jumpedLastTick && jumpCount > 0 && player.getDeltaMovement().y < 0) {
//            if (player.input.jumping && !player.getAbilities().flying) {
//                if (canJump(player)) {
//                    --jumpCount;
//                    player.jumpFromGround();
//                    player.playSound(SoundEvents.PLAYER_SMALL_FALL, 1.0F, 1.0F);
//
//                    FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
//                    passedData.writeUUID(player.getUUID());
//
//                }
//            }
//        }
//
//        jumpedLastTick = player.input.jumping;
//    }

//    @Redirect(method = "updateAutoJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/core/Holder;)Z"))
//    public boolean improvedJumpBoost(LocalPlayer livingEntity, Holder<MobEffect> statusEffect) {
//        return livingEntity.hasEffect(MobEffects.JUMP) || livingEntity.hasEffect(Mo);
//    }
//
//    @Redirect(method = "updateAutoJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getEffect(Lnet/minecraft/core/Holder;)Lnet/minecraft/world/effect/MobEffectInstance;"))
//    public MobEffectInstance improvedJumpBoostAmplifier(LocalPlayer livingEntity, Holder<MobEffect> statusEffect) {
//        return livingEntity.hasEffect(MobEffectRegistry.IMPROVED_JUMP_BOOST) ?
//                livingEntity.getEffect(MobEffectRegistry.IMPROVED_JUMP_BOOST) :
//                livingEntity.getEffect(MobEffects.JUMP);
//    }

    @Unique
    private boolean wearingUsableElytra(LocalPlayer player) {
        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isFlyEnabled(chestItemStack);
    }

    @Unique
    private boolean canJump(LocalPlayer player) {
        return !wearingUsableElytra(player) && !player.isFallFlying() && !player.isPassenger()
                && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION);
    }
}