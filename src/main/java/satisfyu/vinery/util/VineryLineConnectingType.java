package satisfyu.vinery.util;

import net.minecraft.util.StringIdentifiable;

public enum VineryLineConnectingType implements StringIdentifiable {
    NONE("none"),
    MIDDLE("middle"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    private VineryLineConnectingType(String type) {
        this.name = type;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
