package daniking.vinery.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WineYears {
	public static final int DAYS_PER_YEAR = 16;
	public static final int YEARS_START = 0;
	public static final int YEARS_PER_EFFECT_LEVEL = 4;
	public static final int MAX_LEVEL = 5;
	
	public static int getYear(Level world) {
		return YEARS_START + (int) (world.getDayTime() / 24000 / DAYS_PER_YEAR);
	}
	
	public static int getEffectLevel(ItemStack wine, Level world) {
		return Math.max(0, Math.min(MAX_LEVEL, getWineAge(wine, world) / YEARS_PER_EFFECT_LEVEL));
	}
	
	public static int getWineAge(ItemStack wine, Level world) {
		int wineYear = getWineYear(wine);

		// creative/command wine fix (isn't the best solution)
		if(wineYear == 0){
			setWineYear(wine, world);
			wineYear = getWineYear(wine);
		}

		return getYear(world) - wineYear;
	}
	
	public static void setWineYear(ItemStack wine, Level world) {
		wine.getOrCreateTag().putInt("Year", getYear(world));
	}
	
	public static int getWineYear(ItemStack wine) {
		return wine.getOrCreateTag().getInt("Year");
	}
}