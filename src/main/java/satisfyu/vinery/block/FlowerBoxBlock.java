package satisfyu.vinery.block;

import com.google.common.collect.Maps;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import satisfyu.vinery.block.entity.FlowerBoxBlockEntity;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.EnumBlockSide;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
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
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.GeneralUtil;
import satisfyu.vinery.util.VineryTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FlowerBoxBlock extends FacingBlock implements BlockEntityProvider {

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0, 0.5625, 1, 0.375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.5625, 0.0625, 0.375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.5625, 0.9375, 0.375, 0.625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.9375, 0.9375, 0.375, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.625, 0.9375, 0.3125, 0.9375), BooleanBiFunction.OR);        return shape;
	};

	public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
			map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
		}
	});

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE.get(state.get(FACING));
	}

	public FlowerBoxBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}

		FlowerBoxBlockEntity blockEntity = (FlowerBoxBlockEntity) world.getBlockEntity(pos);
		if (blockEntity == null || player.isSneaking()) {
			return ActionResult.PASS;
		}

		Direction facing = state.get(FACING);
		boolean left = (facing.getAxis() == Direction.Axis.X) ? (hit.getPos().z - pos.getZ() > 0.5D) : (hit.getPos().x - pos.getX() > 0.5D);
		left = (facing == Direction.NORTH || facing == Direction.WEST) != left;

		System.out.println(left);

		ItemStack handStack = player.getStackInHand(hand);
		if (handStack.isEmpty()) {
			ItemStack flowerStack = blockEntity.removeFlower(left ? 0 : 1);
			if (!flowerStack.isEmpty()) {
				player.giveItemStack(flowerStack);
				return ActionResult.SUCCESS;
			}
			flowerStack = blockEntity.removeFlower(left ? 1 : 0);
			if (!flowerStack.isEmpty()) {
				player.giveItemStack(flowerStack);
				return ActionResult.SUCCESS;
			}
		} else if (handStack.isIn(VineryTags.SMALL_FLOWER)) {
			if (blockEntity.isSlotEmpty(left ? 0 : 1)) {
				blockEntity.addFlower(new ItemStack(handStack.getItem()), left ? 0 : 1);
				if (!player.isCreative()) {
					handStack.decrement(1);
				}
				return ActionResult.SUCCESS;
			}
			if (blockEntity.isSlotEmpty(left ? 1 : 0)) {
				blockEntity.addFlower(new ItemStack(handStack.getItem()), left ? 1 : 0);
				if (!player.isCreative()) {
					handStack.decrement(1);
				}
				return ActionResult.SUCCESS;
			}
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.IGNORE;
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof FlowerBoxBlockEntity be) {
				for(Item stack : be.getFlowers()){
					ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(stack));
				}
				world.updateComparators(pos,this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FlowerBoxBlockEntity(pos, state);
	}

	@Override
	public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(Text.translatable("block.vinery.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
	}
}

