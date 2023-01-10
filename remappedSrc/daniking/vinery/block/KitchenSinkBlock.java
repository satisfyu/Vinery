package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VinerySoundEvents;
import daniking.vinery.util.VineryUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class KitchenSinkBlock extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty FILLED = BooleanProperty.create("filled");
	public static final BooleanProperty HAS_FAUCET = BooleanProperty.create("has_faucet");

	public KitchenSinkBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HAS_FAUCET, false).setValue(FILLED, false));
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide) return InteractionResult.SUCCESS;
		ItemStack itemStack = player.getItemInHand(hand);
		Item item = itemStack.getItem();
		if (item == ObjectRegistry.FAUCET && !state.getValue(HAS_FAUCET)) {
			world.setBlock(pos, state.setValue(HAS_FAUCET, true), Block.UPDATE_ALL);
			if (!player.isCreative())
				itemStack.shrink(1);

			return InteractionResult.SUCCESS;
		} else if (itemStack.isEmpty() && state.getValue(HAS_FAUCET) && !state.getValue(FILLED)) {
			world.setBlock(pos, state.setValue(HAS_FAUCET, true).setValue(FILLED, true), Block.UPDATE_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), VinerySoundEvents.BLOCK_FAUCET, SoundSource.BLOCKS, 1.0f, 1.0f);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
			return InteractionResult.SUCCESS;
		} else if (item == Items.WATER_BUCKET && !state.getValue(FILLED)) {
			world.setBlock(pos, state.setValue(HAS_FAUCET, state.getValue(HAS_FAUCET)).setValue(FILLED, true), Block.UPDATE_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
			if (!player.isCreative())
				player.setItemInHand(hand, new ItemStack(Items.BUCKET));

			return InteractionResult.SUCCESS;
		} else if (item == Items.BUCKET && state.getValue(FILLED)) {
			world.setBlock(pos, state.setValue(FILLED, false), Block.UPDATE_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
			if (!player.isCreative())
				player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));

			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

	private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.125, 1, 0.75, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.75, 0, 1, 1, 0.1875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.75, 0.75, 1, 1, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.75, 0.1875, 0.1859375, 1, 0.75), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.8125, 0.75, 0.1875, 1, 1, 0.75), BooleanOp.OR);

		return shape;
	};

	public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
			map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
		}
	});

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, FILLED, HAS_FAUCET);
	}
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
}