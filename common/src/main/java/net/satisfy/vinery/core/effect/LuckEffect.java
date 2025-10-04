package net.satisfy.vinery.core.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.core.Vinery;

public class LuckEffect extends MobEffect {
    private static final ResourceLocation LUCK_ID = ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "luck");

    public LuckEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);
        this.addAttributeModifier(
                Attributes.LUCK,
                LUCK_ID,
                2.0,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}