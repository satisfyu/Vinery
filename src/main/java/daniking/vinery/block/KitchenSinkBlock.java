package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VinerySoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
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
import org.jetbrains.annotations.Nullable;

public class KitchenSinkBlock extends Block {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final BooleanProperty FILLED = BooleanProperty.of("filled");
	public static final BooleanProperty HAS_FAUCET = BooleanProperty.of("has_faucet");
	
	protected static final VoxelShape SHAPE_N = makeShapeN();
	protected static final VoxelShape SHAPE_S = makeShapeS();
	protected static final VoxelShape SHAPE_E = makeShapeE();
	protected static final VoxelShape SHAPE_W = makeShapeW();
	
	public KitchenSinkBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(FILLED, false).with(HAS_FAUCET, false));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) return ActionResult.SUCCESS;
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (item == ObjectRegistry.FAUCET && !state.get(HAS_FAUCET)) {
			world.setBlockState(pos, state.with(HAS_FAUCET, true));
			itemStack.decrement(1);
			return ActionResult.SUCCESS;
		} else if (itemStack.isEmpty() && state.get(HAS_FAUCET) && !state.get(FILLED)) {
			world.setBlockState(pos, state.with(FILLED, true));
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), VinerySoundEvents.BLOCK_FAUCET, SoundCategory.BLOCKS, 1.0f, 1.0f);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
			return ActionResult.SUCCESS;
		} else if (item == Items.WATER_BUCKET && !state.get(FILLED)) {
			world.setBlockState(pos, state.with(FILLED, true));
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
			player.setStackInHand(hand, new ItemStack(Items.BUCKET));
			return ActionResult.SUCCESS;
		} else if (item == Items.BUCKET && state.get(FILLED)) {
			world.setBlockState(pos, state.with(FILLED, false));
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
			player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
			return ActionResult.SUCCESS;
		}
		
//		CauldronBehavior cauldronBehavior = ((AbstractCauldronBlockAccessor) Blocks.CAULDRON).getBehaviorMap().get(item);
//		if (cauldronBehavior != null) {
//			return cauldronBehavior.interact(state, world, pos, player, hand, itemStack);
//		}
		return ActionResult.PASS;
	}
	
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, FILLED, HAS_FAUCET);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction facing = state.get(FACING);
		switch (facing) {
			case NORTH:
				return SHAPE_N;
			case SOUTH:
				return SHAPE_S;
			case EAST:
				return SHAPE_E;
			case WEST:
				return SHAPE_W;
			default:
				return SHAPE_N;
		}
	}
	
	public static VoxelShape makeShapeE() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0.75, 0, 0.8125, 1, 0.1859375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.75, 0, 0.3125, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0.75, 0.8125, 0.8125, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.75, 0, 0.9375, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0, 0.8125, 0.75, 1), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeW() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.75, 0.8140625, 0.71875, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.75, 0, 1.03125, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.75, 0, 0.71875, 1, 0.1875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0.75, 0, 0.21875, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0, 0, 1.03125, 0.75, 1), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeN() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.015625, 0.75, 0.203125, 0.20156249999999998, 1, 0.703125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.015625, 0.75, 0.703125, 1.015625, 1, 1.015625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.828125, 0.75, 0.203125, 1.015625, 1, 0.703125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.015625, 0.75, 0.078125, 1.015625, 1, 0.203125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.015625, 0, 0.203125, 1.015625, 0.75, 1.015625), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeS() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8296875, 0.75, 0.296875, 1.015625, 1, 0.796875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.015625, 0.75, -0.015625, 1.015625, 1, 0.296875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.015625, 0.75, 0.296875, 0.203125, 1, 0.796875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.015625, 0.75, 0.796875, 1.015625, 1, 0.921875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.015625, 0, -0.015625, 1.015625, 0.75, 0.796875), BooleanBiFunction.OR);
		
		return shape;
	}
}