package net.satisfy.vinery.core.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.satisfy.vinery.core.Vinery;

public class FrostyArmorEffect extends MobEffect {
    private static final ResourceLocation MOVEMENT_SPEED_ID = ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "frosty_movement_speed");
    private static final ResourceLocation DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "frosty_damage");
    private static final ResourceLocation ARMOR_ID = ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "frosty_armor");

    public static final double FROST_MULTIPLIER = -0.05D;

    public FrostyArmorEffect() {
        super(MobEffectCategory.NEUTRAL, 0x56CBFD);
        this.addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                MOVEMENT_SPEED_ID,
                FROST_MULTIPLIER,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        this.addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                DAMAGE_ID,
                2.0,
                AttributeModifier.Operation.ADD_VALUE
        );
        this.addAttributeModifier(
                Attributes.ARMOR,
                ARMOR_ID,
                4.0,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

    @Override
    public boolean applyEffectTick(LivingEntity living, int amplifier) {
        living.setIsInPowderSnow(true);
        if (amplifier > 0 && living.canFreeze()) {
            living.setTicksFrozen(Math.min(living.getTicksRequiredToFreeze(), living.getTicksFrozen() + amplifier));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}