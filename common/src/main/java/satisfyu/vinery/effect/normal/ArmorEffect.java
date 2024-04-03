package satisfyu.vinery.effect.normal;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import satisfyu.vinery.effect.NormalEffect;

import java.util.UUID;

public class ArmorEffect extends NormalEffect {
    private static final String ARMOR_UUID = "710D4861-7021-47DE-9F52-62F48D2B61EB";
    private static final String ARMOR_TOUGHNESS_UUID = "B5A8D51B-47EC-47FD-9886-7EBDFE81EBA7";


    public ArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_UUID, 4.0F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_UUID, 6.0F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        if (modifier.getId().equals(UUID.fromString(ARMOR_UUID)))
            return (amplifier + 1) * 4.0F;
        if (modifier.getId().equals(UUID.fromString(ARMOR_TOUGHNESS_UUID)))
            return (amplifier + 1) * 2.0F;
        return amplifier + 1;
    }
}