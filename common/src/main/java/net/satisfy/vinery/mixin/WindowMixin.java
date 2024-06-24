package net.satisfy.vinery.mixin;

import com.mojang.blaze3d.platform.Window;
import net.satisfy.vinery.util.ShaderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {

    @Inject(at = @At("TAIL"), method = "onFramebufferResize")
    private void updateShaderSize(CallbackInfo ci) {
        if(ShaderUtils.enabled)
            ShaderUtils.shader.resize(ShaderUtils.client.getWindow().getWidth(), ShaderUtils.client.getWindow().getHeight());
    }
}