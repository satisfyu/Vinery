package satisfyu.vinery.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.model.MuleModel;
import satisfyu.vinery.entity.TraderMuleEntity;

@Environment(value= EnvType.CLIENT)
public class MuleRenderer<T extends TraderMuleEntity> extends MobRenderer<T, MuleModel<T>> {
    private static final ResourceLocation TEXTURE = new VineryIdentifier("textures/entity/wandering_mule.png");

    public MuleRenderer(EntityRendererProvider.Context context) {
        super(context, new MuleModel<>(context.bakeLayer(MuleModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(TraderMuleEntity entity) {
        return TEXTURE;
    }

}
