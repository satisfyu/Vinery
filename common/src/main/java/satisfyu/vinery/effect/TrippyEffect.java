package satisfyu.vinery.effect;


import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import satisfyu.vinery.network.VineryNetwork;
import satisfyu.vinery.util.GeneralUtil;

public class TrippyEffect extends MobEffect {
    public TrippyEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB80070);
    }


    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int i) {
        if(livingEntity instanceof ServerPlayer player){
            FriendlyByteBuf buf = GeneralUtil.create();
            buf.writeBoolean(false);
            NetworkManager.sendToPlayer(player, VineryNetwork.SHADER_S2C, buf);
        }
    }

    @Override
    public void addAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int i) {
        if(livingEntity instanceof ServerPlayer player){
            FriendlyByteBuf buf = GeneralUtil.create();
            buf.writeBoolean(true);
            NetworkManager.sendToPlayer(player, VineryNetwork.SHADER_S2C, buf);
        }
        super.addAttributeModifiers(livingEntity, attributeMap, i);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}


