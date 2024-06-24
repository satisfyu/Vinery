package net.satisfy.vinery.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.config.VineryConfig;

public class WineYears {
	public static final int YEARS_START = 0;
	public static final int MAX_LEVEL = 5;

	public static int getYear(Level world) {
		return world != null ? YEARS_START + (int) (world.getDayTime() / 24000 / VineryConfig.DEFAULT.getConfig().yearLengthInDays()) : YEARS_START;
	}

	public static int getEffectLevel(ItemStack wine, Level world) {
		return Math.max(0, Math.min(MAX_LEVEL, getWineAge(wine, world) / VineryConfig.DEFAULT.getConfig().yearsPerEffectLevel()));
	}

	public static int getWineAge(ItemStack wine, Level world) {
		return getYear(world) - getWineYear(wine, world);
	}

	public static void setWineYear(ItemStack wine, Level world) {
		wine.getOrCreateTag().putInt("Year", getYear(world));
	}

	public static int getWineYear(ItemStack wine, Level world) {
		CompoundTag nbt = wine.getOrCreateTag();
		if (!nbt.contains("Year")) setWineYear(wine, world);
		return nbt.getInt("Year");
	}
}