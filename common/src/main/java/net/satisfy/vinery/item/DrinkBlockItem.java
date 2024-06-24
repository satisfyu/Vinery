package net.satisfy.vinery.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.registry.ObjectRegistry;
import net.satisfy.vinery.util.WineYears;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DrinkBlockItem extends BlockItem {
    public DrinkBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }


    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        if (!Objects.requireNonNull(context.getPlayer()).isCrouching()) {
            return null;
        }

        BlockState blockState = this.getBlock().getStateForPlacement(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos blockPos, Level level, @Nullable Player player, ItemStack itemStack, BlockState blockState) {
        if(level.getBlockEntity(blockPos) instanceof StorageBlockEntity wineEntity){
            wineEntity.setStack(0, itemStack.copyWithCount(1));
        }
        return super.updateCustomBlockEntityTag(blockPos, level, player, itemStack, blockState);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        List<Pair<MobEffectInstance, Float>> list2 = getFoodProperties() != null ? getFoodProperties().getEffects() : Lists.newArrayList();
        List<Pair<Attribute, AttributeModifier>> list3 = Lists.newArrayList();
        if (list2.isEmpty()) {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
        } else {
            for(Pair<MobEffectInstance, Float> statusEffectInstance : list2) {
                MutableComponent mutableText = Component.translatable(statusEffectInstance.getFirst().getDescriptionId());
                MobEffect statusEffect = statusEffectInstance.getFirst().getEffect();
                Map<Attribute, AttributeModifier> map = statusEffect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier entityAttributeModifier = entry.getValue();
                        AttributeModifier entityAttributeModifier2 = new AttributeModifier(
                                entityAttributeModifier.getName(),
                                statusEffect.getAttributeModifierValue(statusEffectInstance.getFirst().getAmplifier(), entityAttributeModifier),
                                entityAttributeModifier.getOperation()
                        );
                        list3.add(new Pair<>(entry.getKey(), entityAttributeModifier2));
                    }
                }

                if (world != null) {
                    mutableText = Component.translatable(
                            "potion.withAmplifier",
                            mutableText, Component.translatable("potion.potency." + WineYears.getEffectLevel(stack, world)));
                }

                if (statusEffectInstance.getFirst().getDuration() > 20) {
                    mutableText = Component.translatable(
                            "potion.withDuration",
                            mutableText, MobEffectUtil.formatDuration(statusEffectInstance.getFirst(), statusEffectInstance.getSecond()));
                }

                tooltip.add(mutableText.withStyle(statusEffect.getCategory().getTooltipFormatting()));
            }
        }

        if (!list3.isEmpty()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for(Pair<Attribute, AttributeModifier> pair : list3) {
                AttributeModifier entityAttributeModifier3 = pair.getSecond();
                double d = entityAttributeModifier3.getAmount();
                double e;
                if (entityAttributeModifier3.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && entityAttributeModifier3.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    e = entityAttributeModifier3.getAmount();
                } else {
                    e = entityAttributeModifier3.getAmount() * 100.0;
                }

                if (d > 0.0) {
                    tooltip.add(
                            Component.translatable(
                                            "attribute.modifier.plus." + entityAttributeModifier3.getOperation().toValue(),
                                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(pair.getFirst().getDescriptionId()))
                                    .withStyle(ChatFormatting.BLUE)
                    );
                } else if (d < 0.0) {
                    e *= -1.0;
                    tooltip.add(
                            Component.translatable(
                                            "attribute.modifier.take." + entityAttributeModifier3.getOperation().toValue(),
                                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(pair.getFirst().getDescriptionId()))
                                    .withStyle(ChatFormatting.RED)
                    );
                }
            }
        }
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.vinery.year").withStyle(ChatFormatting.WHITE).append(Component.nullToEmpty(" " + WineYears.getWineYear(stack, world))));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(itemStack, level, livingEntity);
        return GeneralUtil.convertStackAfterFinishUsing(livingEntity, itemStack, ObjectRegistry.WINE_BOTTLE.get(), this);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}