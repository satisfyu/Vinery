package satisfyu.vinery.effect;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TeleportEffect extends InstantStatusEffect {

    public TeleportEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFF69B4);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            teleport(player, amplifier);
        }
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (source instanceof PlayerEntity player) {
            teleport(player, amplifier);
        }
    }

    private void teleport(PlayerEntity player , int amplifier) {
        World world = player.getWorld();
        Vec3d targetVec = player.getPos();
        Vec3d lookVec = player.getRotationVector();
        BlockPos target = null;
        for (double i = 12; i >= 2; i -= 0.5) {
            Vec3d v3d = targetVec.add(lookVec.multiply(i, i, i));
            target = new BlockPos((int) Math.round(v3d.x), (int) Math.round(v3d.y), (int) Math.round(v3d.z));
            if (!fullBlockAt(world, target) && !fullBlockAt(world, target.up())) {
                break;
            } else {
                target = null;
            }
        }
        if (target != null) {
            if (!player.getWorld().isClient) {
                Vec3d teleportVec = new Vec3d(target.getX(), target.getY(), target.getZ());
                player.teleport(teleportVec.x + 0.5, teleportVec.y, teleportVec.z + 0.5);
            }
            player.fallDistance = 0;
            player.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1F, 1F);
        }
    }

    private static boolean fullBlockAt(World world, BlockPos target){
        return Block.isShapeFullCube(world.getBlockState(target).getCollisionShape(world, target));
    }

    private void oldTeleport(PlayerEntity player) {
        Vec3d lookVec = player.getRotationVector();
        Vec3d teleportPos = player.getPos().add(lookVec.x * 30, lookVec.y * 30, lookVec.z * 30);
        player.teleport(teleportPos.x, teleportPos.y, teleportPos.z);
    }
}
