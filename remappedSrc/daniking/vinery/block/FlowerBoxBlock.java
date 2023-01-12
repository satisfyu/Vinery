package daniking.vinery.block;

import com.google.common.collect.Maps;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.util.EnumBlockSide;
import net.minecraft.ChatFormatting;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class FlowerBoxBlock extends Block {
	private static final Map<Block, Block> CONTENT_TO_POTTED = Maps.newHashMap();
	private final Block content;
	
	protected static final VoxelShape SHAPE_CENTER_EW = makeShapeCenterEW();
	protected static final VoxelShape SHAPE_CENTER_NS = makeShapeCenterNS();
	protected static final VoxelShape SHAPE_BOTTOM_E = makeShapeBottomE();
	protected static final VoxelShape SHAPE_BOTTOM_W = makeShapeBottomW();
	protected static final VoxelShape SHAPE_BOTTOM_N = makeShapeBottomN();
	protected static final VoxelShape SHAPE_BOTTOM_S = makeShapeBottomS();
	protected static final VoxelShape SHAPE_TOP_E = makeShapeTopE();
	protected static final VoxelShape SHAPE_TOP_W = makeShapeTopW();
	protected static final VoxelShape SHAPE_TOP_N = makeShapeTopN();
	protected static final VoxelShape SHAPE_TOP_S = makeShapeTopS();
	
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<EnumBlockSide> SIDE = EnumProperty.create("side", EnumBlockSide.class);
	
	public FlowerBoxBlock(Block content, BlockBehaviour.Properties settings) {
		super(settings);
		this.content = content;
		CONTENT_TO_POTTED.put(content, this);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(SIDE, EnumBlockSide.CENTER));
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		if (ctx.getNearestLookingDirection() == Direction.DOWN) {
			return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection()).setValue(SIDE, EnumBlockSide.CENTER);
		} else if (ctx.getNearestLookingDirection() == Direction.UP) {
			return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection()).setValue(SIDE, EnumBlockSide.TOP);
		}
		return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection()).setValue(SIDE, EnumBlockSide.BOTTOM);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, SIDE);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		switch (state.getValue(SIDE)) {
			case CENTER:
				// switch/case messing up directions here, wtf?
				if (state.getValue(FACING) == Direction.EAST || state.getValue(FACING) == Direction.WEST) {
					return SHAPE_CENTER_EW;
				} else {
					return SHAPE_CENTER_NS;
				}
			case TOP:
				if (state.getValue(FACING) == Direction.EAST) {
					return SHAPE_TOP_E;
				} else if (state.getValue(FACING) == Direction.WEST) {
					return SHAPE_TOP_W;
				} else if (state.getValue(FACING) == Direction.NORTH) {
					return SHAPE_TOP_N;
				} else {
					return SHAPE_TOP_S;
				}
			case BOTTOM:
				if (state.getValue(FACING) == Direction.EAST) {
					return SHAPE_BOTTOM_E;
				} else if (state.getValue(FACING) == Direction.WEST) {
					return SHAPE_BOTTOM_W;
				} else if (state.getValue(FACING) == Direction.NORTH) {
					return SHAPE_BOTTOM_N;
				} else {
					return SHAPE_BOTTOM_S;
				}
			default:
				return Shapes.block();
		}
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item item = itemStack.getItem();
		BlockState blockState = (item instanceof BlockItem ? (Block) CONTENT_TO_POTTED.getOrDefault(((BlockItem) item).getBlock(), Blocks.AIR) : Blocks.AIR).defaultBlockState();
		boolean bl = blockState.is(Blocks.AIR);
		boolean bl2 = this.isEmpty();
		if (bl != bl2) {
			if (bl2) {
				world.setBlock(pos, blockState.setValue(SIDE, state.getValue(SIDE)).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
			} else {
				ItemStack itemStack2 = new ItemStack(this.content);
				if (itemStack.isEmpty()) {
					player.setItemInHand(hand, itemStack2);
				} else if (!player.addItem(itemStack2)) {
					player.drop(itemStack2, false);
				}
				
				world.setBlock(pos, ObjectRegistry.FLOWER_BOX.defaultBlockState().setValue(SIDE, state.getValue(SIDE)).setValue(FACING, state.getValue(FACING)), 3);
			}
			
			world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			return InteractionResult.sidedSuccess(world.isClientSide);
		} else {
			return InteractionResult.CONSUME;
		}
	}
	
	@Override
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return this.isEmpty() ? super.getCloneItemStack(world, pos, state) : new ItemStack(this.content);
	}
	
	private boolean isEmpty() {
		return this.content == Blocks.AIR;
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
	}
	
	public Block getContent() {
		return this.content;
	}
	
	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		return false;
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	
	public static VoxelShape makeShapeCenterNS() { // Center, North, South
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.28125, 1, 0.34375, 0.5), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.28125, 0.9375, 0.34375, 0.34375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.65625, 0.9375, 0.34375, 0.71875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.5, 1, 0.34375, 0.71875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.28125, 0.0625, 0.34375, 0.5), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.5, 0.0625, 0.34375, 0.71875), BooleanOp.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeCenterEW() { // Center, East, West
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5, 0, 0.9375, 0.71875, 0.34375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.65625, 0, 0.0625, 0.71875, 0.34375, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.28125, 0, 0.0625, 0.34375, 0.34375, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.28125, 0, 0.9375, 0.5, 0.34375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5, 0, 0, 0.71875, 0.34375, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.28125, 0, 0, 0.5, 0.34375, 0.0625), BooleanOp.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeBottomS() { // Bottom, South
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.5625, 1, 0.34375, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.5625, 0.9375, 0.34375, 0.625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.34375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.78125, 1, 0.34375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.5625, 0.0625, 0.34375, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.78125, 0.0625, 0.34375, 1), BooleanOp.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeBottomN() { // Bottom, North
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.21875, 0.0625, 0.34375, 0.4375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.375, 0.9375, 0.34375, 0.4375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0, 0.9375, 0.34375, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0, 0.0625, 0.34375, 0.21875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.21875, 1, 0.34375, 0.4375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0, 1, 0.34375, 0.21875), BooleanOp.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeBottomE() { // Bottom, East
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5625, 0, 0, 0.78125, 0.34375, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5625, 0, 0.0625, 0.625, 0.34375, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.0625, 1, 0.34375, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.78125, 0, 0, 1, 0.34375, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5625, 0, 0.9375, 0.78125, 0.34375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.78125, 0, 0.9375, 1, 0.34375, 1), BooleanOp.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeBottomW() { // Bottom, West
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.21875, 0, 0.9375, 0.4375, 0.34375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.375, 0, 0.0625, 0.4375, 0.34375, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.0625, 0.0625, 0.34375, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.9375, 0.21875, 0.34375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.21875, 0, 0, 0.4375, 0.34375, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0, 0.21875, 0.34375, 0.0625), BooleanOp.OR);

		return shape;
	}
	
	public static VoxelShape makeShapeTopS() { // Top, South
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.625, 0.5625, 1, 0.96875, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.625, 0.5625, 0.9375, 0.96875, 0.625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.625, 0.9375, 0.9375, 0.96875, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.625, 0.78125, 1, 0.96875, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.625, 0.5625, 0.0625, 0.96875, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.625, 0.78125, 0.0625, 0.96875, 1), BooleanOp.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeTopN() { // Top, North
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.625, 0.21875, 0.0625, 0.96875, 0.4375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.625, 0.375, 0.9375, 0.96875, 0.4375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.625, 0, 0.9375, 0.96875, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.625, 0, 0.0625, 0.96875, 0.21875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.625, 0.21875, 1, 0.96875, 0.4375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.625, 0, 1, 0.96875, 0.21875), BooleanOp.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeTopE() { // Top, East
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5625, 0.625, 0, 0.78125, 0.96875, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5625, 0.625, 0.0625, 0.625, 0.96875, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.625, 0.0625, 1, 0.96875, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.78125, 0.625, 0, 1, 0.96875, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5625, 0.625, 0.9375, 0.78125, 0.96875, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.78125, 0.625, 0.9375, 1, 0.96875, 1), BooleanOp.OR);

		return shape;
	}
	
	public static VoxelShape makeShapeTopW() { // Top, West
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.21875, 0.625, 0.9375, 0.4375, 0.96875, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.375, 0.625, 0.0625, 0.4375, 0.96875, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.625, 0.0625, 0.0625, 0.96875, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.625, 0.9375, 0.21875, 0.96875, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.21875, 0.625, 0, 0.4375, 0.96875, 0.0625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.625, 0, 0.21875, 0.96875, 0.0625), BooleanOp.OR);

		return shape;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
		tooltip.add(Component.translatable("block.vinery.canbeplaced.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
	}
}