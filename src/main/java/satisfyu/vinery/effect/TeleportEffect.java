package satisfyu.vinery.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class TeleportEffect extends StatusEffect {
    public TeleportEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x800080);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if(player instanceof ServerPlayerEntity) {
                Vec3d lookVec = player.getRotationVector();
                Vec3d teleportPos = player.getPos().add(lookVec.x * 30, lookVec.y * 30, lookVec.z * 30);
                player.teleport(teleportPos.x, teleportPos.y, teleportPos.z);
            }
        }
    }
}