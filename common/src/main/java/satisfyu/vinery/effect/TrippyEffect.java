package satisfyu.vinery.effect;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import satisfyu.vinery.network.VineryNetwork;

public class TrippyEffect extends MobEffect {
    public TrippyEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB80070);
    }

    @Override
    public void addAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int i) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeBoolean(true);
            NetworkManager.sendToPlayer(serverPlayer, VineryNetwork.SHADER_S2C, buf);
        }
        super.addAttributeModifiers(livingEntity, attributeMap, i);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int i) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeBoolean(false);
            NetworkManager.sendToPlayer(serverPlayer, VineryNetwork.SHADER_S2C, buf);
        }
        super.removeAttributeModifiers(livingEntity, attributeMap, i);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
