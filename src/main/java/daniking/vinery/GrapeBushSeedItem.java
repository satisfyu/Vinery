package daniking.vinery;

import daniking.vinery.util.GrapevineType;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;

public class GrapeBushSeedItem extends AliasedBlockItem {

    private final GrapevineType type;

    public GrapeBushSeedItem(Block block, Settings settings, GrapevineType type) {
        super(block, settings);
        this.type = type;
    }

    public GrapevineType getType() {
        return type;
    }
}
