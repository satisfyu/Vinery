package satisfyu.vinery.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class MagnetEffect extends StatusEffect {
    public MagnetEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xB80070);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            List<Entity> entities = player.getEntityWorld().getOtherEntities(player, player.getBoundingBox().expand(5 + amplifier));
            for (Entity entityNearby : entities) {
                if (entityNearby instanceof ItemEntity && !player.isSneaking()) {
                    entityNearby.onPlayerCollision(player);
                }
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
