package satisfyu.vinery.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.ItemScatterer;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.block.entity.FlowerPotBlockEntity;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VineryBlockEntityTypes;
import satisfyu.vinery.util.EnumTallFlower;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
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
import satisfyu.vinery.util.VineryTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FlowerPotBlock extends Block implements BlockEntityProvider{
	private static final VoxelShape SHAPE;

	private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.78125, 0.421875, 0.21875, 0.875, 0.609375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0, 0.21875, 0.78125, 0.46875, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.421875, 0.125, 0.875, 0.609375, 0.21875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.421875, 0.78125, 0.875, 0.609375, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.421875, 0.21875, 0.21875, 0.609375, 0.78125), BooleanBiFunction.OR);
		return shape;
	};
	
	public FlowerPotBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient || hand == Hand.OFF_HAND) return ActionResult.SUCCESS;
		FlowerPotBlockEntity be = (FlowerPotBlockEntity)world.getBlockEntity(pos);
		if (be == null || player.isSneaking()) return ActionResult.PASS;

		ItemStack handStack = player.getStackInHand(hand);
		Item flower = be.getFlower();

		if (handStack.isEmpty()) {
			if (flower != null) {
				player.giveItemStack(flower.getDefaultStack());
				be.setFlower(null);
				return ActionResult.SUCCESS;
			}
		} else if (fitInPot(handStack) && flower == null) {
			be.setFlower(handStack.getItem());
			if (!player.isCreative()) {
				handStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	public boolean fitInPot(ItemStack item) {
		return item.isIn(VineryTags.BIG_FLOWER);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof FlowerPotBlockEntity be) {
				Item flower = be.getFlower();
				if (flower != null) {
					ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), flower.getDefaultStack());
				}
				world.updateComparators(pos,this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.IGNORE;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FlowerPotBlockEntity(pos, state);
	}

	@Override
	public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(Text.translatable("block.vinery.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
	}

	static {
		SHAPE = voxelShapeSupplier.get();
	}
}