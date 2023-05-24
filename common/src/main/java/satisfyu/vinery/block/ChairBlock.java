package satisfyu.vinery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfyu.vinery.block.entity.chair.ChairUtil;

public class ChairBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	private static final VoxelShape[] SHAPE = {
			Block.box(3, 10, 11, 13, 22, 13), Block.box(11, 10, 3, 13, 22, 13), Block.box(3, 10, 3, 13, 22, 5),
			Block.box(3, 10, 3, 5, 22, 13)
	};

	public ChairBlock(Properties settings) {
		super(settings);
	}

	protected static VoxelShape SINGLE_SHAPE() {
		VoxelShape top = Block.box(3.0, 9.0, 3.0, 13.0, 10.0, 13.0);
		VoxelShape leg1 = Block.box(3.0, 0.0, 3.0, 5.0, 9.0, 5.0);
		VoxelShape leg2 = Block.box(3.0, 0.0, 11.0, 5.0, 9.0, 13.0);
		VoxelShape leg3 = Block.box(11.0, 0.0, 11.0, 13.0, 9.0, 13.0);
		VoxelShape leg4 = Block.box(11.0, 0.0, 3.0, 13.0, 9.0, 5.0);
		return Shapes.or(top, leg1, leg2, leg3, leg4);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		VoxelShape s = SINGLE_SHAPE();
		switch (state.getValue(FACING)) {
			default: {
				return Shapes.or(s, SHAPE[0]);
			}
			case WEST: {
				return Shapes.or(s, SHAPE[1]);
			}
			case SOUTH: {
				return Shapes.or(s, SHAPE[2]);
			}
			case EAST:
		}
		return Shapes.or(s, SHAPE[3]);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return ChairUtil.onUse(world, player, hand, hit, 0.1);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		super.onRemove(state, world, pos, newState, moved);
		ChairUtil.onStateReplaced(world, pos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
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
