package satisfyu.vinery.mixin;

import satisfyu.vinery.client.render.feature.CustomArmorFeatureRenderer;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntityRenderer.class)
public abstract class ArmorStandRendererMixin extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> {
    @Inject(at = @At("RETURN"), method = "<init>")
    public void onConstruct(EntityRendererFactory.Context context, CallbackInfo ci) {
        addFeature(new CustomArmorFeatureRenderer<>(this, context.getModelLoader()));
    }
    public ArmorStandRendererMixin(EntityRendererFactory.Context ctx, ArmorStandArmorEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }
}