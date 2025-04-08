package net.satisfy.vinery.core.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.core.block.entity.StorageBlockEntity;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.util.GeneralUtil;
import net.satisfy.vinery.core.util.WineYears;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class DrinkBlockItem extends BlockItem {
    private final int baseDuration;
    private final boolean scaleDurationWithAge;
    private final BottleSize bottleSize;

    public DrinkBlockItem(Block block, Properties settings, int baseDuration, boolean scaleDurationWithAge, BottleSize bottleSize) {
        super(block, settings);
        this.baseDuration = baseDuration;
        this.scaleDurationWithAge = scaleDurationWithAge;
        this.bottleSize = bottleSize;
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
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        List<Pair<MobEffectInstance, Float>> effects = getFoodProperties() != null ? getFoodProperties().getEffects() : Lists.newArrayList();
        if (effects.isEmpty()) {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
        } else {
            for (Pair<MobEffectInstance, Float> effectPair : effects) {
                MobEffectInstance effectInstance = effectPair.getFirst();
                MobEffect effect = effectInstance.getEffect();
                String effectName = effect.getDisplayName().getString();
                int amplifier = Math.max(0, WineYears.getEffectLevel(stack, world));
                String amplifierRoman = amplifier > 0 ? " " + toRoman(amplifier) : "";
                int durationTicks = scaleDurationWithAge ? WineYears.getEffectDuration(stack, world) : baseDuration;
                durationTicks = Math.max(0, durationTicks);
                String formattedDuration = formatDuration(durationTicks);
                String tooltipText = effectName + amplifierRoman + " (" + formattedDuration + ")";
                tooltip.add(Component.literal(tooltipText).withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }
        tooltip.add(Component.empty());
        if (world != null) {
            int age = Math.max(0, WineYears.getWineAge(stack, world));
            tooltip.add(Component.translatable("tooltip.vinery.age", age).withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.empty());
            int yearsToNextUpgrade = WineYears.YEARS_PER_EFFECT_LEVEL - (age % WineYears.YEARS_PER_EFFECT_LEVEL);
            int daysToNextUpgrade = Math.max(0, yearsToNextUpgrade * WineYears.DAYS_PER_YEAR);
            tooltip.add(Component.translatable("tooltip.vinery.next_upgrade", daysToNextUpgrade)
                    .withStyle(style -> style.withColor(TextColor.fromRgb(0x93c47d))));
        }
        tooltip.add(Component.translatable("tooltip.vinery.bottle_size." + bottleSize.name().toLowerCase())
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide) {
            int duration = Math.max(0, WineYears.getEffectDuration(itemStack, level));
            int amplifier = Math.max(0, WineYears.getEffectLevel(itemStack, level));
            List<Pair<MobEffectInstance, Float>> effects = Objects.requireNonNull(getFoodProperties()).getEffects();
            for (Pair<MobEffectInstance, Float> effectPair : effects) {
                MobEffect effect = effectPair.getFirst().getEffect();
                livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }
        itemStack.shrink(1);
        return GeneralUtil.convertStackAfterFinishUsing(livingEntity, itemStack, ObjectRegistry.WINE_BOTTLE.get(), this);
    }

    private String formatDuration(int ticks) {
        int totalSeconds = Math.max(0, ticks) / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        WineYears.setWineYear(stack, world);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (world != null && WineYears.hasWineYear(stack)) {
            WineYears.setWineYear(stack, world);
        }
    }

    private String toRoman(int number) {
        return switch (number) {
            case 0 -> "I";
            case 1 -> "II";
            case 2 -> "III";
            case 3 -> "IV";
            case 4 -> "V";
            case 5 -> "VI";
            case 6 -> "VII";
            case 7 -> "VIII";
            case 8 -> "IX";
            case 9 -> "X";
            default -> String.valueOf(number);
        };
    }

    public enum BottleSize {
        SMALL, BIG
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public CompoundTag getShareTag(ItemStack stack) {
        return WineYears.getShareTag(stack);
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        WineYears.readShareTag(stack, nbt);
    }
}
