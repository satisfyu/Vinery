package net.satisfy.vinery.item;

import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.block.grape.GrapeType;

public class GrapeBushSeedItem extends ItemNameBlockItem {
    private final GrapeType type;

    public GrapeBushSeedItem(Block block, Properties settings, GrapeType type) {
        super(block, settings);
        this.type = type;
    }

    public GrapeType getType() {
        return this.type;
    }

}