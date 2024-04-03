package satisfyu.vinery.effect.instant;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TeleportEffect extends InstantenousMobEffect {

    public TeleportEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF69B4);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        teleport(source);
    }

    @Override
    public void applyEffectTick(LivingEntity source, int i) {
        teleport(source);
    }

    private void teleport(Entity source) {
        if (!(source instanceof Player player)) return;

        Level world = player.level();
        Vec3 targetVec = player.position();
        Vec3 lookVec = player.getLookAngle();
        BlockPos target = null;
        for (double i = 12; i >= 2; i -= 0.5) {
            Vec3 v3d = targetVec.add(lookVec.multiply(i, i, i));
            target = new BlockPos((int) Math.round(v3d.x), (int) Math.round(v3d.y), (int) Math.round(v3d.z));
            if (!fullBlockAt(world, target) && !fullBlockAt(world, target.above())) {
                break;
            } else {
                target = null;
            }
        }
        if (target != null) {
            if (!player.level().isClientSide) {
                Vec3 teleportVec = new Vec3(target.getX(), target.getY(), target.getZ());
                player.teleportToWithTicket(teleportVec.x + 0.5, teleportVec.y, teleportVec.z + 0.5);
            }
            player.fallDistance = 0;
            player.playSound(SoundEvents.ENDER_EYE_DEATH, 1F, 1F);
        }
    }

    private static boolean fullBlockAt(Level world, BlockPos target){
        return Block.isShapeFullBlock(world.getBlockState(target).getCollisionShape(world, target));
    }

    private void oldTeleport(Player player) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 teleportPos = player.position().add(lookVec.x * 30, lookVec.y * 30, lookVec.z * 30);
        player.teleportToWithTicket(teleportPos.x, teleportPos.y, teleportPos.z);
    }
}
