package daniking.vinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class BigTableBlock extends Block {
	private static final VoxelShape SHAPE_S = makeShapeS();
	private static final VoxelShape SHAPE_N = makeShapeN();
	private static final VoxelShape SHAPE_E = makeShapeE();
	private static final VoxelShape SHAPE_W = makeShapeW();
	
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	
	public BigTableBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
	}
	
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(FACING).getOpposite()) {
			case NORTH -> SHAPE_N;
			case EAST -> SHAPE_E;
			case WEST -> SHAPE_W;
			case SOUTH -> SHAPE_S;
			default -> SHAPE_S;
		};
	}
	
	private static VoxelShape makeShapeS() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.9375, 0, 0.0625, 0.9375, 0.875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.9375, 0.9375, 0.0625, 0.9375, 0.96875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-1, 0.875, 0.9375, 1, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-1, 0.875, 0, 1, 1, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0.875, 0.0625, 1, 1, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-1, 0.875, 0.0625, -0.9375, 1, 0.9375), BooleanBiFunction.OR);

		return shape;
	}
	
	private static VoxelShape makeShapeN() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 1.9375, 0.875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.9375, 0.0625, 1.9375, 0.96875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.875, 0, 2, 1, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.875, 0.9375, 2, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.875, 0.0625, 0.0625, 1, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(1.9375, 0.875, 0.0625, 2, 1, 0.9375), BooleanBiFunction.OR);
		
		return shape;
	}
	
	private static VoxelShape makeShapeE() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.875, 1.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.9375, 0.0625, 0.9375, 0.96875, 1.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0.875, 0, 1, 1, 2), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.875, 0, 0.0625, 1, 2), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.875, 0, 0.9375, 1, 0.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.875, 1.9375, 0.9375, 1, 2), BooleanBiFunction.OR);

		return shape;
	}
	
	private static VoxelShape makeShapeW() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, -0.9375, 0.9375, 0.875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.9375, -0.9375, 0.9375, 0.96875, 0.9375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.875, -1, 0.0625, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.9375, 0.875, -1, 1, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.875, 0.9375, 0.9375, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.875, -1, 0.9375, 1, -0.9375), BooleanBiFunction.OR);

		return shape;
	}
	
}