package daniking.vinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class WineBottleBlock extends StackableBlock {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static VoxelShape SHAPE = VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.875, 0.875);

    public WineBottleBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(STACK, 1).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, STACK);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0.6875, 0.4375, 0.5625, 0.9375, 0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.6875, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3625, 0.425, 0.3625, 0.6375, 0.5125, 0.6375));

        return shape;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }
}
