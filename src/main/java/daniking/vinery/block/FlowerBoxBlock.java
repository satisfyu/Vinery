package daniking.vinery.block;

import com.google.common.collect.Maps;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.util.EnumBlockSide;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

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
	
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final EnumProperty<EnumBlockSide> SIDE = EnumProperty.of("side", EnumBlockSide.class);
	
	public FlowerBoxBlock(Block content, AbstractBlock.Settings settings) {
		super(settings);
		this.content = content;
		CONTENT_TO_POTTED.put(content, this);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(SIDE, EnumBlockSide.CENTER));
	}
	
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (ctx.getPlayerLookDirection() == Direction.DOWN) {
			return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(SIDE, EnumBlockSide.CENTER);
		} else if (ctx.getPlayerLookDirection() == Direction.UP) {
			return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(SIDE, EnumBlockSide.TOP);
		}
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(SIDE, EnumBlockSide.BOTTOM);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, SIDE);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(SIDE)) {
			case CENTER:
				// switch/case messing up directions here, wtf?
				if (state.get(FACING) == Direction.EAST || state.get(FACING) == Direction.WEST) {
					return SHAPE_CENTER_EW;
				} else {
					return SHAPE_CENTER_NS;
				}
			case TOP:
				if (state.get(FACING) == Direction.EAST) {
					return SHAPE_TOP_E;
				} else if (state.get(FACING) == Direction.WEST) {
					return SHAPE_TOP_W;
				} else if (state.get(FACING) == Direction.NORTH) {
					return SHAPE_TOP_N;
				} else {
					return SHAPE_TOP_S;
				}
			case BOTTOM:
				if (state.get(FACING) == Direction.EAST) {
					return SHAPE_BOTTOM_E;
				} else if (state.get(FACING) == Direction.WEST) {
					return SHAPE_BOTTOM_W;
				} else if (state.get(FACING) == Direction.NORTH) {
					return SHAPE_BOTTOM_N;
				} else {
					return SHAPE_BOTTOM_S;
				}
			default:
				return VoxelShapes.fullCube();
		}
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		BlockState blockState = (item instanceof BlockItem ? (Block) CONTENT_TO_POTTED.getOrDefault(((BlockItem) item).getBlock(), Blocks.AIR) : Blocks.AIR).getDefaultState();
		boolean bl = blockState.isOf(Blocks.AIR);
		boolean bl2 = this.isEmpty();
		if (bl != bl2) {
			if (bl2) {
				world.setBlockState(pos, blockState.with(SIDE, state.get(SIDE)).with(FACING, state.get(FACING)), 3);
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
				}
			} else {
				ItemStack itemStack2 = new ItemStack(this.content);
				if (itemStack.isEmpty()) {
					player.setStackInHand(hand, itemStack2);
				} else if (!player.giveItemStack(itemStack2)) {
					player.dropItem(itemStack2, false);
				}
				
				world.setBlockState(pos, ObjectRegistry.FLOWER_BOX.getDefaultState().with(SIDE, state.get(SIDE)).with(FACING, state.get(FACING)), 3);
			}
			
			world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			return ActionResult.success(world.isClient);
		} else {
			return ActionResult.CONSUME;
		}
	}
	
	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return this.isEmpty() ? super.getPickStack(world, pos, state) : new ItemStack(this.content);
	}
	
	private boolean isEmpty() {
		return this.content == Blocks.AIR;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	
	public Block getContent() {
		return this.content;
	}
	
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}
	
	public static VoxelShape makeShapeCenterNS() { // Center, North, South
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0, 0.28125, 1, 0.34375, 0.5), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.28125, 0.9375, 0.34375, 0.34375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.65625, 0.9375, 0.34375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0, 0.5, 1, 0.34375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.28125, 0.0625, 0.34375, 0.5), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.5, 0.0625, 0.34375, 0.71875), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeCenterEW() { // Center, East, West
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5, 0, 0.9375, 0.71875, 0.34375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.65625, 0, 0.0625, 0.71875, 0.34375, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0, 0.0625, 0.34375, 0.34375, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0, 0.9375, 0.5, 0.34375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5, 0, 0, 0.71875, 0.34375, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0, 0, 0.5, 0.34375, 0.0625), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeBottomS() { // Bottom, South
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0, 0.5625, 1, 0.34375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.5625, 0.9375, 0.34375, 0.625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.9375, 0.9375, 0.34375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0, 0.78125, 1, 0.34375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.5625, 0.0625, 0.34375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.78125, 0.0625, 0.34375, 1), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeBottomN() { // Bottom, North
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.21875, 0.0625, 0.34375, 0.4375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.375, 0.9375, 0.34375, 0.4375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0, 0.9375, 0.34375, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0, 0.0625, 0.34375, 0.21875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0, 0.21875, 1, 0.34375, 0.4375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0, 0, 1, 0.34375, 0.21875), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeBottomE() { // Bottom, East
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5625, 0, 0, 0.78125, 0.34375, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5625, 0, 0.0625, 0.625, 0.34375, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0, 0.0625, 1, 0.34375, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.78125, 0, 0, 1, 0.34375, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5625, 0, 0.9375, 0.78125, 0.34375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.78125, 0, 0.9375, 1, 0.34375, 1), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeBottomW() { // Bottom, West
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0, 0.9375, 0.4375, 0.34375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.375, 0, 0.0625, 0.4375, 0.34375, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.0625, 0.0625, 0.34375, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.9375, 0.21875, 0.34375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0, 0, 0.4375, 0.34375, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0, 0.21875, 0.34375, 0.0625), BooleanBiFunction.OR);

		return shape;
	}
	
	public static VoxelShape makeShapeTopS() { // Top, South
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0.625, 0.5625, 1, 0.96875, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.625, 0.5625, 0.9375, 0.96875, 0.625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.625, 0.9375, 0.9375, 0.96875, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0.625, 0.78125, 1, 0.96875, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.625, 0.5625, 0.0625, 0.96875, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.625, 0.78125, 0.0625, 0.96875, 1), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeTopN() { // Top, North
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.625, 0.21875, 0.0625, 0.96875, 0.4375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.625, 0.375, 0.9375, 0.96875, 0.4375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.625, 0, 0.9375, 0.96875, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.625, 0, 0.0625, 0.96875, 0.21875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0.625, 0.21875, 1, 0.96875, 0.4375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0.625, 0, 1, 0.96875, 0.21875), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeTopE() { // Top, East
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5625, 0.625, 0, 0.78125, 0.96875, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5625, 0.625, 0.0625, 0.625, 0.96875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0.625, 0.0625, 1, 0.96875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.78125, 0.625, 0, 1, 0.96875, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5625, 0.625, 0.9375, 0.78125, 0.96875, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.78125, 0.625, 0.9375, 1, 0.96875, 1), BooleanBiFunction.OR);

		return shape;
	}
	
	public static VoxelShape makeShapeTopW() { // Top, West
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.625, 0.9375, 0.4375, 0.96875, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.375, 0.625, 0.0625, 0.4375, 0.96875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.625, 0.0625, 0.0625, 0.96875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.625, 0.9375, 0.21875, 0.96875, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.625, 0, 0.4375, 0.96875, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.625, 0, 0.21875, 0.96875, 0.0625), BooleanBiFunction.OR);

		return shape;
	}
}