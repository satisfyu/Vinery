package satisfyu.vinery.util;

import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;
import satisfyu.vinery.registry.ObjectRegistry;

public enum GrapevineType implements IGrapevineType, StringIdentifiable {
    NONE,
    RED,
    WHITE,
    JUNGLE_RED,
    JUNGLE_WHITE,
    TAIGA_RED,
    TAIGA_WHITE,
    SAVANNA_RED,
    SAVANNA_WHITE,
    TOMATO;

    public boolean isPaleType() {
        return !(this == JUNGLE_RED || this == JUNGLE_WHITE);
    }

    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
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
            case RED -> ObjectRegistry.RED_GRAPE;
            case WHITE -> ObjectRegistry.WHITE_GRAPE;
            case JUNGLE_RED -> ObjectRegistry.JUNGLE_RED_GRAPE;
            case JUNGLE_WHITE -> ObjectRegistry.JUNGLE_WHITE_GRAPE;
            case TAIGA_RED -> ObjectRegistry.TAIGA_RED_GRAPE;
            case TAIGA_WHITE -> ObjectRegistry.TAIGA_WHITE_GRAPE;
            case SAVANNA_RED -> ObjectRegistry.SAVANNA_RED_GRAPE;
            case SAVANNA_WHITE -> ObjectRegistry.SAVANNA_WHITE_GRAPE;
            case TOMATO -> ObjectRegistry.TOMATO;
            default -> ObjectRegistry.RED_GRAPE;
        };
    }

    public Item getSeeds() {
        return switch (this) {
            case RED -> ObjectRegistry.RED_GRAPE_SEEDS;
            case WHITE -> ObjectRegistry.WHITE_GRAPE_SEEDS;
            case JUNGLE_RED -> ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS;
            case JUNGLE_WHITE -> ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS;
            case TAIGA_RED -> ObjectRegistry.TAIGA_RED_GRAPE_SEEDS;
            case TAIGA_WHITE -> ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS;
            case SAVANNA_RED -> ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS;
            case SAVANNA_WHITE -> ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS;
            case TOMATO -> ObjectRegistry.TOMATO_SEEDS;
            default -> ObjectRegistry.RED_GRAPE_SEEDS;
        };
    }
}