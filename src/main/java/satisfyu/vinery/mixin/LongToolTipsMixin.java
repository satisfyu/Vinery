package satisfyu.vinery.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.util.TooltipHelper;

import java.util.ArrayList;
import java.util.List;

@Mixin(Screen.class)
public abstract class LongToolTipsMixin {
    @Shadow
    protected TextRenderer textRenderer;
    @Shadow
    public int width;

    @ModifyVariable(method = "renderTooltipFromComponents", at = @At(value = "HEAD"), index = 2, argsOnly = true)
    public List<TooltipComponent> makeListMutable(List<TooltipComponent> value) {
        return new ArrayList<>(value);
    }

    @Inject(method = "renderTooltipFromComponents", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0))
    public void fix(MatrixStack matrices, List<TooltipComponent> components, int x, int y, CallbackInfo ci) {
        TooltipHelper.newFix(components, textRenderer, x, width);
    }

    @ModifyVariable(method = "renderTooltipFromComponents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"), index = 7)
    public int modifyRenderX(int value, MatrixStack matrices, List<TooltipComponent> components, int x, int y) {
        return TooltipHelper.shouldFlip(components, textRenderer, x);
    }
}
