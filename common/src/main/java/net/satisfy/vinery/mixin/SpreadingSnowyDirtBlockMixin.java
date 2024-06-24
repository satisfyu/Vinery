package net.satisfy.vinery.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.block.SpreadableGrassSlabBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {

    @Inject(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci, BlockState defaultState, int j, BlockPos spreadPos) {
        SpreadableGrassSlabBlock.trySpread(world, spreadPos);
    }
}


