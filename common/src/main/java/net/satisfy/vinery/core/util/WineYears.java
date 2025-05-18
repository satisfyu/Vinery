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
		return YEARS_START + (world != null ? (int) (world.getDayTime() / 24000 / DAYS_PER_YEAR) : 0);
	}

	public static int getDays(Level world) {
		return YEARS_START * DAYS_PER_YEAR + (world != null ? (int)(world.getDayTime() / 24000) : 0);
	}

	public static int getEffectLevel(ItemStack wine, Level world) {
		if (wine.getOrCreateTag().contains(TAG_EFFECT_LEVEL)) {
			return wine.getOrCreateTag().getInt(TAG_EFFECT_LEVEL);
		}
		int calculated = Math.max(0, Math.min(MAX_LEVEL, getWineAge(wine, world) / YEARS_PER_EFFECT_LEVEL));
		wine.getOrCreateTag().putInt(TAG_EFFECT_LEVEL, calculated);
		return calculated;
	}

	public static int getWineAge(ItemStack wine, Level world) {
		return getYear(world) - (!hasWineYear(wine) ? getWineYear(wine) : 0);
	}

	public static int getWineAgeDays(ItemStack wine, Level world) {
		return getDays(world) - ((!hasWineYear(wine) ? getWineYear(wine) : 0) * DAYS_PER_YEAR);
	}

	public static void setWineYear(ItemStack wine, Level world) {
		int year = world != null ? getYear(world) : YEARS_START;
		wine.getOrCreateTag().putInt(TAG_YEAR, year);
		int age = getYear(world) - year;
		int amplifier = Math.max(0, Math.min(MAX_LEVEL, age / YEARS_PER_EFFECT_LEVEL));
		int duration = Math.min(START_DURATION + (DURATION_PER_YEAR * age), MAX_DURATION);
		wine.getOrCreateTag().putInt(TAG_EFFECT_LEVEL, amplifier);
		wine.getOrCreateTag().putInt(TAG_EFFECT_DURATION, duration);
	}

	public static int getWineYear(ItemStack wine) {
		return wine.getOrCreateTag().getInt("Year");
	}

	public static int getEffectDuration(ItemStack wine, Level world) {
		if (wine.getOrCreateTag().contains(TAG_EFFECT_DURATION)) {
			return wine.getOrCreateTag().getInt(TAG_EFFECT_DURATION);
		}
		int age = getWineAge(wine, world);
		int calculated = Math.min(START_DURATION + (DURATION_PER_YEAR * age), MAX_DURATION);
		wine.getOrCreateTag().putInt(TAG_EFFECT_DURATION, calculated);
		return calculated;
	}

	public static boolean hasWineYear(ItemStack wine) {
		return !wine.getOrCreateTag().contains(TAG_YEAR);
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
