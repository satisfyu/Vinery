package daniking.vinery.block;



import daniking.vinery.Vinery;
import net.minecraft.block.*;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;


public class TableBlock extends Block {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<ChestType> CHEST_TYPE = Properties.CHEST_TYPE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;


    public TableBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(CHEST_TYPE, ChestType.SINGLE).with(WATERLOGGED, false));
    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (neighborState.isOf(this) && direction.getAxis().isHorizontal()) {
            ChestType chestType = neighborState.get(CHEST_TYPE);
            if (state.get(CHEST_TYPE) == ChestType.SINGLE && chestType != ChestType.SINGLE && state.get(FACING) == neighborState.get(FACING) && TableBlock.getFacing(neighborState) == direction.getOpposite()) {
                return state.with(CHEST_TYPE, chestType.getOpposite());
            }
        } else if (TableBlock.getFacing(state) == direction) {
            return state.with(CHEST_TYPE, ChestType.SINGLE);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape top = Block.createCuboidShape(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
        VoxelShape leg1 = Block.createCuboidShape(1.0, 0.0, 1.0, 4.0, 13.0, 4.0);
        VoxelShape leg2 = Block.createCuboidShape(1.0, 0.0, 12.0, 4.0, 13.0, 15.0);
        VoxelShape leg3 = Block.createCuboidShape(12.0, 0.0, 12.0, 15.0, 13.0, 15.0);
        VoxelShape leg4 = Block.createCuboidShape(12.0, 0.0, 1.0, 15.0, 13.0, 4.0);

        if (state.get(CHEST_TYPE) == ChestType.SINGLE) {
            return VoxelShapes.union(top, leg1, leg2, leg3, leg4);
        }


        VoxelShape legs1;
        VoxelShape legs2;

        Direction direction = state.get(FACING);
        ChestType chestType = state.get(CHEST_TYPE);

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
        return VoxelShapes.union(top, legs1, legs2);
    }

    public static Direction getFacing(BlockState state) {
        Direction direction = state.get(FACING);
        return state.get(CHEST_TYPE) == ChestType.LEFT ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction3;
        ChestType chestType = ChestType.SINGLE;
        Direction direction = ctx.getPlayerFacing().getOpposite();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = ctx.shouldCancelInteraction();
        Direction direction2 = ctx.getSide();
        if (direction2.getAxis().isHorizontal() && bl && (direction3 = this.getNeighborChestDirection(ctx, direction2.getOpposite())) != null && direction3.getAxis() != direction2.getAxis()) {
            direction = direction3;
            chestType = direction.rotateYCounterclockwise() == direction2.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
        }
        if (chestType == ChestType.SINGLE && !bl) {
            if (direction == this.getNeighborChestDirection(ctx, direction.rotateYClockwise())) {
                chestType = ChestType.LEFT;
            } else if (direction == this.getNeighborChestDirection(ctx, direction.rotateYCounterclockwise())) {
                chestType = ChestType.RIGHT;
            }
        }
        return this.getDefaultState().with(FACING, direction).with(CHEST_TYPE, chestType).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Nullable
    private Direction getNeighborChestDirection(ItemPlacementContext ctx, Direction dir) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir));
        return blockState.isOf(this) && blockState.get(CHEST_TYPE) == ChestType.SINGLE ? blockState.get(FACING) : null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CHEST_TYPE, WATERLOGGED);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

