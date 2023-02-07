package satisfyu.vinery.block;

import net.minecraft.block.ShapeContext;
import net.minecraft.world.BlockView;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VinerySoundEvents;
import satisfyu.vinery.util.VineryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class KitchenSinkBlock extends Block {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final BooleanProperty FILLED = BooleanProperty.of("filled");
	public static final BooleanProperty HAS_FAUCET = BooleanProperty.of("has_faucet");

	public KitchenSinkBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(HAS_FAUCET, false).with(FILLED, false));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) return ActionResult.SUCCESS;
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (item == ObjectRegistry.FAUCET && !state.get(HAS_FAUCET)) {
			world.setBlockState(pos, state.with(HAS_FAUCET, true), Block.NOTIFY_ALL);
			if (!player.isCreative())
				itemStack.decrement(1);
			return ActionResult.SUCCESS;
		} else if (itemStack.isEmpty() && state.get(HAS_FAUCET) && !state.get(FILLED)) {
			world.setBlockState(pos, state.with(HAS_FAUCET, true).with(FILLED, true), Block.NOTIFY_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), VinerySoundEvents.BLOCK_FAUCET, SoundCategory.BLOCKS, 1.0f, 1.0f);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
			return ActionResult.SUCCESS;
		} else if (item == Items.WATER_BUCKET && !state.get(FILLED)) {
			world.setBlockState(pos, state.with(HAS_FAUCET, state.get(HAS_FAUCET)).with(FILLED, true), Block.NOTIFY_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
			if (!player.isCreative()) {
				itemStack.decrement(1);
				player.giveItemStack(new ItemStack(Items.BUCKET));
			}
			return ActionResult.SUCCESS;
		} else if (item == Items.BUCKET && state.get(FILLED)) {
			world.setBlockState(pos, state.with(FILLED, false), Block.NOTIFY_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
			if (!player.isCreative()) {
				itemStack.decrement(1);
				player.giveItemStack(new ItemStack(Items.WATER_BUCKET));
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.125, 1, 0.75, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.75, 0, 1, 1, 0.1875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.75, 0.75, 1, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.75, 0.1875, 0.1859375, 1, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.75, 0.1875, 1, 1, 0.75), BooleanBiFunction.OR);
		return shape;
	};

	public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
			map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
		}
	});

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE.get(state.get(FACING));
	}



	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, FILLED, HAS_FAUCET);
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