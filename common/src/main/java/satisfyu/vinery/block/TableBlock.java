package satisfyu.vinery.block;



import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfyu.vinery.util.VineryLineConnectingType;

import java.util.List;


public class TableBlock extends VineryLineConnectingBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED;
    public static final VoxelShape TOP_SHAPE;
    public static final VoxelShape[] LEG_SHAPES;

    public TableBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any().setValue(WATERLOGGED, false)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        VineryLineConnectingType type = state.getValue(TYPE);

        if (type == VineryLineConnectingType.MIDDLE) {
            return TOP_SHAPE;
        }

        if((direction == Direction.NORTH && type == VineryLineConnectingType.LEFT) || (direction == Direction.SOUTH && type == VineryLineConnectingType.RIGHT)){
            return Shapes.or(TOP_SHAPE, LEG_SHAPES[0], LEG_SHAPES[3]);
        }
        else if((direction == Direction.NORTH && type == VineryLineConnectingType.RIGHT) || (direction == Direction.SOUTH && type == VineryLineConnectingType.LEFT)){
            return Shapes.or(TOP_SHAPE, LEG_SHAPES[1], LEG_SHAPES[2]);
        }
        else if((direction == Direction.EAST && type == VineryLineConnectingType.LEFT) || (direction == Direction.WEST && type == VineryLineConnectingType.RIGHT)){
            return Shapes.or(TOP_SHAPE, LEG_SHAPES[0], LEG_SHAPES[1]);
        }
        else if((direction == Direction.EAST && type == VineryLineConnectingType.LEFT.RIGHT) || (direction == Direction.WEST && type == VineryLineConnectingType.LEFT)){
            return Shapes.or(TOP_SHAPE, LEG_SHAPES[2], LEG_SHAPES[3]);
        }
        return Shapes.or(TOP_SHAPE, LEG_SHAPES);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Level world = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        return super.getStateForPlacement(context).setValue(WATERLOGGED, world.getFluidState(clickedPos).getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        TOP_SHAPE = Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
        LEG_SHAPES = new VoxelShape[]{
                Block.box(1.0, 0.0, 1.0, 4.0, 13.0, 4.0), //north
                Block.box(12.0, 0.0, 1.0, 15.0, 13.0, 4.0), //east
                Block.box(12.0, 0.0, 12.0, 15.0, 13.0, 15.0), //south
                Block.box(1.0, 0.0, 12.0, 4.0, 13.0, 15.0) //west
        };
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.expandable.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

}
