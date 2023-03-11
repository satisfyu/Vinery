package satisfyu.vinery.block;



import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import satisfyu.vinery.util.VineryLineConnectingType;


public class TableBlock extends VineryLineConnectingBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final VoxelShape TOP_SHAPE;
    public static final VoxelShape[] LEG_SHAPES;

    public TableBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState().with(WATERLOGGED, false)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        VineryLineConnectingType type = state.get(TYPE);

        if (type == VineryLineConnectingType.MIDDLE) {
            return TOP_SHAPE;
        }

        if((direction == Direction.NORTH && type == VineryLineConnectingType.LEFT) || (direction == Direction.SOUTH && type == VineryLineConnectingType.RIGHT)){
            return VoxelShapes.union(TOP_SHAPE, LEG_SHAPES[0], LEG_SHAPES[3]);
        }
        else if((direction == Direction.NORTH && type == VineryLineConnectingType.RIGHT) || (direction == Direction.SOUTH && type == VineryLineConnectingType.LEFT)){
            return VoxelShapes.union(TOP_SHAPE, LEG_SHAPES[1], LEG_SHAPES[2]);
        }
        else if((direction == Direction.EAST && type == VineryLineConnectingType.LEFT) || (direction == Direction.WEST && type == VineryLineConnectingType.RIGHT)){
            return VoxelShapes.union(TOP_SHAPE, LEG_SHAPES[0], LEG_SHAPES[1]);
        }
        else if((direction == Direction.EAST && type == VineryLineConnectingType.LEFT.RIGHT) || (direction == Direction.WEST && type == VineryLineConnectingType.LEFT)){
            return VoxelShapes.union(TOP_SHAPE, LEG_SHAPES[2], LEG_SHAPES[3]);
        }
        return VoxelShapes.union(TOP_SHAPE, LEG_SHAPES);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {

        World world = context.getWorld();
        BlockPos clickedPos = context.getBlockPos();
        return super.getPlacementState(context).with(WATERLOGGED, world.getFluidState(clickedPos).getFluid() == Fluids.WATER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        TOP_SHAPE = Block.createCuboidShape(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
        LEG_SHAPES = new VoxelShape[]{
                Block.createCuboidShape(1.0, 0.0, 1.0, 4.0, 13.0, 4.0), //north
                Block.createCuboidShape(12.0, 0.0, 1.0, 15.0, 13.0, 4.0), //east
                Block.createCuboidShape(12.0, 0.0, 12.0, 15.0, 13.0, 15.0), //south
                Block.createCuboidShape(1.0, 0.0, 12.0, 4.0, 13.0, 15.0) //west
        };
    }

}
