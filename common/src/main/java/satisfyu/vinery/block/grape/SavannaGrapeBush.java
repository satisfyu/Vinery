package satisfyu.vinery.block.grape;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfyu.vinery.util.GrapevineType;

public class SavannaGrapeBush extends GrapeBush {

    public SavannaGrapeBush(Properties settings, GrapevineType type) {
        super(settings, type, 3);
    }

    @Override
    public boolean canGrowPlace(LevelReader world, BlockPos blockPos, BlockState blockState) {
        return world.getRawBrightness(blockPos, 0) >= 14;
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0, 1, 1, 1), BooleanOp.OR);
        return shape;
    }

}
