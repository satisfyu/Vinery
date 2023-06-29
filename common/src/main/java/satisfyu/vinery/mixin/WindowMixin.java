package satisfyu.vinery.mixin;

import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.util.ShaderUtils;

@Mixin(Window.class)
public class WindowMixin {

    @Inject(at = @At("TAIL"), method = "onFramebufferResize")
    private void updateShaderSize(CallbackInfo ci) {
        if(ShaderUtils.enabled)
            ShaderUtils.shader.resize(ShaderUtils.client.getWindow().getWidth(), ShaderUtils.client.getWindow().getHeight());
    }
}