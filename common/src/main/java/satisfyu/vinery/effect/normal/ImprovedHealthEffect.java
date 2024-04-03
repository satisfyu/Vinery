package satisfyu.vinery.effect.normal;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import satisfyu.vinery.effect.NormalEffect;

import java.util.UUID;

public class ImprovedHealthEffect extends NormalEffect {
    private static final String MAX_HEALTH_UUID = "9A8F2C6B-AE75-42E1-A837-3A15A04C6C57";

    public ImprovedHealthEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x56CBFD);
        this.addAttributeModifier(Attributes.MAX_HEALTH, MAX_HEALTH_UUID, 3.0F, AttributeModifier.Operation.ADDITION);
    }


    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        if (modifier.getId().equals(UUID.fromString(MAX_HEALTH_UUID)))
            return (amplifier + 1) * 2.0F;
        return amplifier + 1;
    }
}