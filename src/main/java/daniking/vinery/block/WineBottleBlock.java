package daniking.vinery.block;

import daniking.vinery.util.WineType;

public class WineBottleBlock extends StackableBlock {

    private final WineType type;
    public WineBottleBlock(Settings settings, WineType type) {
        super(settings);
        this.type = type;
    }

    public WineType getType() {
        return type;
    }
}
