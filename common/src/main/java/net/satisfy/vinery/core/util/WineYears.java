package net.satisfy.vinery.core.util;

import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.platform.PlatformHelper;

public class WineYears {
    public static final int YEARS_START = 0;
    public static final int MAX_LEVEL = PlatformHelper.getWineMaxLevel();
    public static final int START_DURATION = PlatformHelper.getWineStartDuration();
    public static final int DURATION_PER_YEAR = PlatformHelper.getWineDurationPerYear();
    public static final int DAYS_PER_YEAR = PlatformHelper.getWineDaysPerYear();
    public static final int YEARS_PER_EFFECT_LEVEL = PlatformHelper.getWineYearsPerEffectLevel();
    public static final int MAX_DURATION = PlatformHelper.getWineMaxDuration();

    public static final String TAG_YEAR = "Year";
    public static final String TAG_EFFECT_LEVEL = "EffectAmplifier";
    public static final String TAG_EFFECT_DURATION = "EffectDuration";

    public static int getYear(Level world) {
        return world != null ? YEARS_START + (int) ((world.getGameTime() / 24000L) / DAYS_PER_YEAR) : YEARS_START;
    }

    public static int getDays(Level world) {
        return world != null ? (int) (world.getGameTime() / 24000L) : 0;
    }

    public static int getWineAge(ItemStack wine, Level world) {
        int y = hasWineYear(wine) ? getWineYear(wine) : YEARS_START;
        return Math.max(0, getYear(world) - y);
    }

    public static int getWineAgeDays(ItemStack wine, Level world) {
        int y = hasWineYear(wine) ? getWineYear(wine) : YEARS_START;
        return Math.max(0, getDays(world) - (y * DAYS_PER_YEAR));
    }

    public static int getEffectLevel(ItemStack wine, Level world) {
        int age = getWineAge(wine, world);
        return Math.max(0, Math.min(MAX_LEVEL, YEARS_PER_EFFECT_LEVEL > 0 ? age / YEARS_PER_EFFECT_LEVEL : 0));
    }

    public static int getEffectDuration(ItemStack wine, Level world) {
        int age = getWineAge(wine, world);
        return Math.min(MAX_DURATION, Math.max(0, START_DURATION + (DURATION_PER_YEAR * age)));
    }

    public static void setWineYear(ItemStack wine, Level world) {
        int year = world != null ? getYear(world) : YEARS_START;
        wine.getOrCreateTag().putInt(TAG_YEAR, year);
        refreshCached(wine, world);
    }

    public static void refreshCached(ItemStack wine, Level world) {
        int amplifier = getEffectLevel(wine, world);
        int duration = getEffectDuration(wine, world);
        CompoundTag tag = wine.getOrCreateTag();
        tag.putInt(TAG_EFFECT_LEVEL, amplifier);
        tag.putInt(TAG_EFFECT_DURATION, duration);
    }

    public static int getWineYear(ItemStack wine) {
        return wine.getOrCreateTag().getInt(TAG_YEAR);
    }

    public static boolean hasWineYear(ItemStack wine) {
        return wine.getOrCreateTag().contains(TAG_YEAR);
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public static CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = new CompoundTag();
        if (stack.getTag() != null) {
            if (stack.getTag().contains(TAG_YEAR)) {
                tag.putInt(TAG_YEAR, stack.getTag().getInt(TAG_YEAR));
            }
            if (stack.getTag().contains(TAG_EFFECT_LEVEL)) {
                tag.putInt(TAG_EFFECT_LEVEL, stack.getTag().getInt(TAG_EFFECT_LEVEL));
            }
            if (stack.getTag().contains(TAG_EFFECT_DURATION)) {
                tag.putInt(TAG_EFFECT_DURATION, stack.getTag().getInt(TAG_EFFECT_DURATION));
            }
        }
        return tag;
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public static void readShareTag(ItemStack stack, CompoundTag nbt) {
        if (nbt != null) {
            if (nbt.contains(TAG_YEAR)) {
                stack.getOrCreateTag().putInt(TAG_YEAR, nbt.getInt(TAG_YEAR));
            }
            if (nbt.contains(TAG_EFFECT_LEVEL)) {
                stack.getOrCreateTag().putInt(TAG_EFFECT_LEVEL, nbt.getInt(TAG_EFFECT_LEVEL));
            }
            if (nbt.contains(TAG_EFFECT_DURATION)) {
                stack.getOrCreateTag().putInt(TAG_EFFECT_DURATION, nbt.getInt(TAG_EFFECT_DURATION));
            }
        }
    }
}
