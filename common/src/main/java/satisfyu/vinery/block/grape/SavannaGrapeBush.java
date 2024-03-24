package satisfyu.vinery.block.grape;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class SavannaGrapeBush extends GrapeBush {

    public SavannaGrapeBush(Properties settings, GrapeType type) {
        super(settings, type, 3);
    }

    @Override
    public boolean canGrowPlace(LevelReader world, BlockPos blockPos, BlockState blockState) {
        return world.getRawBrightness(blockPos, 0) >= 14;
    }
}