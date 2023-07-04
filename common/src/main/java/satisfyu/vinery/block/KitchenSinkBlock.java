package satisfyu.vinery.block;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.registry.VinerySoundEvents;
import satisfyu.vinery.util.GeneralUtil;
import satisfyu.vinery.util.VineryTags;

import java.util.HashMap;
import java.util.List;
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
		if (itemStack.is(VineryTags.FAUCET) && !state.getValue(HAS_FAUCET)) {
			world.setBlock(pos, state.setValue(HAS_FAUCET, true), Block.UPDATE_ALL);
			if (!player.isCreative())
				itemStack.shrink(1);
			return InteractionResult.SUCCESS;
		} else if (itemStack.isEmpty() && state.getValue(HAS_FAUCET) && !state.getValue(FILLED)) {
			world.setBlock(pos, state.setValue(HAS_FAUCET, true).setValue(FILLED, true), Block.UPDATE_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), VinerySoundEvents.BLOCK_FAUCET.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
			return InteractionResult.SUCCESS;
		} else if ((item == Items.WATER_BUCKET || item == Items.GLASS_BOTTLE) && !state.getValue(FILLED)) {
			world.setBlock(pos, state.setValue(HAS_FAUCET, state.getValue(HAS_FAUCET)).setValue(FILLED, true), Block.UPDATE_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
			if (!player.isCreative()) {
				if (item == Items.WATER_BUCKET) {
					itemStack.shrink(1);
					player.addItem(new ItemStack(Items.BUCKET));
				} else {
					itemStack.shrink(1);
					player.addItem(new ItemStack(Items.GLASS_BOTTLE));
				}
			}
			return InteractionResult.SUCCESS;
		} else if ((item == Items.BUCKET || item == Items.GLASS_BOTTLE) && state.getValue(FILLED)) {
			world.setBlock(pos, state.setValue(FILLED, false), Block.UPDATE_ALL);
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
			if (!player.isCreative()) {
				if (item == Items.BUCKET) {
					itemStack.shrink(1);
					player.addItem(new ItemStack(Items.WATER_BUCKET));
				} else {
					itemStack.shrink(1);
					player.addItem(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
				}
			}
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

	private static final Supplier<VoxelShape> voxelShapeWithFaucetSupplier = () -> {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.125, 1, 0.75, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3984375, 1.171875, 0.84375, 0.4296875, 1.234375, 0.90625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.4296875, 1.375, 0.625, 0.5546875, 1.5, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.4296875, 1.3125, 0.5, 0.5546875, 1.5, 0.625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.4296875, 1.0625, 0.8125, 0.5546875, 1.375, 0.9375), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3828125, 1.25, 0.453125, 0.6015625, 1.3125, 0.671875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3671875, 1, 0.75, 0.6171875, 1.0625, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3671875, 1.125, 0.78125, 0.3984375, 1.3125, 0.96875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.75, 0, 1, 1, 0.1875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.75, 0.75, 1, 1, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.75, 0.1875, 0.1859375, 1, 0.75), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.8125, 0.75, 0.1875, 1, 1, 0.75), BooleanOp.OR);
		return shape;
	};


	public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
			map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
		}
	});

	public static final Map<Direction, VoxelShape> SHAPE_WITH_FAUCET = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
			map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeWithFaucetSupplier.get()));
		}
	});

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		if (state.getValue(HAS_FAUCET)) {
			return SHAPE_WITH_FAUCET.get(state.getValue(FACING));
		} else {
			return SHAPE.get(state.getValue(FACING));
		}
	}

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

	@Override
	public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
		tooltip.add(Component.translatable("block.vinery.deprecated.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

	}
}
