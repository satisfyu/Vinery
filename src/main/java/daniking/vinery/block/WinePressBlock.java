package daniking.vinery.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WinePressBlock extends FacingBlock {
	protected static final VoxelShape SHAPE_WE = makeShapeWE();
	protected static final VoxelShape SHAPE_NS = makeShapeNS();
	
	public WinePressBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(FACING) == Direction.SOUTH || state.get(FACING) == Direction.NORTH ? SHAPE_WE : SHAPE_NS;
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}
	
	public static VoxelShape makeShapeWE() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.4375, 0.71875, 0.75, 0.9, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.53125, 0.21875, 0.75, 0.59375, 0.25), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.53125, 0.21875, 0.71875, 0.59375, 0.25), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.78125, 0.75, 0.28125, 0.84375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.78125, 0.21875, 0.71875, 0.84375, 0.25), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.4375, 0.25, 0.71875, 0.9, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.78125, 0.71875, 0.78125, 0.84375, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.53125, 0.71875, 0.78125, 0.59375, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.53125, 0.21875, 0.78125, 0.59375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.78125, 0.21875, 0.78125, 0.84375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.4375, 0.25, 0.75, 0.9, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.99375, 0.25, 0.75, 1.0875, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.99375, 0.28125, 0.28125, 1.0875, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.99375, 0.25, 0.71875, 1.0875, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.99375, 0.71875, 0.75, 1.0875, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.9, 0.71875, 0.75, 0.99375, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.9, 0.25, 0.71875, 0.99375, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.9, 0.28125, 0.28125, 0.99375, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.9, 0.25, 0.75, 0.99375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.53125, 0.25, 0.25, 0.59375, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.53125, 0.28125, 0.25, 0.59375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.78125, 0.25, 0.25, 0.84375, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.78125, 0.28125, 0.25, 0.84375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.4375, 0.28125, 0.28125, 0.9, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.53125, 0.75, 0.78125, 0.59375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.53125, 0.75, 0.28125, 0.59375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.78125, 0.21875, 0.75, 0.84375, 0.25), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.78125, 0.75, 0.78125, 0.84375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.4375, 0.28125, 0.71875, 0.4375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 1.01875, 0.28125, 0.71875, 1.01875, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.46875, 0.3125, 0.09375, 0.53125, 0.4375, 0.90625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5, 0.4375, 0.8125, 0.875, 0.5625, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5, 0.4375, 0.125, 0.875, 0.5625, 0.1875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.5, 0.1875, 0.5625, 0.8125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.125, 0.5, 0.5625, 0.1875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.1875, 0.1875, 0.5625, 0.5), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.4375, 0.1875, 0.875, 0.5625, 0.4375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.4375, 0.4375, 0.875, 0.5625, 0.8125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.8125, 0.5, 0.5625, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 0.125, 0, 0.59375, 0.5, 0.125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.49375, 0, 0, 0.86875, 0.125, 0.125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.11875000000000002, 0, 0.875, 0.49375, 0.125, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.49375, 0, 0.875, 0.86875, 0.125, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.11875000000000002, 0, 0, 0.49375, 0.125, 0.125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 1, 0.875, 0.59375, 1.5, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 0.5, 0.875, 0.59375, 1, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 0.125, 0.875, 0.59375, 0.5, 1), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 0.5, 0, 0.59375, 1, 0.125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.40625, 1, 0, 0.59375, 1.5, 0.125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.44375, 1.34375, 0.375, 0.56875, 1.46875, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.475, 1.46875, 0.46875, 0.5375, 1.90625, 0.53125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.475, 1.09375, 0.46875, 0.5375, 1.34375, 0.53125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.45625, 1.03125, 0.45, 0.55625, 1.09375, 0.55), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.39375, 1.90625, 0.3875, 0.61875, 1.9375, 0.6125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.475, 1.34375, 1, 0.5375, 1.46875, 1.0625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.475, 1.34375, -0.0625, 0.5375, 1.46875, 0), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.44375, 1.34375, 0.125, 0.56875, 1.46875, 0.375), BooleanBiFunction.OR);
		
		return shape;
	}
	
	public static VoxelShape makeShapeNS() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.4375, 0.28125, 0.28125, 0.9, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.53125, 0.71875, 0.78125, 0.59375, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.53125, 0.21875, 0.78125, 0.59375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.78125, 0.25, 0.25, 0.84375, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.78125, 0.21875, 0.78125, 0.84375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.4375, 0.25, 0.75, 0.9, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.78125, 0.75, 0.28125, 0.84375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.53125, 0.75, 0.28125, 0.59375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.53125, 0.75, 0.78125, 0.59375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.78125, 0.75, 0.78125, 0.84375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.4375, 0.71875, 0.75, 0.9, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.99375, 0.71875, 0.75, 1.0875, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.99375, 0.25, 0.71875, 1.0875, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.99375, 0.25, 0.75, 1.0875, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.99375, 0.28125, 0.28125, 1.0875, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.9, 0.28125, 0.28125, 0.99375, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.9, 0.25, 0.75, 0.99375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.9, 0.25, 0.71875, 0.99375, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.9, 0.71875, 0.75, 0.99375, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.53125, 0.21875, 0.75, 0.59375, 0.25), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.53125, 0.21875, 0.71875, 0.59375, 0.25), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.71875, 0.78125, 0.21875, 0.75, 0.84375, 0.25), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.78125, 0.21875, 0.71875, 0.84375, 0.25), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.4375, 0.25, 0.71875, 0.9, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.53125, 0.28125, 0.25, 0.59375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.53125, 0.25, 0.25, 0.59375, 0.28125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.75, 0.78125, 0.71875, 0.78125, 0.84375, 0.75), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.21875, 0.78125, 0.28125, 0.25, 0.84375, 0.78125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0.4375, 0.28125, 0.71875, 0.4375, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 1.01875, 0.28125, 0.71875, 1.01875, 0.71875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0.3125, 0.46875, 0.90625, 0.4375, 0.53125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.5, 0.1875, 0.5625, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.4375, 0.5, 0.875, 0.5625, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.125, 0.5, 0.5625, 0.1875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.4375, 0.125, 0.875, 0.5625, 0.5), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5, 0.4375, 0.125, 0.8125, 0.5625, 0.1875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.8125, 0.8125, 0.5625, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.4375, 0.8125, 0.5625, 0.5625, 0.875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.4375, 0.125, 0.1875, 0.5625, 0.5), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 0.125, 0.40625, 1, 0.5, 0.59375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 0, 0.49375, 1, 0.125, 0.86875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.11875000000000002, 0.125, 0.125, 0.49375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0, 0.49375, 0.125, 0.125, 0.86875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 0, 0.11875000000000002, 1, 0.125, 0.49375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 1, 0.40625, 0.125, 1.5, 0.59375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.5, 0.40625, 0.125, 1, 0.59375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.125, 0.40625, 0.125, 0.5, 0.59375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 0.5, 0.40625, 1, 1, 0.59375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.875, 1, 0.40625, 1, 1.5, 0.59375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 1.34375, 0.44375, 0.625, 1.46875, 0.56875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.46875, 1.46875, 0.475, 0.53125, 1.90625, 0.5375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.46875, 1.09375, 0.475, 0.53125, 1.34375, 0.5375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.44999999999999996, 1.03125, 0.45625, 0.55, 1.09375, 0.55625), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.38749999999999996, 1.90625, 0.39375, 0.6125, 1.9375, 0.61875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.0625, 1.34375, 0.475, 0, 1.46875, 0.5375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(1, 1.34375, 0.475, 1.0625, 1.46875, 0.5375), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.625, 1.34375, 0.44375, 0.875, 1.46875, 0.56875), BooleanBiFunction.OR);
		
		return shape;
	}
}