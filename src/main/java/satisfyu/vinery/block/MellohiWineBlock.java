package satisfyu.vinery.block;

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

public class MellohiWineBlock extends WineBottleBlock
{
    public MellohiWineBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.25, 0.4375, 0.5625, 0.5, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.425, 0.2375, 0.425, 0.575, 0.5125, 0.575));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.625, 0.4375, 0.5625, 0.6875, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.125, 0.3125, 0.6875, 0.25, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.125, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.2625, 0.0125, 0.2625, 0.7375, 0.1125, 0.7375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.5, 0.375, 0.625, 0.625, 0.625));

        return shape;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return super.getPlacementState(ctx);
    }
}
