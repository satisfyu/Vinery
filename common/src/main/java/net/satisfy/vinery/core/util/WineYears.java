package net.satisfy.vinery.core.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.core.components.WineYearComponent;
import net.satisfy.vinery.core.registry.DataComponentRegistry;

public class WineYears {
    public static final int YEARS_START = 0;
    public static final int MAX_LEVEL = net.satisfy.vinery.platform.PlatformHelper.getWineMaxLevel();
    public static final int START_DURATION = net.satisfy.vinery.platform.PlatformHelper.getWineStartDuration();
    public static final int DURATION_PER_YEAR = net.satisfy.vinery.platform.PlatformHelper.getWineDurationPerYear();
    public static final int DAYS_PER_YEAR = net.satisfy.vinery.platform.PlatformHelper.getWineDaysPerYear();
    public static final int YEARS_PER_EFFECT_LEVEL = net.satisfy.vinery.platform.PlatformHelper.getWineYearsPerEffectLevel();
    public static final int MAX_DURATION = net.satisfy.vinery.platform.PlatformHelper.getWineMaxDuration();

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
        int amplifier = getEffectLevel(wine, world);
        int duration = getEffectDuration(wine, world);
        wine.set(DataComponentRegistry.WINE_YEAR.get(), new WineYearComponent(year, amplifier, duration));
    }

    public static void refreshCached(ItemStack wine, Level world) {
        WineYearComponent existing = wine.get(DataComponentRegistry.WINE_YEAR.get());
        int year = existing != null ? existing.year() : getYear(world);
        int amplifier = getEffectLevel(wine, world);
        int duration = getEffectDuration(wine, world);
        wine.set(DataComponentRegistry.WINE_YEAR.get(), new WineYearComponent(year, amplifier, duration));
    }

    public static int getWineYear(ItemStack wine) {
        WineYearComponent comp = wine.get(DataComponentRegistry.WINE_YEAR.get());
        return comp != null ? comp.year() : YEARS_START;
    }

    public static boolean hasWineYear(ItemStack wine) {
        return wine.get(DataComponentRegistry.WINE_YEAR.get()) != null;
    }
}