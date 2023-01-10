package daniking.vinery.block;



import daniking.vinery.Vinery;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


public class TableBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<ChestType> CHEST_TYPE = BlockStateProperties.CHEST_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    public TableBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(CHEST_TYPE, ChestType.SINGLE).setValue(WATERLOGGED, false));
    }


    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (neighborState.is(this) && direction.getAxis().isHorizontal()) {
            ChestType chestType = neighborState.getValue(CHEST_TYPE);
            if (state.getValue(CHEST_TYPE) == ChestType.SINGLE && chestType != ChestType.SINGLE && state.getValue(FACING) == neighborState.getValue(FACING) && TableBlock.getFacing(neighborState) == direction.getOpposite()) {
                return state.setValue(CHEST_TYPE, chestType.getOpposite());
            }
        } else if (TableBlock.getFacing(state) == direction) {
            return state.setValue(CHEST_TYPE, ChestType.SINGLE);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape top = Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
        VoxelShape leg1 = Block.box(1.0, 0.0, 1.0, 4.0, 13.0, 4.0);
        VoxelShape leg2 = Block.box(1.0, 0.0, 12.0, 4.0, 13.0, 15.0);
        VoxelShape leg3 = Block.box(12.0, 0.0, 12.0, 15.0, 13.0, 15.0);
        VoxelShape leg4 = Block.box(12.0, 0.0, 1.0, 15.0, 13.0, 4.0);

        if (state.getValue(CHEST_TYPE) == ChestType.SINGLE) {
            return Shapes.or(top, leg1, leg2, leg3, leg4);
        }


        VoxelShape legs1;
        VoxelShape legs2;

        Direction direction = state.getValue(FACING);
        ChestType chestType = state.getValue(CHEST_TYPE);

        if((direction == Direction.NORTH && chestType == ChestType.LEFT) || (direction == Direction.SOUTH && chestType == ChestType.RIGHT)){
            legs1 = leg1;
            legs2 = leg2;
        }
        else if((direction == Direction.NORTH && chestType == ChestType.RIGHT) || (direction == Direction.SOUTH && chestType == ChestType.LEFT)){
            legs1 = leg3;
            legs2 = leg4;
        }
        else if((direction == Direction.EAST && chestType == ChestType.RIGHT) || (direction == Direction.WEST && chestType == ChestType.LEFT)){
            legs1 = leg2;
            legs2 = leg3;
        }
        else if((direction == Direction.EAST && chestType == ChestType.LEFT) || (direction == Direction.WEST && chestType == ChestType.RIGHT)){
            legs1 = leg1;
            legs2 = leg4;
        }
        else {
            Vinery.LOGGER.error("Table blockstate not correct!");
            return top;
        }
        return Shapes.or(top, legs1, legs2);
    }

    public static Direction getFacing(BlockState state) {
        Direction direction = state.getValue(FACING);
        return state.getValue(CHEST_TYPE) == ChestType.LEFT ? direction.getClockWise() : direction.getCounterClockWise();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction3;
        ChestType chestType = ChestType.SINGLE;
        Direction direction = ctx.getHorizontalDirection().getOpposite();
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        boolean bl = ctx.isSecondaryUseActive();
        Direction direction2 = ctx.getClickedFace();
        if (direction2.getAxis().isHorizontal() && bl && (direction3 = this.getNeighborChestDirection(ctx, direction2.getOpposite())) != null && direction3.getAxis() != direction2.getAxis()) {
            direction = direction3;
            chestType = direction.getCounterClockWise() == direction2.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
        }
        if (chestType == ChestType.SINGLE && !bl) {
            if (direction == this.getNeighborChestDirection(ctx, direction.getClockWise())) {
                chestType = ChestType.LEFT;
            } else if (direction == this.getNeighborChestDirection(ctx, direction.getCounterClockWise())) {
                chestType = ChestType.RIGHT;
            }
        }
        return this.defaultBlockState().setValue(FACING, direction).setValue(CHEST_TYPE, chestType).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @Nullable
    private Direction getNeighborChestDirection(BlockPlaceContext ctx, Direction dir) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().relative(dir));
        return blockState.is(this) && blockState.getValue(CHEST_TYPE) == ChestType.SINGLE ? blockState.getValue(FACING) : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CHEST_TYPE, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return false;
    }
}

