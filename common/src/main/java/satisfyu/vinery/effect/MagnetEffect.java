package satisfyu.vinery.effect;

import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class MagnetEffect extends MobEffect {
    public MagnetEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB80070);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            List<Entity> entities = player.getCommandSenderWorld().getEntities(player, player.getBoundingBox().inflate(5 + amplifier));
            for (Entity entityNearby : entities) {
                if (entityNearby instanceof ItemEntity && !player.isShiftKeyDown()) {
                    entityNearby.playerTouch(player);
                }
            }
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
