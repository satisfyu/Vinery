package satisfyu.vinery.block;

import satisfyu.vinery.block.entity.FlowerBoxBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.GeneralUtil;
import satisfyu.vinery.util.VineryTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FlowerBoxBlock extends FacingBlock implements EntityBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.5625, 1, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.5625, 0.0625, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.5625, 0.9375, 0.375, 0.625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.625, 0.9375, 0.3125, 0.9375), BooleanOp.OR);        return shape;
	};

	public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
			map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
		}
	});

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE.get(state.getValue(FACING));
	}

	public FlowerBoxBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		}

		FlowerBoxBlockEntity blockEntity = (FlowerBoxBlockEntity) world.getBlockEntity(pos);
		if (blockEntity == null || player.isShiftKeyDown()) {
			return InteractionResult.PASS;
		}

		Direction facing = state.getValue(FACING);
		boolean left = (facing.getAxis() == Direction.Axis.X) ? (hit.getLocation().z - pos.getZ() > 0.5D) : (hit.getLocation().x - pos.getX() > 0.5D);
		left = (facing == Direction.NORTH || facing == Direction.WEST) != left;

		ItemStack handStack = player.getItemInHand(hand);
		if (handStack.isEmpty()) {
			ItemStack flowerStack = blockEntity.removeFlower(left ? 0 : 1);
			if (!flowerStack.isEmpty()) {
				player.addItem(flowerStack);
				return InteractionResult.SUCCESS;
			}
			flowerStack = blockEntity.removeFlower(left ? 1 : 0);
			if (!flowerStack.isEmpty()) {
				player.addItem(flowerStack);
				return InteractionResult.SUCCESS;
			}
		} else if (handStack.is(VineryTags.SMALL_FLOWER)) {
			if (blockEntity.isSlotEmpty(left ? 0 : 1)) {
				blockEntity.addFlower(new ItemStack(handStack.getItem()), left ? 0 : 1);
				if (!player.isCreative()) {
					handStack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
			if (blockEntity.isSlotEmpty(left ? 1 : 0)) {
				blockEntity.addFlower(new ItemStack(handStack.getItem()), left ? 1 : 0);
				if (!player.isCreative()) {
					handStack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}

		return super.use(state, world, pos, player, hand, hit);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.IGNORE;
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof FlowerBoxBlockEntity be) {
				for(Item stack : be.getFlowers()){
					Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(stack));
				}
				world.updateNeighbourForOutputSignal(pos,this);
			}
			super.onRemove(state, world, pos, newState, moved);
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FlowerBoxBlockEntity(pos, state);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
		tooltip.add(Component.translatable("block.vinery.canbeplaced.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
	}
}

