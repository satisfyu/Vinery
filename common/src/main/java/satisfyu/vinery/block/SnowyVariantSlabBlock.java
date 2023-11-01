package satisfyu.vinery.block;

import com.google.common.math.DoubleMath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfyu.vinery.registry.ObjectRegistry;

public class SnowyVariantSlabBlock extends SlabBlock implements BonemealableBlock {
	public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
	
	public SnowyVariantSlabBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, false).setValue(SNOWY, false));
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.UP) {
			return state.setValue(SNOWY, neighborState.is(BlockTags.SNOW));
		}
		return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SNOWY);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
		return blockState.getValue(SlabBlock.TYPE).equals(SlabType.DOUBLE) && levelReader.getBlockState(blockPos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		GrassBlock block = (GrassBlock) Blocks.GRASS_BLOCK;
		block.performBonemeal(serverLevel, randomSource, blockPos, blockState);
	}

	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		spreadingTick(this, blockState, serverLevel, blockPos, randomSource);
	}

	// Credits to Wunderreich for the spreading mechanic
	public static void spreadingTick(Block me, BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random) {
		if (!canBeGrass(blockState, level, blockPos)) {
			final BlockState testState = level.getBlockState(blockPos);
			if (me instanceof SnowyVariantSlabBlock) {
				level.setBlockAndUpdate(
						blockPos,
						ObjectRegistry.DIRT_SLAB.get()
								.defaultBlockState()
								.setValue(WATERLOGGED, testState.getValue(WATERLOGGED))
								.setValue(TYPE, testState.getValue(TYPE))
				);
			} else if (me instanceof SpreadingSnowyDirtBlock) {
				level.setBlockAndUpdate(
						blockPos,
						Blocks.DIRT.defaultBlockState()
				);
			}
			return;
		}

		if (level.getMaxLocalRawBrightness(blockPos.above()) >= 9) {
			final BlockState grassSlabBlockState = ObjectRegistry.GRASS_SLAB.get().defaultBlockState();
			final BlockState grassBlockState = Blocks.GRASS_BLOCK.defaultBlockState();
			BlockState testState;

			for (int i = 0; i < 2; ++i) {
				BlockPos testPos = blockPos.offset(
						random.nextInt(3) - 1,
						random.nextInt(5) - 3,
						random.nextInt(3) - 1
				);
				testState = level.getBlockState(testPos);
				if (!canPropagate(testState, level, testPos)) continue;

				if (testState.is(Blocks.DIRT)) {
					level.setBlockAndUpdate(testPos, grassBlockState.setValue(SNOWY, level.getBlockState(testPos.above()).is(Blocks.SNOW)));
				} else if (testState.is(ObjectRegistry.DIRT_SLAB.get())) {
					final BlockState newState = grassSlabBlockState.setValue(SNOWY, level.getBlockState(testPos.above()).is(Blocks.SNOW)).setValue(WATERLOGGED, testState.getValue(WATERLOGGED)).setValue(TYPE, testState.getValue(TYPE));
					level.setBlockAndUpdate(testPos, newState);
				}
			}
		}
	}

	public static boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		final Block bl = blockState.getBlock();
		if (bl instanceof SnowyVariantSlabBlock) {
			return canBeGrassNewSlab(blockState, levelReader, blockPos);
		} else {
			return SpreadingSnowyDirtBlock.canBeGrass(blockState, levelReader, blockPos);
		}
	}

	private static boolean canPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		BlockPos blockPos2 = blockPos.above();
		return canBeGrass(blockState, levelReader, blockPos) && !levelReader
				.getFluidState(blockPos2).is(FluidTags.WATER);
	}

	private static boolean canBeGrassNewSlab(
			BlockState state,
			LevelReader reader,
			BlockPos pos
	) {
		if (state.getValue(TYPE) == SlabType.BOTTOM) {
			return true;
		}

		return canBeGrassNew(state, reader, pos);
	}

	private static boolean canBeGrassNew(
			BlockState state,
			LevelReader reader,
			BlockPos pos
	) {

		BlockPos abovePos = pos.above();
		BlockState aboveState = reader.getBlockState(abovePos);
		if (state.is(Blocks.SNOW) && state.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		}
		if (state.getFluidState().getAmount() == 8) {
			return false;
		}
		return !doesOcclude(
				reader,
				state,
				pos,
				aboveState,
				abovePos,
				Direction.UP
		);
	}

	public static boolean doesOcclude(
			BlockGetter blockGetter,
			BlockState state,
			BlockPos pos,
			BlockState aboveState,
			BlockPos abovePos,
			Direction direction
	) {


		if (!aboveState.canOcclude()) {
			return false;
		}

		VoxelShape shape = state.getOcclusionShape(blockGetter, pos);
		VoxelShape aboveShape = aboveState.getOcclusionShape(blockGetter, abovePos);
		if (!aboveState.useShapeForLightOcclusion() && aboveShape != Shapes.block()) {
			return false;
		}

		if (aboveShape.isEmpty()) return false;
		Direction.Axis axis = direction.getAxis();
		if (!DoubleMath.fuzzyEquals(shape.max(axis), 1.0, 1.0E-7)) {
			return false;
		}
		if (!DoubleMath.fuzzyEquals(aboveShape.min(axis), 0.0, 1.0E-7)) {
			return false;
		}
		return Shapes.mergedFaceOccludes(shape, aboveShape, direction);
	}

}