package daniking.vinery.util;

import net.minecraft.util.StringIdentifiable;

public enum GrapevineType implements StringIdentifiable  {
    RED,
    WHITE;

    public String toString() {
        return this.asString();
    }
    @Override
    public String asString() {
        return this == RED ? "red" : "white";
    }

}
