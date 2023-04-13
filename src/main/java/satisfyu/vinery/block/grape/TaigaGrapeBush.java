package satisfyu.vinery.block.grape;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import satisfyu.vinery.util.GrapevineType;

import java.util.Iterator;

public class TaigaGrapeBush extends GrapeBush {

    public TaigaGrapeBush(Settings settings, GrapevineType type) {
        super(settings, type, 5);
    }

    @Override
    public boolean canGrowPlace(WorldView world, BlockPos blockPos, BlockState blockState) {
        if (world.getBaseLightLevel(blockPos, 0) <= 4) {
            return false;
        }
        int size = 4;
        Iterator<BlockPos> var2 = BlockPos.iterate(blockPos.add(-size, -2, -size), blockPos.add(size, 1, size)).iterator();

        BlockPos pos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            pos = var2.next();
        } while(!(world.getBlockState(pos).getBlock() == Blocks.PODZOL || world.getBlockState(pos).getBlock() == Blocks.COARSE_DIRT || world.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK));

        return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.slowMovement(state, new Vec3d(0.800000011920929, 0.75, 0.800000011920929));
            if (!world.isClient && state.get(AGE) > 0 && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                double d = Math.abs(entity.getX() - entity.lastRenderX);
                double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                if (d >= 0.003000000026077032 || e >= 0.003000000026077032) {
                    entity.damage(world.getDamageSources().sweetBerryBush(), 1.0F);
                }
            }

        }
    }
}
