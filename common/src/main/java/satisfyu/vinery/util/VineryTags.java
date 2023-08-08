package satisfyu.vinery.util;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryIdentifier;

public class VineryTags {

    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.create(Registry.BLOCK.key(), new VineryIdentifier("can_not_connect"));
    public static final TagKey<Block> WINE_RACK = TagKey.create(Registry.BLOCK.key(), new VineryIdentifier("wine_racks"));
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registry.ITEM.key(), new VineryIdentifier("ignore_block_item"));
    public static final TagKey<Item> CHERRY_LOGS = TagKey.create(Registry.ITEM.key(), new VineryIdentifier("cherry_logs"));
    public static final TagKey<Item> SMALL_FLOWER = TagKey.create(Registry.ITEM.key(), new VineryIdentifier( "small_flower"));
    public static final TagKey<Item> BIG_FLOWER = TagKey.create(Registry.ITEM.key(), new VineryIdentifier("big_flower"));
    public static final TagKey<Item> WINE = TagKey.create(Registry.ITEM.key(), new VineryIdentifier("wine"));
}
