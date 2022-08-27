package daniking.vinery.client.model;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SimpleGeoModel<T extends LivingEntity & IAnimatable> extends AnimatedGeoModel<T> {
    private final Identifier texture;
    private final Identifier model;
    private final Identifier animation;

    public SimpleGeoModel(String modId, String name) {
        this.texture = new Identifier(modId, "textures/entity/" + name + ".png");
        this.model = new Identifier(modId, "geo/" + name + ".geo.json");
        this.animation = new Identifier(modId, "animations/" + name + ".animation.json");
    }

    @Override
    public Identifier getAnimationFileLocation(T entity) {
        return animation;
    }

    @Override
    public Identifier getModelLocation(T entity) {
        return model;
    }

    @Override
    public Identifier getTextureLocation(T entity) {
        return texture;
    }
}