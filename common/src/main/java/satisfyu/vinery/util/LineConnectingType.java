package satisfyu.vinery.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum LineConnectingType implements StringRepresentable {
    NONE("none"),
    MIDDLE("middle"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    LineConnectingType(String type) {
        this.name = type;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
