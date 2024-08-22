package net.satisfy.vinery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.Fluids;
import net.satisfy.vinery.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public class SpreadableGrassSlabBlock extends SlabBlock implements BonemealableBlock {
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
    public static final Block GRASS_BLOCK = Blocks.GRASS_BLOCK;

    public static Block getDirtSlabBlock(){
        return ObjectRegistry.DIRT_SLAB.get();
    }

    public static Block getGrassSlabBlock(){
        return ObjectRegistry.GRASS_SLAB.get();
    }

    public SpreadableGrassSlabBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, false).setValue(SNOWY, false));
    }

    public static boolean canSurviveNew(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);

        if (state.getBlock().equals(getDirtSlabBlock()) && state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM) {
            return !state.getValue(SlabBlock.WATERLOGGED);
        }

        if (blockState.is(Blocks.SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }

        if (blockState.getFluidState().getAmount() == 8) {
            return false;
        }

        if (state.is(BlockTags.WALLS) && blockState.is(BlockTags.WALLS) && blockState.canOcclude()) {
            return false;
        }

        if (blockState.getBlock() instanceof SlabBlock && blockState.getValue(SlabBlock.TYPE) == SlabType.TOP){
            return true;
        }

        if (blockState.getBlock() instanceof StairBlock && blockState.getValue(StairBlock.HALF) == Half.TOP){
            return true;
        }

        int i = LightEngine.getLightBlockInto(world, GRASS_BLOCK.defaultBlockState(), pos, blockState, blockPos, Direction.UP, blockState.getLightBlock(world, blockPos));
        return i < world.getMaxLightLevel();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        if(blockState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM) return false;

        return levelReader.getBlockState(blockPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        GrassBlock block = (GrassBlock) GRASS_BLOCK;
        block.performBonemeal(world, random, pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!canSurviveNew(state, world, pos)) {
            world.setBlock(pos, getDirtSlabBlock().withPropertiesOf(state), Block.UPDATE_CLIENTS);
        } else {
            if (world.getMaxLocalRawBrightness(pos.above()) >= 9) {
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    trySpread(world, blockPos);
                }
            }
        }
    }

    public static void trySpread(ServerLevel world, BlockPos spreadPos) {
        BlockState oldState = world.getBlockState(spreadPos);

        if (oldState.is(Blocks.DIRT) || oldState.is(getDirtSlabBlock())) {
            BlockState aboveState = world.getBlockState(spreadPos.above());
            boolean isSnowy = aboveState.is(Blocks.SNOW);
            BlockState newState = null;

            if (oldState.is(Blocks.DIRT)) {
                newState = GRASS_BLOCK.defaultBlockState().setValue(BlockStateProperties.SNOWY, isSnowy);
            } else if(oldState.is(getDirtSlabBlock())){
                newState = getGrassSlabBlock().withPropertiesOf(oldState).setValue(BlockStateProperties.SNOWY, isSnowy);
            }

            if (newState != null && canSurviveNew(newState, world, spreadPos) && !world.getFluidState(spreadPos.above()).is(FluidTags.WATER)) {
                world.setBlockAndUpdate(spreadPos, newState);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SNOWY);
    }

    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        state = state.setValue(SNOWY, world.getBlockState(pos.above()).is(BlockTags.SNOW));

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }
}
