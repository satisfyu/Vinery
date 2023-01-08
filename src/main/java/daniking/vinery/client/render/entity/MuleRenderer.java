package daniking.vinery.client.render.entity;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.ClientSetup;
import daniking.vinery.client.model.MuleModel;
import daniking.vinery.entity.TraderMuleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class MuleRenderer
        extends MobEntityRenderer<TraderMuleEntity, MuleModel> {
    private static final Identifier TEXTURE = new VineryIdentifier("textures/entity/wandering_mule.png");

    public MuleRenderer(EntityRendererFactory.Context context) {
        super(context, new MuleModel(context.getPart(ClientSetup.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public Identifier getTexture(TraderMuleEntity cowEntity) {
        return TEXTURE;
    }
}
