package satisfyu.vinery.block;

import net.minecraft.block.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import satisfyu.vinery.block.entity.WinePressBlockEntity;
import satisfyu.vinery.registry.VineryBlockEntityTypes;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class WinePressBlock extends BlockWithEntity {

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public WinePressBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof WinePressBlockEntity) {
				ItemScatterer.spawn(world, pos, (WinePressBlockEntity)blockEntity);
				world.updateComparators(pos,this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
			if (screenHandlerFactory != null) {
				player.openHandledScreen(screenHandlerFactory);
			}
		}
		return ActionResult.SUCCESS;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new WinePressBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, VineryBlockEntityTypes.WINE_PRESS_BLOCK_ENTITY, WinePressBlockEntity::tick);
	}


	protected static final VoxelShape SHAPE_WE = makeShapeWE();
	protected static final VoxelShape SHAPE_NS = makeShapeNS();

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(FACING) == Direction.SOUTH || state.get(FACING) == Direction.NORTH ? SHAPE_WE : SHAPE_NS;
	}


	public static VoxelShape makeShapeWE() {
		VoxelShape shape = VoxelShapes.empty();
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.09375, 0.125, 0.125, 0.90625), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 0, 0.09375, 1, 0.125, 0.90625), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.46875, 1.46875, 0.475, 0.53125, 1.90625, 0.5375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.46875, 1.03125, 0.475, 0.53125, 1.46875, 0.5375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.375, 1.90625, 0.375, 0.625, 1.96875, 0.625), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.125, 0.40625, 0.125, 1.0625, 0.59375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 0.125, 0.40625, 1, 1.0625, 0.59375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.8125, 0.8125, 0.5625, 0.875), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.125, 0.8125, 0.5625, 0.1875), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.1875, 0.8125, 1.1875, 0.8125), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.125, 0.1875, 0.5625, 0.875), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.4375, 0.125, 0.875, 0.5625, 0.875), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 1.0625, 0.40625, 0.125, 1.5, 0.59375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 1.0625, 0.40625, 1, 1.5, 0.59375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.3125, 0.46875, 0.875, 0.4375, 0.53125), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 1.375, 0.40625, 0.875, 1.5, 0.59375), BooleanBiFunction.OR);
		return shape;
	}

	public static VoxelShape makeShapeNS() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0, 0, 0.90625, 0.125, 0.125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.15625, 0, 0.875, 0.90625, 0.125, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4625, 1.46875, 0.46875, 0.525, 1.90625, 0.53125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4625, 1.03125, 0.46875, 0.525, 1.46875, 0.53125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.375, 1.90625, 0.375, 0.625, 1.96875, 0.625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 0.125, 0, 0.59375, 1.0625, 0.125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 0.125, 0.875, 0.59375, 1.0625, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.1875, 0.1875, 0.5625, 0.8125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.4375, 0.1875, 0.875, 0.5625, 0.8125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.1875, 0.8125, 1.1875, 0.8125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.125, 0.875, 0.5625, 0.1875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.8125, 0.875, 0.5625, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 1.0625, 0, 0.59375, 1.5, 0.125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 1.0625, 0.875, 0.59375, 1.5, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.46875, 0.3125, 0.125, 0.53125, 0.4375, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 1.375, 0.125, 0.59375, 1.5, 0.875), BooleanBiFunction.OR);

		return shape;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(Text.translatable("block.vinery.press.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
	}
}