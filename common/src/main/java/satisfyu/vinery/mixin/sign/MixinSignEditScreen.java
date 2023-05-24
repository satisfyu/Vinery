package satisfyu.vinery.mixin.sign;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import satisfyu.vinery.util.sign.TerraformSign;

@Mixin(SignEditScreen.class)
@Environment(EnvType.CLIENT)
public class MixinSignEditScreen {
	@Shadow @Final private SignBlockEntity sign;

	@ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/client/renderer/Sheets;getSignMaterial(Lnet/minecraft/world/level/block/state/properties/WoodType;)Lnet/minecraft/client/resources/model/Material;"))
	private Material getSignTextureId(Material spriteIdentifier) {
		if (sign.getBlockState().getBlock() instanceof TerraformSign) {
			return new Material(Sheets.SIGN_SHEET, ((TerraformSign) sign.getBlockState().getBlock()).getTexture());
		}
		return spriteIdentifier;
	}
}
