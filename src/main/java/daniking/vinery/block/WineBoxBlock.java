package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WineBoxBlock extends Block {
	private static final VoxelShape SHAPE_S = makeShapeS();
	private static final VoxelShape SHAPE_E = makeShapeE();
	
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final BooleanProperty OPEN = Properties.OPEN;
	public static final BooleanProperty HAS_BOTTLE = BooleanProperty.of("has_bottle");
	
	public WineBoxBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false).with(HAS_BOTTLE, false));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) return ActionResult.SUCCESS;
		final ItemStack stack = player.getStackInHand(hand);
		if (player.isSneaking()) {
			world.setBlockState(pos, state.with(OPEN, !state.get(OPEN)), Block.NOTIFY_ALL);
		} else if (stack.getItem() == ObjectRegistry.KING_DANIS_WINE.asItem() && !state.get(HAS_BOTTLE) && state.get(OPEN)) {
			world.setBlockState(pos, state.with(HAS_BOTTLE, true), Block.NOTIFY_ALL);
			if (!player.isCreative()) stack.decrement(1);
			return ActionResult.SUCCESS;
		} else if (state.get(HAS_BOTTLE) && state.get(OPEN)) {
			world.setBlockState(pos, state.with(HAS_BOTTLE, false), Block.NOTIFY_ALL);
			player.giveItemStack(new ItemStack(ObjectRegistry.KING_DANIS_WINE));
			return ActionResult.SUCCESS;
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}


	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, OPEN, HAS_BOTTLE);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(FACING)) {
			case WEST, EAST -> SHAPE_E;
			default -> SHAPE_S;
		};
	}
	
	private static VoxelShape makeShapeS() {
		VoxelShape shape = VoxelShapes.empty();
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.28125, 0.9375, 0.3125, 0.34375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.65625, 0.9375, 0.3125, 0.71875), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 0, 0.34375, 0.9375, 0.3125, 0.65625), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.34375, 0.125, 0.3125, 0.65625), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0, 0.34375, 0.875, 0.125, 0.65625), BooleanBiFunction.OR);
		return shape;
	}
	
	private static VoxelShape makeShapeE() {
		VoxelShape shape = VoxelShapes.empty();
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.65625, 0, 0.0625, 0.71875, 0.3125, 0.9375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0, 0.0625, 0.34375, 0.3125, 0.9375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.34375, 0, 0.875, 0.65625, 0.3125, 0.9375), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.34375, 0, 0.0625, 0.65625, 0.3125, 0.125), BooleanBiFunction.OR);
	shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.34375, 0, 0.125, 0.65625, 0.125, 0.875), BooleanBiFunction.OR);
		return shape;
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
	public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(Text.translatable("block.vinery.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
	}
	
}