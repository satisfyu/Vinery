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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.block.SnowyVariantSlabBlock;
import satisfyu.vinery.registry.ObjectRegistry;



@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {

    @Inject(method = "randomTick", at = @At("HEAD"))
    void vineryRandomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random, CallbackInfo ci) {
        SnowyVariantSlabBlock.spreadingTick((SpreadingSnowyDirtBlock) (Object) this, blockState, serverLevel, blockPos, random);
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


