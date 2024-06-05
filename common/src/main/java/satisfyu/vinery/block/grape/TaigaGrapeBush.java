package satisfyu.vinery.block.grape;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import satisfyu.vinery.config.VineryConfig;

import java.util.Iterator;

public class TaigaGrapeBush extends GrapeBush {

    public TaigaGrapeBush(Properties settings, GrapeType type) {
        super(settings, type);
    }

    @Override
    public boolean canGrowPlace(LevelReader world, BlockPos blockPos, BlockState blockState) {
        if (world.getRawBrightness(blockPos, 0) <= 4) {
            return false;
        }
        int size = 4;
        Iterator<BlockPos> var2 = BlockPos.betweenClosed(blockPos.offset(-size, -2, -size), blockPos.offset(size, 1, size)).iterator();

        BlockPos pos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            pos = var2.next();
        } while(!(world.getBlockState(pos).getBlock() == Blocks.PODZOL || world.getBlockState(pos).getBlock() == Blocks.COARSE_DIRT || world.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK));

        return true;
    }

    public boolean isPathfindable(BlockState arg, BlockGetter arg2, BlockPos arg3, PathComputationType arg4) {
        return false;
    }
}
