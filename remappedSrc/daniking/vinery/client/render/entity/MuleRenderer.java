package daniking.vinery.client.render.entity;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.ClientSetup;
import daniking.vinery.client.model.MuleModel;
import daniking.vinery.entity.TraderMuleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(value= EnvType.CLIENT)
public class MuleRenderer
        extends MobRenderer<TraderMuleEntity, MuleModel> {
    private static final ResourceLocation TEXTURE = new VineryIdentifier("textures/entity/wandering_mule.png");

    public MuleRenderer(EntityRendererProvider.Context context) {
        super(context, new MuleModel(context.bakeLayer(ClientSetup.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public ResourceLocation getTexture(TraderMuleEntity cowEntity) {
        return TEXTURE;
    }
}
