package satisfyu.vinery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import satisfyu.vinery.registry.VineryBlockEntityTypes;

public class StandardBlockEntity extends BlockEntity {
    public StandardBlockEntity(BlockPos blockPos, BlockState state) {
        super(VineryBlockEntityTypes.STANDARD.get(), blockPos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        world.getEntitiesOfClass(Player.class, new AABB(pos).inflate(8F), player -> true).forEach(player -> player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2, true, false, true)));
    }
}