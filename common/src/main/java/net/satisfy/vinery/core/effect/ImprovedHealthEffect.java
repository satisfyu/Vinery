package net.satisfy.vinery.core.effect;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.core.Vinery;

public class ImprovedHealthEffect extends MobEffect {
    private static final ResourceLocation MAX_HEALTH_ID = ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "max_health");

    public ImprovedHealthEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);

        this.addAttributeModifier(
                Attributes.MAX_HEALTH,
                MAX_HEALTH_ID,
                4.0,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}