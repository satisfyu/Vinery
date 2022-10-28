package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static java.util.OptionalInt.of;

public class WineRackBlock extends Block {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final IntProperty STAGE = IntProperty.of("stage", 0, 16);
	
	private final int MAX_STAGE;
	
	public WineRackBlock(Settings settings, int max_stage) {
		super(settings);
		MAX_STAGE = max_stage;
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(STAGE, 0));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient)
			return ActionResult.SUCCESS;
		final ItemStack stack = player.getStackInHand(hand);
		if (player.isSneaking() && state.get(STAGE) > 0) {
			world.setBlockState(pos, state.with(STAGE, state.get(STAGE) - 1), 3);
			player.giveItemStack(new ItemStack(ObjectRegistry.CHERRY_WINE));
		} else if (stack.getItem() == ObjectRegistry.CHERRY_WINE.asItem() && state.get(STAGE) < MAX_STAGE) {
			world.setBlockState(pos, state.with(STAGE, state.get(STAGE) + 1), 3);
			if (!player.isCreative())
				stack.decrement(1);
			return ActionResult.SUCCESS;
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

		int amount = MathHelper.clamp(state.get(STAGE), 0, MAX_STAGE);

		if(amount > 0) list.add(new ItemStack(ObjectRegistry.CHERRY_WINE, amount));
		return list;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, STAGE);
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