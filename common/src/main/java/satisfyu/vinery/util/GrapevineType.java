package satisfyu.vinery.util;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import satisfyu.vinery.registry.ItemRegistry;

public enum GrapevineType implements IGrapevineType, StringRepresentable {
	NONE, RED, WHITE, JUNGLE_RED, JUNGLE_WHITE, TAIGA_RED, TAIGA_WHITE, SAVANNA_RED, SAVANNA_WHITE, TOMATO;

	public boolean isPaleType() {
		return !(this == JUNGLE_RED || this == JUNGLE_WHITE);
	}

	public String toString() {
		return this.getSerializedName();
	}

	@Override
	public String getSerializedName() {
		return switch (this) {
			case NONE -> "none";
			case RED -> "red";
			case WHITE -> "white";
			case JUNGLE_RED -> "jungle_red";
			case JUNGLE_WHITE -> "jungle_white";
			case TAIGA_RED -> "taiga_red";
			case TAIGA_WHITE -> "taiga_white";
			case SAVANNA_RED -> "savanna_red";
			case SAVANNA_WHITE -> "savanna_white";
			case TOMATO -> "tomato";
		};
	}

	public Item getFruit() {
		return switch (this) {
			case RED -> ItemRegistry.RED_GRAPE.get();
			case WHITE -> ItemRegistry.WHITE_GRAPE.get();
			case JUNGLE_RED -> ItemRegistry.JUNGLE_RED_GRAPE.get();
			case JUNGLE_WHITE -> ItemRegistry.JUNGLE_WHITE_GRAPE.get();
			case TAIGA_RED -> ItemRegistry.TAIGA_RED_GRAPE.get();
			case TAIGA_WHITE -> ItemRegistry.TAIGA_WHITE_GRAPE.get();
			case SAVANNA_RED -> ItemRegistry.SAVANNA_RED_GRAPE.get();
			case SAVANNA_WHITE -> ItemRegistry.SAVANNA_WHITE_GRAPE.get();
			default -> ItemRegistry.RED_GRAPE.get();
		};
	}

	public Item getSeeds() {
		return switch (this) {
			case RED -> ItemRegistry.RED_GRAPE_SEEDS.get();
			case WHITE -> ItemRegistry.WHITE_GRAPE_SEEDS.get();
			case JUNGLE_RED -> ItemRegistry.JUNGLE_RED_GRAPE_SEEDS.get();
			case JUNGLE_WHITE -> ItemRegistry.JUNGLE_WHITE_GRAPE_SEEDS.get();
			case TAIGA_RED -> ItemRegistry.TAIGA_RED_GRAPE_SEEDS.get();
			case TAIGA_WHITE -> ItemRegistry.TAIGA_WHITE_GRAPE_SEEDS.get();
			case SAVANNA_RED -> ItemRegistry.SAVANNA_RED_GRAPE_SEEDS.get();
			case SAVANNA_WHITE -> ItemRegistry.SAVANNA_WHITE_GRAPE_SEEDS.get();
			default -> ItemRegistry.RED_GRAPE_SEEDS.get();
		};
	}
}