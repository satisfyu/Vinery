package satisfyu.vinery.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.util.ShaderUtils;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;tryTakeScreenshotIfNeeded()V"))
    public void render(CallbackInfo ci) {
        if(ShaderUtils.enabled && ShaderUtils.shader != null) {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            //RenderSystem.enableTexture();
            RenderSystem.resetTextureMatrix();
            ShaderUtils.shader.process(ShaderUtils.client.getFrameTime());
        }
    }
}
