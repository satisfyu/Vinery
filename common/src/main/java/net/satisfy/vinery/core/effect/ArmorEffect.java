package net.satisfy.vinery.core.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.core.Vinery;

public class ArmorEffect extends MobEffect {
    private static final ResourceLocation ARMOR_ID = ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "armor");
    private static final ResourceLocation ARMOR_TOUGHNESS_ID = ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "armor_toughness");

    public ArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);
        this.addAttributeModifier(
                Attributes.ARMOR,
                ARMOR_ID,
                2.0,
                AttributeModifier.Operation.ADD_VALUE
        );
        this.addAttributeModifier(
                Attributes.ARMOR_TOUGHNESS,
                ARMOR_TOUGHNESS_ID,
                3.0,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}