package satisfyu.vinery.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.lighting.LightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.registry.ObjectRegistry;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {



    /**
     * @author Cristelknight
     * @reason Grass spreading. And I'm too stupid to do it without this overwrite. This works somehow. don't touch
     */
    @Overwrite
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!canBeGrass(blockState, serverLevel, blockPos)) {
            serverLevel.setBlockAndUpdate(blockPos, fromGrass(blockState));
            return;
        }
        if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9) {
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                BlockState toReplace = serverLevel.getBlockState(blockPos2);
                if(!toReplace.is(Blocks.DIRT) && !toReplace.is(ObjectRegistry.DIRT_SLAB.get())) continue;
                BlockState blockState2 = toGrass(toReplace);
                if (!canPropagate(blockState2, serverLevel, blockPos2)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, blockState2.setValue(SpreadingSnowyDirtBlock.SNOWY, serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW)));
            }
        }
    }

    @Unique
    private static boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(Blocks.SNOW) && blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState2.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(
                    levelReader, blockState, blockPos, blockState2, blockPos2, Direction.UP, blockState2.getLightBlock(levelReader, blockPos2)
            );

            //fix stupid bug ig
            if(i == 16 && blockState.is(ObjectRegistry.GRASS_SLAB.get()) && blockState2.getBlock() instanceof AirBlock && blockState.getValue(SlabBlock.TYPE).equals(SlabType.TOP)){
                return true;
            }
            boolean bl = i < levelReader.getMaxLightLevel();
            if(!bl) Vinery.LOGGER.error(i + " " + blockState + " " + blockState2);

            return bl;
        }
    }

    @Unique
    private static boolean canPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        return canBeGrass(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPos2).is(FluidTags.WATER);
    }

    @Unique
    private BlockState fromGrass(BlockState blockState){
        BlockState state;
        if(blockState.is(ObjectRegistry.GRASS_SLAB.get())){
            state = ObjectRegistry.DIRT_SLAB.get().defaultBlockState().setValue(SlabBlock.TYPE, blockState.getValue(SlabBlock.TYPE));
        }
        else state = Blocks.DIRT.defaultBlockState();
        return state;
    }

    @Unique
    private BlockState toGrass(BlockState blockState){
        BlockState state;
        if(blockState.is(ObjectRegistry.DIRT_SLAB.get())){
            state = ObjectRegistry.GRASS_SLAB.get().defaultBlockState().setValue(SlabBlock.TYPE, blockState.getValue(SlabBlock.TYPE));
        }
        else state = Blocks.GRASS_BLOCK.defaultBlockState();
        return state;
    }

    /*
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean isBlock(BlockState instance, Block block) {
        if(block.equals(Blocks.DIRT)){
            return instance.is(block) || instance.is(ObjectRegistry.DIRT_SLAB.get());
        }
        return instance.is(block);
    }
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean replace(ServerLevel level, BlockPos blockPos, BlockState blockState) {

        BlockState state;
        BlockState original = level.getBlockState(blockPos);
        Vinery.LOGGER.error("Original is: " + original + " at: " + blockPos);

        if(original.is(ObjectRegistry.DIRT_SLAB.get())) state = ObjectRegistry.GRASS_SLAB.get().defaultBlockState().setValue(SlabBlock.TYPE, original.getValue(SlabBlock.TYPE));
        else state = ((SpreadingSnowyDirtBlock)((Object) this)).defaultBlockState();

        return level.setBlockAndUpdate(blockPos, state.setValue(SnowyDirtBlock.SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
    }
     */
}
