package satisfyu.vinery.block.grape;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import satisfyu.vinery.util.GrapevineType;

public class SavannaGrapeBush extends GrapeBush {

    public SavannaGrapeBush(Settings settings, GrapevineType type) {
        super(settings, type, 3);
    }

    @Override
    public boolean canGrowPlace(WorldView world, BlockPos blockPos, BlockState blockState) {
        return world.getBaseLightLevel(blockPos, 0) >= 14;
    }
}
