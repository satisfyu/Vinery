package daniking.vinery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MellohiWineBlock extends WineBottleBlock
{
    public MellohiWineBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.25, 0.4375, 0.5625, 0.5, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.425, 0.2375, 0.425, 0.575, 0.5125, 0.575));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.625, 0.4375, 0.5625, 0.6875, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.3125, 0.125, 0.3125, 0.6875, 0.25, 0.6875));
        shape = Shapes.or(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.125, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.2625, 0.0125, 0.2625, 0.7375, 0.1125, 0.7375));
        shape = Shapes.or(shape, Shapes.box(0.375, 0.5, 0.375, 0.625, 0.625, 0.625));

        return shape;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return super.getStateForPlacement(ctx);
    }
}
