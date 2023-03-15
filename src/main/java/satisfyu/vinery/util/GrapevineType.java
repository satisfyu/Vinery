package satisfyu.vinery.util;

import net.minecraft.util.StringIdentifiable;

public enum GrapevineType implements StringIdentifiable  {
    RED,
    WHITE,
    JUNGLE_RED,
    JUNGLE_WHITE,
    TAIGA_RED,
    TAIGA_WHITE,
    SAVANNA_RED,
    SAVANNA_WHITE;

    public String toString() {
        return this.asString();
    }
    @Override
    public String asString() {
        return this == RED ? "red" : "white";
    }

}
