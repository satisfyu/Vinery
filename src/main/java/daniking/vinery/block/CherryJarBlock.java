package daniking.vinery.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CherryJarBlock extends StackableBlock
{
    public CherryJarBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        VoxelShape shape = VoxelShapes.empty();

        if (state.get(STACK) == 1)
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.000625, 0.5, 0.8125, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.375, 0.53125, 0.78125, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.53125, 0.59375, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.59375, 0.59375, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.375, 0.59375, 0.78125, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.53125, 0.78125, 0.4375, 0.53125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.71875, 0.78125, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.375, 0.71875, 0.71875, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.375, 0.53125, 0.71875, 0.4375, 0.53125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.8125, 0.375625, 0.5));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.5625, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.53125, 0.59375, 0.5, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.4375, 0.53125, 0.8125, 0.5, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.75, 0.8125, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.8125, 0.000625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.000625, 0.5625, 0.75, 0.000625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.375, 0.5, 0.8125, 0.375, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.5, 0.53125, 0.78125, 0.5625, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.0625, 0.5625, 0.5625, 0.3125, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.0625, 0.5625, 0.8125, 0.3125, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.0625, 0.75, 0.75, 0.3125, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.0625, 0.5, 0.75, 0.3125, 0.5));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.71875, 0.8125, 0.5, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.5, 0.8125, 0.5, 0.53125));
        }
        else if (state.get(STACK) == 2)
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.000625, 0.5, 0.8125, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.40625, 0.375, 0.53125, 0.40625, 0.4375, 0.59375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.375, 0.53125, 0.21875, 0.4375, 0.59375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.28125, 0.375, 0.46875, 0.34375, 0.4375, 0.46875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.28125, 0.375, 0.65625, 0.34375, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.0625, 0.4375, 0.375, 0.3125, 0.4375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.0625, 0.6875, 0.375, 0.3125, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.0625, 0.5, 0.4375, 0.3125, 0.625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.0625, 0.5, 0.1875, 0.3125, 0.625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.4375, 0.4375, 0.5, 0.46875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.65625, 0.4375, 0.5, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.5, 0.46875, 0.40625, 0.5625, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.375, 0.4375, 0.4375, 0.375, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.000625, 0.4375, 0.4375, 0.000625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.000625, 0.6875, 0.4375, 0.375625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.40625, 0.4375, 0.46875, 0.4375, 0.5, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.46875, 0.21875, 0.5, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.000625, 0.4375, 0.1875, 0.375625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.000625, 0.4375, 0.4375, 0.375625, 0.4375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.375, 0.65625, 0.40625, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.375, 0.46875, 0.40625, 0.4375, 0.46875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.375, 0.46875, 0.21875, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.40625, 0.375, 0.46875, 0.40625, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.000625, 0.4375, 0.4375, 0.375625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.375, 0.53125, 0.78125, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.53125, 0.59375, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.59375, 0.59375, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.375, 0.59375, 0.78125, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.53125, 0.78125, 0.4375, 0.53125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.71875, 0.78125, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.375, 0.71875, 0.71875, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.375, 0.53125, 0.71875, 0.4375, 0.53125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.8125, 0.375625, 0.5));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.5625, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.53125, 0.59375, 0.5, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.4375, 0.53125, 0.8125, 0.5, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.75, 0.8125, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.8125, 0.000625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.000625, 0.5625, 0.75, 0.000625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.375, 0.5, 0.8125, 0.375, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.5, 0.53125, 0.78125, 0.5625, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.0625, 0.5625, 0.5625, 0.3125, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.0625, 0.5625, 0.8125, 0.3125, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.0625, 0.75, 0.75, 0.3125, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.0625, 0.5, 0.75, 0.3125, 0.5));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.71875, 0.8125, 0.5, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.5, 0.8125, 0.5, 0.53125));
        }
        else if (state.get(STACK) == 3)
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.000625, 0.5, 0.8125, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.40625, 0.375, 0.53125, 0.40625, 0.4375, 0.59375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.375, 0.53125, 0.21875, 0.4375, 0.59375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.28125, 0.375, 0.46875, 0.34375, 0.4375, 0.46875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.28125, 0.375, 0.65625, 0.34375, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.000625, 0.5, 0.375, 0.000625, 0.625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.0625, 0.4375, 0.375, 0.3125, 0.4375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.0625, 0.6875, 0.375, 0.3125, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.0625, 0.5, 0.4375, 0.3125, 0.625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.0625, 0.5, 0.1875, 0.3125, 0.625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.4375, 0.4375, 0.5, 0.46875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.65625, 0.4375, 0.5, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.5, 0.46875, 0.40625, 0.5625, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.375, 0.4375, 0.4375, 0.375, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.000625, 0.4375, 0.4375, 0.000625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.000625, 0.6875, 0.4375, 0.375625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.40625, 0.4375, 0.46875, 0.4375, 0.5, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.46875, 0.21875, 0.5, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.000625, 0.4375, 0.1875, 0.375625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.000625, 0.4375, 0.4375, 0.375625, 0.4375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.375, 0.65625, 0.40625, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.375, 0.46875, 0.40625, 0.4375, 0.46875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.21875, 0.375, 0.46875, 0.21875, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.40625, 0.375, 0.46875, 0.40625, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.000625, 0.4375, 0.4375, 0.375625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.375, 0.53125, 0.78125, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.53125, 0.59375, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.59375, 0.59375, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.375, 0.59375, 0.78125, 0.4375, 0.65625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.53125, 0.78125, 0.4375, 0.53125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.375, 0.71875, 0.78125, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.375, 0.71875, 0.71875, 0.4375, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.375, 0.53125, 0.71875, 0.4375, 0.53125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.8125, 0.375625, 0.5));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.375, -0.21875, 0.65625, 0.4375, -0.15625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.46875, 0.375, -0.21875, 0.46875, 0.4375, -0.15625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.53125, 0.375, -0.28125, 0.59375, 0.4375, -0.28125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.53125, 0.375, -0.09375, 0.59375, 0.4375, -0.09375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5, 0.0625, -0.3125, 0.625, 0.3125, -0.3125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5, 0.0625, -0.0625, 0.625, 0.3125, -0.0625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.0625, -0.25, 0.6875, 0.3125, -0.125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.0625, -0.25, 0.4375, 0.3125, -0.125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.4375, -0.3125, 0.6875, 0.5, -0.28125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.4375, -0.09375, 0.6875, 0.5, -0.0625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.46875, 0.5, -0.28125, 0.65625, 0.5625, -0.09375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.375, -0.3125, 0.6875, 0.375, -0.0625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.000625, -0.3125, 0.6875, 0.000625, -0.0625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.000625, -0.0625, 0.6875, 0.375625, -0.0625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.4375, -0.28125, 0.6875, 0.5, -0.09375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.4375, -0.28125, 0.46875, 0.5, -0.09375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.000625, -0.3125, 0.4375, 0.375625, -0.0625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.000625, -0.3125, 0.6875, 0.375625, -0.3125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.46875, 0.375, -0.09375, 0.65625, 0.4375, -0.09375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.46875, 0.375, -0.28125, 0.65625, 0.4375, -0.28125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.46875, 0.375, -0.28125, 0.46875, 0.4375, -0.09375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.65625, 0.375, -0.28125, 0.65625, 0.4375, -0.09375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.000625, -0.3125, 0.6875, 0.375625, -0.0625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.5625, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.53125, 0.59375, 0.5, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.78125, 0.4375, 0.53125, 0.8125, 0.5, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.75, 0.8125, 0.375625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.000625, 0.5, 0.8125, 0.000625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.000625, 0.5625, 0.75, 0.000625, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.375, 0.5, 0.8125, 0.375, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.59375, 0.5, 0.53125, 0.78125, 0.5625, 0.71875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.0625, 0.5625, 0.5625, 0.3125, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.0625, 0.5625, 0.8125, 0.3125, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.0625, 0.75, 0.75, 0.3125, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.0625, 0.5, 0.75, 0.3125, 0.5));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.71875, 0.8125, 0.5, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.5, 0.8125, 0.5, 0.53125));
        }
        else
        {
            shape = VoxelShapes.fullCube();
        }

        return shape;
    }
}
