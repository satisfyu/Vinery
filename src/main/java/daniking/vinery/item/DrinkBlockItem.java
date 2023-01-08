package daniking.vinery.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import daniking.vinery.util.WineYears;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DrinkBlockItem extends BlockItem {
    public DrinkBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        List<Pair<StatusEffectInstance, Float>> list2 = getFoodComponent() != null ? getFoodComponent().getStatusEffects() : Lists.newArrayList();
        List<Pair<EntityAttribute, EntityAttributeModifier>> list3 = Lists.newArrayList();
        if (list2.isEmpty()) {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
        } else {
            for(Pair<StatusEffectInstance, Float> statusEffectInstance : list2) {
                MutableText mutableText = Text.translatable(statusEffectInstance.getFirst().getTranslationKey());
                StatusEffect statusEffect = statusEffectInstance.getFirst().getEffectType();
                Map<EntityAttribute, EntityAttributeModifier> map = statusEffect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for(Map.Entry<EntityAttribute, EntityAttributeModifier> entry : map.entrySet()) {
                        EntityAttributeModifier entityAttributeModifier = entry.getValue();
                        EntityAttributeModifier entityAttributeModifier2 = new EntityAttributeModifier(
                                entityAttributeModifier.getName(),
                                statusEffect.adjustModifierAmount(statusEffectInstance.getFirst().getAmplifier(), entityAttributeModifier),
                                entityAttributeModifier.getOperation()
                        );
                        list3.add(new Pair<>(entry.getKey(), entityAttributeModifier2));
                    }
                }

                if (world != null) {
                    mutableText = Text.translatable(
                            "potion.withAmplifier",
                            mutableText, Text.translatable("potion.potency." + WineYears.getEffectLevel(stack, world)));
                }

                if (statusEffectInstance.getFirst().getDuration() > 20) {
                    mutableText = Text.translatable(
                            "potion.withDuration",
                            mutableText, StatusEffectUtil.durationToString(statusEffectInstance.getFirst(), statusEffectInstance.getSecond()));
                }

                tooltip.add(mutableText.formatted(statusEffect.getCategory().getFormatting()));
            }
        }

        if (!list3.isEmpty()) {
            tooltip.add(Text.empty());
            tooltip.add(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE));

            for(Pair<EntityAttribute, EntityAttributeModifier> pair : list3) {
                EntityAttributeModifier entityAttributeModifier3 = pair.getSecond();
                double d = entityAttributeModifier3.getValue();
                double e;
                if (entityAttributeModifier3.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_BASE && entityAttributeModifier3.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_TOTAL) {
                    e = entityAttributeModifier3.getValue();
                } else {
                    e = entityAttributeModifier3.getValue() * 100.0;
                }

                if (d > 0.0) {
                    tooltip.add(
                            Text.translatable(
                                    "attribute.modifier.plus." + entityAttributeModifier3.getOperation().getId(),
                                    ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(pair.getFirst().getTranslationKey()))
                                    .formatted(Formatting.BLUE)
                    );
                } else if (d < 0.0) {
                    e *= -1.0;
                    tooltip.add(
                            Text.translatable(
                                    "attribute.modifier.take." + entityAttributeModifier3.getOperation().getId(),
                                    ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(pair.getFirst().getTranslationKey()))
                                    .formatted(Formatting.RED)
                    );
                }
            }
        }
        
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("tooltip.vinery.year").formatted(Formatting.GRAY).append(Text.of(" " + WineYears.getWineYear(stack))));
    }
}
