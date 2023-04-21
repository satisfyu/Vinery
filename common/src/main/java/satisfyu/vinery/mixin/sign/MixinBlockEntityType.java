package satisfyu.vinery.mixin.sign;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfyu.vinery.util.sign.TerraformSign;

@Mixin(BlockEntityType.class)
public class MixinBlockEntityType {
	@Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
	private void supports(BlockState state, CallbackInfoReturnable<Boolean> info) {
		Block block = state.getBlock();
		if (block instanceof TerraformSign) {
			if (BlockEntityType.HANGING_SIGN.equals(this)) {
				if (!(block instanceof CeilingHangingSignBlock || block instanceof WallHangingSignBlock)) {
					return;
				}
			} else if (!BlockEntityType.SIGN.equals(this)) {
				return;
			}

			info.setReturnValue(true);
		}
	}
}
