package daniking.vinery.block;

import daniking.vinery.util.EnumTallFlower;
import net.minecraft.ChatFormatting;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlowerPotBlock extends Block {
	protected static final VoxelShape SHAPE = makeShape();
	
	public static final EnumProperty<EnumTallFlower> CONTENT = EnumProperty.create("content", EnumTallFlower.class);
	
	public FlowerPotBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(CONTENT, EnumTallFlower.NONE));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CONTENT);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item item = itemStack.getItem();
		if (item instanceof BlockItem blockItem) {
			Block block = blockItem.getBlock();
			if (block instanceof DoublePlantBlock) {
				EnumTallFlower flower = EnumTallFlower.NONE;
				for (EnumTallFlower f : EnumTallFlower.values()) {
					if (f.getFlower() == block) {
						flower = f;
						break;
					}
				}
				if (flower == EnumTallFlower.NONE) {
					return InteractionResult.FAIL;
				}
				world.setBlockAndUpdate(pos, state.setValue(CONTENT, flower));
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
				world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				return InteractionResult.sidedSuccess(world.isClientSide);
			}
		} else if (player.isShiftKeyDown() && !isEmpty(state)) {
			player.addItem(new ItemStack(state.getValue(CONTENT).getFlower()));
			world.setBlockAndUpdate(pos, state.setValue(CONTENT, EnumTallFlower.NONE));
			world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return this.isEmpty(state) ? super.getCloneItemStack(world, pos, state) : new ItemStack(state.getValue(CONTENT).getFlower());
	}
	
	private boolean isEmpty(BlockState state) {
		return state.getValue(CONTENT) == EnumTallFlower.NONE;
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
	}
	
	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(this));
		Optional.of(state.getValue(CONTENT)).filter(a -> a != EnumTallFlower.NONE).map(EnumTallFlower::getFlower).map(ItemStack::new).ifPresent(list::add);
		return list;
	}

	public static VoxelShape makeShape() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.78125, 0.421875, 0.21875, 0.875, 0.609375, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.21875, 0, 0.21875, 0.78125, 0.46875, 0.78125), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.421875, 0.125, 0.875, 0.609375, 0.21875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.421875, 0.78125, 0.875, 0.609375, 0.875), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.421875, 0.21875, 0.21875, 0.609375, 0.78125), BooleanOp.OR);
		
		return shape;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
		tooltip.add(Component.translatable("block.vinery.canbeplaced.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
	}
}