package satisfyu.vinery.mixin.sign;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfyu.vinery.util.sign.TerraformSign;

@Mixin(SignRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinSignBlockEntityRenderer {
	@Unique
	protected SignBlockEntity terraform$renderedBlockEntity;

	@Shadow
	public abstract void renderSign(PoseStack matrices, MultiBufferSource verticesProvider, int light, int overlay, float scale, WoodType type, Model model);

	@Redirect(method = "render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;renderSign(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIFLnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model;)V"))
	private void setRenderedBlockEntity(SignRenderer renderer, PoseStack matrices, MultiBufferSource verticesProvider, int light, int overlay, float scale, WoodType type, Model model, SignBlockEntity signBlockEntity) {
		MixinSignBlockEntityRenderer mixin = (MixinSignBlockEntityRenderer) (Object) renderer;

		mixin.terraform$renderedBlockEntity = signBlockEntity;
		mixin.renderSign(matrices, verticesProvider, light, overlay, scale, type, model);
		mixin.terraform$renderedBlockEntity = null;
	}

	@Inject(method = "getSignMaterial", at = @At("HEAD"), cancellable = true)
	private void getSignTextureId(CallbackInfoReturnable<Material> ci) {
		if (this.terraform$renderedBlockEntity != null) {
			Block block = this.terraform$renderedBlockEntity.getBlockState().getBlock();
			if (block instanceof TerraformSign) {
				ci.setReturnValue(new Material(Sheets.SIGN_SHEET, ((TerraformSign) block).getTexture()));
			}
		}
	}
}
