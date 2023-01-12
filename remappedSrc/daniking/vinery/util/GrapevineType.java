package daniking.vinery.util;

import net.minecraft.util.StringRepresentable;

public enum GrapevineType implements StringRepresentable  {
    RED,
    WHITE;

    public String toString() {
        return this.getSerializedName();
    }
    @Override
    public String getSerializedName() {
        return this == RED ? "red" : "white";
    }

}
