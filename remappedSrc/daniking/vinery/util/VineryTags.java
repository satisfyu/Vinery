package daniking.vinery.util;

import daniking.vinery.VineryIdentifier;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class VineryTags {

    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.create(Registry.BLOCK_REGISTRY, new VineryIdentifier("allows_cooking_on_pot"));
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.create(Registry.BLOCK_REGISTRY, new VineryIdentifier("can_not_connect"));

    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registry.ITEM_REGISTRY, new VineryIdentifier("ignore_block_item"));
    public static final TagKey<Item> CHERRY_LOGS = TagKey.create(Registry.ITEM_REGISTRY, new VineryIdentifier("cherry_logs"));
    public static final TagKey<Item> JAMS = TagKey.create(Registry.ITEM_REGISTRY, new VineryIdentifier("jams"));
    public static final TagKey<Block> WINE_RACK = TagKey.create(Registry.BLOCK_REGISTRY, new VineryIdentifier("wine_racks"));
}
