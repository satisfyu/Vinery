package satisfyu.vinery.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryIdentifier;

public class VineryTags {

    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.create(BuiltInRegistries.BLOCK.key(), new VineryIdentifier("allows_cooking_on_pot"));
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.create(BuiltInRegistries.BLOCK.key(), new VineryIdentifier("can_not_connect"));
    public static final TagKey<Block> WINE_RACK = TagKey.create(BuiltInRegistries.BLOCK.key(), new VineryIdentifier("wine_racks"));
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(BuiltInRegistries.ITEM.key(), new VineryIdentifier("ignore_block_item"));
    public static final TagKey<Item> CHERRY_LOGS = TagKey.create(BuiltInRegistries.ITEM.key(), new VineryIdentifier("cherry_logs"));
    public static final TagKey<Item> JAMS = TagKey.create(BuiltInRegistries.ITEM.key(), new VineryIdentifier("jams"));
    public static final TagKey<Item> SMALL_FLOWER = TagKey.create(BuiltInRegistries.ITEM.key(), new VineryIdentifier( "small_flower"));
    public static final TagKey<Item> BIG_FLOWER = TagKey.create(BuiltInRegistries.ITEM.key(), new VineryIdentifier("big_flower"));
    public static final TagKey<Item> WINE = TagKey.create(BuiltInRegistries.ITEM.key(), new VineryIdentifier("wine"));
}
