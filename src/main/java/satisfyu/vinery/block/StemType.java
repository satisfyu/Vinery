package satisfyu.vinery.block;

import net.minecraft.util.StringIdentifiable;

public enum StemType implements StringIdentifiable {
    PALE,
    LATTICE;

    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        return this == PALE ? "pale" : "lattice";
    }
}
