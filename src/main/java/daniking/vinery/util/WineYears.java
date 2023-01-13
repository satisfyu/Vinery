package daniking.vinery.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class WineYears {
	public static final int DAYS_PER_YEAR = 16;
	public static final int YEARS_START = 0;
	public static final int YEARS_PER_EFFECT_LEVEL = 4;
	public static final int MAX_LEVEL = 5;
	
	public static int getYear(World world) {
		return YEARS_START + (int) (world.getTimeOfDay() / 24000 / DAYS_PER_YEAR);
	}
	
	public static int getEffectLevel(ItemStack wine, World world) {
		return Math.max(0, Math.min(MAX_LEVEL, getWineAge(wine, world) / YEARS_PER_EFFECT_LEVEL));
	}
	
	public static int getWineAge(ItemStack wine, World world) {
		return getYear(world) - getWineYear(wine, world);
	}
	
	public static void setWineYear(ItemStack wine, World world) {
		wine.getOrCreateNbt().putInt("Year", getYear(world));
	}
	
	public static int getWineYear(ItemStack wine, World world) {
		NbtCompound compound = wine.getOrCreateNbt();
		if(compound.contains("Year")) return compound.getInt("Year");
		setWineYear(wine, world);
		return getWineYear(wine, world);
	}
}