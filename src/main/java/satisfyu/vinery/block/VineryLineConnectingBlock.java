package satisfyu.vinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.VineryLineConnectingType;
import satisfyu.vinery.util.VineryProperties;


public class VineryLineConnectingBlock extends Block {

    public static final DirectionProperty FACING;
    public static final EnumProperty<VineryLineConnectingType> TYPE;

    public VineryLineConnectingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState().with(FACING, Direction.NORTH)).with(TYPE, VineryLineConnectingType.NONE)));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction facing = context.getHorizontalPlayerFacing().getOpposite();
        BlockState blockState = this.getDefaultState().with(FACING, facing);

        World world = context.getWorld();
        BlockPos clickedPos = context.getBlockPos();

        return switch (facing) {
            case EAST ->
                    blockState.with(TYPE, getType(blockState, world.getBlockState(clickedPos.south()), world.getBlockState(clickedPos.north())));
            case SOUTH ->
                    blockState.with(TYPE, getType(blockState, world.getBlockState(clickedPos.west()), world.getBlockState(clickedPos.east())));
            case WEST ->
                    blockState.with(TYPE, getType(blockState, world.getBlockState(clickedPos.north()), world.getBlockState(clickedPos.south())));
            default ->
                    blockState.with(TYPE, getType(blockState, world.getBlockState(clickedPos.east()), world.getBlockState(clickedPos.west())));
        };
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClient) return;

        Direction facing = state.get(FACING);

        VineryLineConnectingType type;
        switch (facing) {
            case EAST ->
                    type = getType(state, world.getBlockState(pos.south()), world.getBlockState(pos.north()));
            case SOUTH ->
                    type =  getType(state, world.getBlockState(pos.west()), world.getBlockState(pos.east()));
            case WEST ->
                    type =  getType(state, world.getBlockState(pos.north()), world.getBlockState(pos.south()));
            default ->
                    type =  getType(state, world.getBlockState(pos.east()), world.getBlockState(pos.west()));
        }
        if (state.get(TYPE) != type) {
            state = state.with(TYPE, type);
        }
        world.setBlockState(pos, state, 3);
    }

    public VineryLineConnectingType getType(BlockState state, BlockState left, BlockState right) {
        boolean shape_left_same = left.getBlock() == state.getBlock() && left.get(FACING) == state.get(FACING);
        boolean shape_right_same = right.getBlock() == state.getBlock() && right.get(FACING) == state.get(FACING);

        if (shape_left_same && shape_right_same) {
            return VineryLineConnectingType.MIDDLE.MIDDLE;
        } else if (shape_left_same) {
            return VineryLineConnectingType.LEFT.LEFT;
        } else if (shape_right_same) {
            return VineryLineConnectingType.RIGHT.RIGHT;
        }
        return VineryLineConnectingType.NONE.NONE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }


    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
        TYPE = VineryProperties.VINERY_LINE_CONNECTING_TYPE;
    }

}
