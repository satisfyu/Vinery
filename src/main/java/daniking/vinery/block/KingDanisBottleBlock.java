package daniking.vinery.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class KingDanisBottleBlock extends FacingBlock
{
    public KingDanisBottleBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.59375, 0.4375, 0.5625, 0.84375, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.96875, 0.4375, 0.5625, 1.03125, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.353935625, 0.0019275, 0.249875, 0.64520125, 0.4396775, 0.750125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.40625, 0.25, 0.8125, 0.59375, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.1875, 0.15625, 0.5, 0.3125, 0.71875, 0.5));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.15625, 0.5, 1.1875, 0.71875, 0.5));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.51261, 0.028476875, 0.2500625, 0.699985, 0.465851875, 0.7499375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.300015, 0.028476875, 0.2500625, 0.48739, 0.465851875, 0.7499375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.84375, 0.375, 0.625, 0.96875, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.203769375, 0.3994225, 0.2624375, 0.797644375, 0.5620475, 0.7375625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.35850375, 0.0169225, 0.2613125, 0.625769375, 0.3994225, 0.7375625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5250475, 0.040914375, 0.2625, 0.6875475, 0.453414375, 0.7375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3124525, 0.040914375, 0.2625, 0.4749525, 0.453414375, 0.7375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.175, 0.425, 0.2375, 0.825, 0.4875, 0.7625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, -0.25, 0.234375, 0.6875, 0, 0.234375));

        return shape;
    }
}
