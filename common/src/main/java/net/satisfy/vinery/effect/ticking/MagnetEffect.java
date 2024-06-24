package net.satisfy.vinery.effect.ticking;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.satisfy.vinery.effect.TickingEffect;

import java.util.List;

public class MagnetEffect extends TickingEffect {
    public MagnetEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB80070);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player && !player.isShiftKeyDown()) {
            List<Entity> entities = player.getCommandSenderWorld().getEntities(player, player.getBoundingBox().inflate(5 + amplifier), p -> p instanceof ItemEntity);
            for (Entity entityNearby : entities) {

                if(player.getInventory().getFreeSlot() == -1){
                    Vec3 vec3 = entity.getEyePosition().subtract(entityNearby.position());

                    int amp = amplifier + 1;

                    entityNearby.setPosRaw(entityNearby.getX(), entityNearby.getY() + vec3.y * 0.015 * Math.min(amp, 3), entityNearby.getZ());
                    if (entity.level().isClientSide) {
                        entityNearby.yOld = entityNearby.getY();
                    }
                    entityNearby.setDeltaMovement(entityNearby.getDeltaMovement().scale(0.95).add(vec3.normalize().yRot(0.2f).scale(0.10 * (double)amp)));
                }
                else {
                    entityNearby.playerTouch(player);
                }

            }
        }
        super.applyEffectTick(entity, amplifier);
    }
}