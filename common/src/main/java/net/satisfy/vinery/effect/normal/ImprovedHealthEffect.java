package net.satisfy.vinery.effect.normal;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.effect.NormalEffect;
import net.satisfy.vinery.util.VineryIdentifier;

public class ImprovedHealthEffect extends NormalEffect {
    private static final ResourceLocation HEALTH_ID = VineryIdentifier.of("improved_health");

    public ImprovedHealthEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);
        this.addAttributeModifier(Attributes.MAX_HEALTH, HEALTH_ID, 3.0F, AttributeModifier.Operation.ADD_VALUE);
    }
}