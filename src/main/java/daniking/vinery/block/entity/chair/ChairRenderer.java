package daniking.vinery.block.entity.chair;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class ChairRenderer extends EntityRenderer<ChairEntity> {
    public ChairRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldRender(ChairEntity entity, Frustum frustum, double x, double y, double z) {
        return false;
    }

    @Override
    public Identifier getTexture(ChairEntity entity) {
        return null;
    }

}