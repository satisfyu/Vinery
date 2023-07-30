package satisfyu.vinery.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class CreeperEffect extends InstantenousMobEffect {
    public CreeperEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0000);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
                Level world = serverPlayer.getCommandSenderWorld();
                double x = serverPlayer.getX();
                double y = serverPlayer.getY();
                double z = serverPlayer.getZ();
                float power = (amplifier + 1) * 4;
                world.explode(null, x, y, z, power, Level.ExplosionInteraction.TNT);
            }
        }
    }
}
