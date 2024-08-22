package net.satisfy.vinery.effect.normal;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.effect.NormalEffect;
import net.satisfy.vinery.util.VineryIdentifier;

public class LuckEffect extends NormalEffect {
    private static final ResourceLocation LUCK_ID = VineryIdentifier.of("luck_effect");

    public LuckEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);
        this.addAttributeModifier(Attributes.LUCK, LUCK_ID, 4.0F, AttributeModifier.Operation.ADD_VALUE);
    }
}