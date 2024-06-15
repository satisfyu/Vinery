package satisfyu.vinery.effect.instant;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.config.VineryConfig;

public class CreeperEffect extends InstantenousMobEffect {
    public CreeperEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0000);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        explode(source, amplifier);
    }

    @Override
    public void applyEffectTick(LivingEntity source, int amplifier) {
        explode(source, amplifier);
    }

    private void explode(Entity source, int amplifier){
        if (source instanceof ServerPlayer serverPlayer && serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
            Level world = serverPlayer.getCommandSenderWorld();
            double x = serverPlayer.getX();
            double y = serverPlayer.getY();
            double z = serverPlayer.getZ();
            float power = (amplifier + 1) * 4;
            boolean destroyBlocks = VineryConfig.getConfigInstance().destroyBlocks();

            if (destroyBlocks) {
                world.explode(null, x, y, z, power, Level.ExplosionInteraction.TNT);
            } else {
                serverPlayer.hurt(serverPlayer.damageSources().explosion(null), Float.MAX_VALUE);
            }
        }
    }
}
