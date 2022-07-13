package daniking.vinery.util;

public enum WineType {
    RED,
    WHITE;

    @Override
    public String toString() {
        return switch (this) {
            case RED -> "red";
            case WHITE -> "white";
        };
    }
}
