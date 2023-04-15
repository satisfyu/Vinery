package satisfyu.vinery.mixin.sign;

import com.terraformersmc.terraform.sign.mixin.MixinSignBlockEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.blaze3d.vertex.PoseStack;
import com.terraformersmc.terraform.sign.TerraformSign;

@Mixin(HangingSignRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinHangingSignBlockEntityRenderer {
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/HangingSignBlockEntityRenderer;renderSign(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IIFLnet/minecraft/block/WoodType;Lnet/minecraft/client/model/Model;)V"))
	private void setRenderedHangingSignBlockEntity(HangingSignRenderer renderer, PoseStack matrices, MultiBufferSource verticesProvider, int light, int overlay, float scale, WoodType type, Model model, SignBlockEntity signBlockEntity) {
		com.terraformersmc.terraform.sign.mixin.MixinSignBlockEntityRenderer mixin = (com.terraformersmc.terraform.sign.mixin.MixinSignBlockEntityRenderer) (Object) renderer;

		mixin.terraform$renderedBlockEntity = signBlockEntity;
		mixin.renderSign(matrices, verticesProvider, light, overlay, scale, type, model);
		mixin.terraform$renderedBlockEntity = null;
	}

	@Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
	private void getHangingSignTextureId(CallbackInfoReturnable<Material> ci) {
		com.terraformersmc.terraform.sign.mixin.MixinSignBlockEntityRenderer mixin = (MixinSignBlockEntityRenderer) (Object) this;

		if (mixin.terraform$renderedBlockEntity != null) {
			Block block = mixin.terraform$renderedBlockEntity.getCachedState().getBlock();
			if (block instanceof TerraformSign) {
				ci.setReturnValue(new Material(Sheets.SIGN_SHEET, ((TerraformSign) block).getTexture()));
			}
		}
	}
}
