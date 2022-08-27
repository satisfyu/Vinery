package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.3125, 0.09375, 0.3125, 0.6875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.90625, 0, 0.3125, 0.9375, 0.3125, 0.6875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0, 0.3125, 0.90625, 0.3125, 0.34375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0.03125, 0.34375, 0.90625, 0.09375, 0.65625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0, 0.65625, 0.90625, 0.3125, 0.6875), BooleanBiFunction.OR);

		return shape;
	}
	
	private static VoxelShape makeShapeE() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0, 0.0625, 0.6875, 0.3125, 0.09375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0, 0.90625, 0.6875, 0.3125, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.65625, 0, 0.09375, 0.6875, 0.3125, 0.90625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.34375, 0.03125, 0.09375, 0.65625, 0.09375, 0.90625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0, 0.09375, 0.34375, 0.3125, 0.90625), BooleanBiFunction.OR);

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
	
}