package satisfyu.vinery.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.encryption.PlayerPublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.registry.VineryEffects;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    private int jumpCount = 0;
    private boolean jumpedLastTick = false;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        if(this.hasStatusEffect(VineryEffects.IMPROVED_JUMP_BOOST)) {
            ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
            if (player.isOnGround() || player.isClimbing()) {
                jumpCount = 1;
            } else if (!jumpedLastTick && jumpCount > 0 && player.getVelocity().y < 0) {
                if (player.input.jumping && !player.getAbilities().flying) {
                    if (canJump(player)) {
                        --jumpCount;
                        player.jump();
                    }
                }
            }
            jumpedLastTick = player.input.jumping;
        }
    }

    @Redirect(method = "autoJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    public boolean improvedJumpBoost(ClientPlayerEntity livingEntity, StatusEffect statusEffect) {
        return livingEntity.hasStatusEffect(StatusEffects.JUMP_BOOST) || livingEntity.hasStatusEffect(VineryEffects.IMPROVED_JUMP_BOOST);
    }

    @Redirect(method = "autoJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Lnet/minecraft/entity/effect/StatusEffectInstance;"))
    public StatusEffectInstance improvedJumpBoostAmplifier(ClientPlayerEntity livingEntity, StatusEffect statusEffect) {
        return livingEntity.hasStatusEffect(VineryEffects.IMPROVED_JUMP_BOOST) ?  livingEntity.getStatusEffect(VineryEffects.IMPROVED_JUMP_BOOST) : livingEntity.getStatusEffect(StatusEffects.JUMP_BOOST);
    }

    private boolean wearingUsableElytra(ClientPlayerEntity player) {
        ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);
    }

    private boolean canJump(ClientPlayerEntity player) {
        return !wearingUsableElytra(player) && !player.isFallFlying() && !player.hasVehicle()
                && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION);
    }
}
