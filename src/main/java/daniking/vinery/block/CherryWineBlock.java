package daniking.vinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class CherryWineBlock extends WineBottleBlock
{
    public CherryWineBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, STACK);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();

        if (state.get(STACK) == 1)
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.6875, 0.4375, 0.5625, 0.9375, 0.5625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.6875, 0.625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3875, 0.0125, 0.3875, 0.6125, 0.675, 0.6125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3625, 0.425, 0.3625, 0.6375, 0.5125, 0.6375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.361875, 0.299375, 0.361875, 0.638125, 0.513125, 0.638125));
        }
        else if (state.get(STACK) == 2)
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.6875, 0.25, 0.75, 0.9375, 0.375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.549375, 0.205625, 0.171875, 0.825625, 0.481875, 0.385625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0, 0.1875, 0.8125, 0.6875, 0.4375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.575, 0.0125, 0.2, 0.8, 0.6375, 0.425));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.55, 0.3, 0.175, 0.825, 0.55, 0.45));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.6875, 0.6875, 0.6875, 0.9375, 0.8125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5, 0, 0.625, 0.75, 0.6875, 0.875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5125, 0.0125, 0.6375, 0.7375, 0.6375, 0.8625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4875, 0.3, 0.6125, 0.7625, 0.55, 0.8875));
        }
        else if (state.get(STACK) == 3)
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.6875, 0.25, 0.75, 0.9375, 0.375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0, 0.1875, 0.8125, 0.6875, 0.4375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.575, 0.0125, 0.2, 0.8, 0.6375, 0.425));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.55, 0.3, 0.175, 0.825, 0.55, 0.45));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.6875, 0.5625, 0.375, 0.9375, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.5, 0.4375, 0.6875, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.2, 0.0125, 0.5125, 0.425, 0.6375, 0.7375));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.38125, 0.3, -0.0125, 0.65625, 0.55, 0.2625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.6875, 0.625, 0.6875, 0.9375, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5, 0, 0.5625, 0.75, 0.6875, 0.8125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5125, 0.0125, 0.575, 0.7375, 0.675, 0.8));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.486875, 0.236875, 0.549375, 0.763125, 0.450625, 0.825625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.549375, 0.299375, 0.174375, 0.825625, 0.513125, 0.450625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4875, 0.425, 0.55, 0.7625, 0.4875, 0.825));
        }
        else
        {
            shape = VoxelShapes.fullCube();
        }

        return shape;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }
}
