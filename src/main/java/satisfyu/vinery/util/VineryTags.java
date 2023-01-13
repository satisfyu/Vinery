package satisfyu.vinery.util;

import satisfyu.vinery.VineryIdentifier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class VineryTags {

    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.of(Registry.BLOCK_KEY, new VineryIdentifier("allows_cooking_on_pot"));
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.of(Registry.BLOCK_KEY, new VineryIdentifier("can_not_connect"));

    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.of(Registry.ITEM_KEY, new VineryIdentifier("ignore_block_item"));
    public static final TagKey<Item> CHERRY_LOGS = TagKey.of(Registry.ITEM_KEY, new VineryIdentifier("cherry_logs"));
    public static final TagKey<Item> JAMS = TagKey.of(Registry.ITEM_KEY, new VineryIdentifier("jams"));
    public static final TagKey<Block> WINE_RACK = TagKey.of(Registry.BLOCK_KEY, new VineryIdentifier("wine_racks"));
}
