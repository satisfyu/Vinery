package daniking.vinery.block;

import daniking.vinery.block.entity.GeckoStorageBlockEntity;
import daniking.vinery.item.DrinkBlockItem;
import daniking.vinery.util.VineryUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DisplayRackBlock extends WineRackBlock {
	
	private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.25, 0.5, 1, 0.3125, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.1875, 0.625, 0.1875, 0.25, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.1875, 0.625, 0.875, 0.25, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.0625, 0.9375, 0.1875, 0.25, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.0625, 0.9375, 0.875, 0.25, 1), BooleanBiFunction.OR);
		
		return shape;
	};
	
	public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Type.HORIZONTAL) {
			map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
		}
	});
	
	public DisplayRackBlock(Settings settings) {
		super(settings, 2, 4);
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient)
			return ActionResult.SUCCESS;
		final ItemStack stack = player.getStackInHand(hand);
		GeckoStorageBlockEntity blockEntity = (GeckoStorageBlockEntity) world.getBlockEntity(pos);
		if (blockEntity != null) {
			if (!stack.isEmpty()) {
				if (blockEntity.getNonEmptySlotCount() < maxStorage) {
					blockEntity.addItemStack(new ItemStack((stack.getItem())));
					stack.decrement(1);
					player.setStackInHand(hand, stack);
					((ServerPlayerEntity) player).networkHandler.sendPacket(blockEntity.toUpdatePacket());
					return ActionResult.SUCCESS;
				}
			} else if (player.isSneaking() && blockEntity.getNonEmptySlotCount() > 0) {
				player.setStackInHand(hand, blockEntity.getFirstNonEmptyStack().copy());
				blockEntity.removeFirstNonEmptyStack();
				((ServerPlayerEntity) player).networkHandler.sendPacket(blockEntity.toUpdatePacket());
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE.get(state.get(FACING));
	}
}