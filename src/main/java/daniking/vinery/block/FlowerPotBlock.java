package daniking.vinery.block;

import daniking.vinery.util.EnumTallFlower;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
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
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlowerPotBlock extends Block {
	protected static final VoxelShape SHAPE = makeShape();
	
	public static final EnumProperty<EnumTallFlower> CONTENT = EnumProperty.of("content", EnumTallFlower.class);
	
	public FlowerPotBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(CONTENT, EnumTallFlower.NONE));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(CONTENT);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (item instanceof BlockItem blockItem) {
			Block block = blockItem.getBlock();
			if (block instanceof TallPlantBlock) {
				EnumTallFlower flower = EnumTallFlower.NONE;
				for (EnumTallFlower f : EnumTallFlower.values()) {
					if (f.getFlower() == block) {
						flower = f;
						break;
					}
				}
				if (flower == EnumTallFlower.NONE) {
					return ActionResult.FAIL;
				}
				world.setBlockState(pos, state.with(CONTENT, flower));
				if (!player.getAbilities().creativeMode) {
					itemStack.decrement(1);
				}
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				return ActionResult.success(world.isClient);
			}
		} else if (player.isSneaking() && !isEmpty(state)) {
			player.giveItemStack(new ItemStack(state.get(CONTENT).getFlower()));
			world.setBlockState(pos, state.with(CONTENT, EnumTallFlower.NONE));
			world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	}
	
	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return this.isEmpty(state) ? super.getPickStack(world, pos, state) : new ItemStack(state.get(CONTENT).getFlower());
	}
	
	private boolean isEmpty(BlockState state) {
		return state.get(CONTENT) == EnumTallFlower.NONE;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(this));
		Optional.of(state.get(CONTENT)).filter(a -> a != EnumTallFlower.NONE).map(EnumTallFlower::getFlower).map(ItemStack::new).ifPresent(list::add);
		return list;
	}

	public static VoxelShape makeShape() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.78125, 0.421875, 0.21875, 0.875, 0.609375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0, 0.21875, 0.78125, 0.46875, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.421875, 0.125, 0.875, 0.609375, 0.21875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.421875, 0.78125, 0.875, 0.609375, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.421875, 0.21875, 0.21875, 0.609375, 0.78125), BooleanBiFunction.OR);
		
		return shape;
	}
}