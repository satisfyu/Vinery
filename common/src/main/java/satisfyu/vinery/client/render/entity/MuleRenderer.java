package satisfyu.vinery.client.render.entity;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.client.model.MuleModel;
import satisfyu.vinery.entity.TraderMuleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(value= EnvType.CLIENT)
public class MuleRenderer extends MobRenderer<TraderMuleEntity, MuleModel> {
    private static final ResourceLocation TEXTURE = new VineryIdentifier("textures/entity/wandering_mule.png");

    public MuleRenderer(EntityRendererProvider.Context context) {
        super(context, new MuleModel(context.bakeLayer(VineryClient.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(TraderMuleEntity entity) {
        return TEXTURE;
    }

}
