package net.satisfy.vinery.effect.normal;

import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.satisfy.vinery.effect.NormalEffect;
import net.satisfy.vinery.network.packet.ShaderS2CPacket;

public class TrippyEffect extends NormalEffect {
    public TrippyEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB80070);
    }

    public static void onRemove(LivingEntity livingEntity) {
        if(livingEntity instanceof ServerPlayer player){
            ShaderS2CPacket packet = new ShaderS2CPacket(false);
            NetworkManager.sendToPlayer(player, packet);
        }
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int i) {
        if(livingEntity instanceof ServerPlayer player){
            ShaderS2CPacket packet = new ShaderS2CPacket(true);
            NetworkManager.sendToPlayer(player, packet);
        }
        super.onEffectAdded(livingEntity, i);
    }
}