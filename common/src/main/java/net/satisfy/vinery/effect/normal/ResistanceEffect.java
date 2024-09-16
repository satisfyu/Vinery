package net.satisfy.vinery.effect.normal;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.effect.NormalEffect;
import net.satisfy.vinery.util.VineryIdentifier;

public class ResistanceEffect extends NormalEffect {

    private static final ResourceLocation KNOCKBACK_RESISTANCE_ID = VineryIdentifier.of("knockback_resistance");
    private static final ResourceLocation ARMOR_TOUGHNESS_ID = VineryIdentifier.of("armor_toughness");


    public ResistanceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_ID, 4.0F, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_ID, 6.0F, AttributeModifier.Operation.ADD_VALUE);
    }
}