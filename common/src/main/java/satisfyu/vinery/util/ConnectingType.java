package satisfyu.vinery.util;

import net.minecraft.util.StringRepresentable;

public enum ConnectingType implements StringRepresentable {
    SINGLE("single"),
    BOTTOM("bottom"),
    MIDDLE("middle"),
    LEFT("left"),
    RIGHT("right"),
    TOP_LEFT("top_left"),
    TOP_RIGHT("top_right"),
    BOTTOM_LEFT("bottom_left"),
    BOTTOM_RIGHT("bottom_right");
    private final String name;

    ConnectingType(String type) {
        this.name = type;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
