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
	@Inject(method = "getBurnOdd", at = @At("HEAD"), cancellable = true)
	private void getBurnOdd(BlockState blockState, CallbackInfoReturnable<Integer> cir) {
		Block block = blockState.getBlock();
		if (BurningBlockRegistry.getInstance().containsKey(block)) {
			cir.setReturnValue(BurningBlockRegistry.getBurnOdd(block));
		}
	}

	@Inject(method = "getFlameOdds(Lnet/minecraft/world/level/block/state/BlockState;)I", at = @At("HEAD"),
			cancellable = true)
	private void getFlameOdds(BlockState blockState, CallbackInfoReturnable<Integer> cir) {
		Block block = blockState.getBlock();
		if (BurningBlockRegistry.getInstance().containsKey(block)) {
			cir.setReturnValue(BurningBlockRegistry.getIgniteOdd(block));
		}
	}
}
