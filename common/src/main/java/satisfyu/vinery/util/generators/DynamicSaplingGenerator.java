package satisfyu.vinery.util.generators;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public abstract class DynamicSaplingGenerator extends AbstractTreeGrower
{
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
        return null;
    }

    @Nullable
    protected abstract Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(ServerLevel world, RandomSource random, boolean bees);

    public boolean growTree(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random) {
        Holder<? extends ConfiguredFeature<?, ?>> registryEntry = this.getTreeFeature(world, random, this.hasFlowers(world, pos));
        if(registryEntry == null) return false;
        ConfiguredFeature<?, ?> configuredFeature = registryEntry.value();
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
        if(configuredFeature.place(world, chunkGenerator, random, pos)) {
            return true;
        }
        world.setBlock(pos, state, Block.UPDATE_INVISIBLE);
        return false;
    }

    private boolean hasFlowers(LevelAccessor world, BlockPos pos) {
        for(BlockPos blockPos : BlockPos.MutableBlockPos.betweenClosed(pos.below().north(2).west(2), pos.above().south(2).east(2))) {
            if(!world.getBlockState(blockPos).is(BlockTags.FLOWERS)) continue;
            return true;
        }
        return false;
    }
}