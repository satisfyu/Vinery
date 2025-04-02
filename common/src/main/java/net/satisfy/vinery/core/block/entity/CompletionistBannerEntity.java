package net.satisfy.vinery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import net.satisfy.vinery.platform.PlatformHelper;

import java.util.List;

public class CompletionistBannerEntity extends BlockEntity {

    public CompletionistBannerEntity(BlockPos blockPos, BlockState state) {
        super(EntityTypeRegistry.VINERY_STANDARD.get(), blockPos, state);
    }

    public static void tick(Level level, BlockPos pos) {
        if (!level.isClientSide && PlatformHelper.shouldGiveEffect()) {
            AABB effectRadius = new AABB(pos).inflate(8);
            List<Player> players = level.getEntitiesOfClass(Player.class, effectRadius);
            for (Player player : players) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1, true, false));
            }
        }
    }
}
