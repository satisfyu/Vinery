package satisfyu.vinery.util;

import net.minecraft.util.StringIdentifiable;

public enum GrapevineType implements StringIdentifiable  {
    NONE,
    RED,
    WHITE,
    JUNGLE_RED,
    JUNGLE_WHITE,
    TAIGA_RED,
    TAIGA_WHITE,
    SAVANNA_RED,
    SAVANNA_WHITE;


    public boolean isPaleType() {
        System.out.println(this);
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
        };
    }
}