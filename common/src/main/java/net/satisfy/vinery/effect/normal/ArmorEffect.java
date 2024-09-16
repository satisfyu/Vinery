package net.satisfy.vinery.effect.normal;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.effect.NormalEffect;
import net.satisfy.vinery.util.VineryIdentifier;

public class ArmorEffect extends NormalEffect {
    private static final ResourceLocation ARMOR_ID = VineryIdentifier.of("armor_effect");
    private static final ResourceLocation ARMOR_TOUGHNESS_ID = VineryIdentifier.of("armor_toughness_effect");

    public ArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_ID, 4.0F, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_ID, 6.0F, AttributeModifier.Operation.ADD_VALUE);
    }
}