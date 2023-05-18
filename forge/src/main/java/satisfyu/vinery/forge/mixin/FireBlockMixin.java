package satisfyu.vinery.forge.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfyu.vinery.forge.registry.BurningBlockRegistry;

@Mixin(FireBlock.class)
public class FireBlockMixin {


    @Inject(method = "getBurnOdds", at = @At("HEAD"), cancellable = true)
    private void getBurnOdds(BlockState blockState, CallbackInfoReturnable<Integer> cir) {
        Block block = blockState.getBlock();
        if(BurningBlockRegistry.getInstance().containsKey(block)){
            cir.setReturnValue(BurningBlockRegistry.getBurnOdd(block));
        }
    }

    @Inject(method = "getIgniteOdds(Lnet/minecraft/world/level/block/state/BlockState;)I", at = @At("HEAD"), cancellable = true)
    private void getIgniteOdds(BlockState blockState, CallbackInfoReturnable<Integer> cir) {
        Block block = blockState.getBlock();
        if(BurningBlockRegistry.getInstance().containsKey(block)){
            cir.setReturnValue(BurningBlockRegistry.getIgniteOdd(block));
        }
    }

}
