package net.satisfy.vinery.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.client.model.MuleModel;
import net.satisfy.vinery.entity.TraderMuleEntity;
import net.satisfy.vinery.util.VineryIdentifier;
import org.jetbrains.annotations.NotNull;

@Environment(value= EnvType.CLIENT)
public class MuleRenderer<T extends TraderMuleEntity> extends MobRenderer<T, MuleModel<T>> {
    private static final ResourceLocation TEXTURE = VineryIdentifier.of("textures/entity/wandering_mule.png");

    public MuleRenderer(EntityRendererProvider.Context context) {
        super(context, new MuleModel<>(context.bakeLayer(MuleModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(TraderMuleEntity entity) {
        return TEXTURE;
    }

}
