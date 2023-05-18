package satisfyu.vinery.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.util.TooltipHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

@Mixin(Screen.class)
public abstract class LongToolTipsMixin {
    @Shadow
    protected Font font;
    @Shadow
    public int width;

    @ModifyVariable(method = "renderTooltipInternal", at = @At(value = "HEAD"), index = 2, argsOnly = true)
    public List<ClientTooltipComponent> makeListMutable(List<ClientTooltipComponent> value) {
        return new ArrayList<>(value);
    }

    @Inject(method = "renderTooltipInternal", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0))
    public void fix(PoseStack poseStack, List<ClientTooltipComponent> list, int x, int y, CallbackInfo ci) {
        TooltipHelper.newFix(list, font, x, width);
    }

    @ModifyVariable(method = "renderTooltipInternal", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"), index = 7)
    public int modifyRenderX(int value, PoseStack matrices, List<ClientTooltipComponent> components, int x, int y) {
        return TooltipHelper.shouldFlip(components, font, x);
    }
}
