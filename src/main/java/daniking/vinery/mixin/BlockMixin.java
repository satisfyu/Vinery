package daniking.vinery.mixin;

import daniking.vinery.Vinery;
import daniking.vinery.block.WineBottleBlock;
import daniking.vinery.item.DrinkBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "Lnet/minecraft/block/Block;cannotConnect(Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private static void checkCannotConnect(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if(state.isIn(Vinery.CAN_NOT_CONNECT)) cir.setReturnValue(true);
    }
}
