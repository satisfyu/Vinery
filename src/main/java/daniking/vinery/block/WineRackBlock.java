package daniking.vinery.block;

import daniking.vinery.block.entity.GeckoStorageBlockEntity;
import daniking.vinery.item.DrinkBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WineRackBlock extends BlockWithEntity {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	protected final int maxStorage;
	private final int modelPostFix;

	public WineRackBlock(Settings settings, int maxStorage, int modelPostFix) {
		super(settings);
		this.maxStorage = maxStorage;
		this.modelPostFix = modelPostFix;
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient)
			return ActionResult.SUCCESS;
		final ItemStack stack = player.getStackInHand(hand);
		GeckoStorageBlockEntity blockEntity = (GeckoStorageBlockEntity) world.getBlockEntity(pos);
		if (blockEntity != null) {
			if (stack.getItem() instanceof DrinkBlockItem) {
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

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(this.asItem()));
		GeckoStorageBlockEntity blockEntity = (GeckoStorageBlockEntity) builder.get(LootContextParameters.BLOCK_ENTITY);
		if (blockEntity != null) {
			list.addAll(blockEntity.getInvStackList());
		}
		return list;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new GeckoStorageBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	public int getModelPostFix() {
		return modelPostFix;
	}

	@Override
	public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(Text.translatable("block.vinery.fermentationbarrelblock.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
			if (Screen.hasShiftDown()) {
		tooltip.add(Text.translatable("block.vinery.winerack.tooltip.shift_1"));
		tooltip.add(Text.translatable("block.vinery.winerack.tooltip.shift_2"));
	} else {
		tooltip.add(Text.translatable("block.vinery.breadblock.tooltip.tooltip_shift"));
		}
	}
}