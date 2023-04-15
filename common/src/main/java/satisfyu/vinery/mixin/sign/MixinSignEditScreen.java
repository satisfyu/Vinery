package satisfyu.vinery.mixin.sign;

import com.mojang.blaze3d.vertex.PoseStack;
import com.terraformersmc.terraform.sign.TerraformSign;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SignEditScreen.class)
@Environment(EnvType.CLIENT)
public class MixinSignEditScreen {
	@ModifyVariable(method = "renderSignBackground", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/block/WoodType;)Lnet/minecraft/client/util/SpriteIdentifier;"))
	private Material getSignTextureId(Material spriteIdentifier, PoseStack matrices, MultiBufferSource.BufferSource vertexConsumers, BlockState state) {
		if (state.getBlock() instanceof TerraformSign) {
			return new Material(Sheets.SIGN_SHEET, ((TerraformSign) state.getBlock()).getTexture());
		}
		return spriteIdentifier;
	}
}
