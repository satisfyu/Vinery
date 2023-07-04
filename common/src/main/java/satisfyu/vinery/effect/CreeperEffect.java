package satisfyu.vinery.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class CreeperEffect extends MobEffect {
    public CreeperEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0000);
    }




    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            Level world = player.getCommandSenderWorld();
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            float power = 4.0f;

            if (player instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
                    entity.level().explode(entity, x, y, z, power, Level.ExplosionInteraction.BLOCK);
                    for (int i = 0; i < 4; i++) {
                        world.addParticle(ParticleTypes.EXPLOSION, x, y, z, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
