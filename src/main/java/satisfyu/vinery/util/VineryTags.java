package satisfyu.vinery.util;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import satisfyu.vinery.VineryIdentifier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class VineryTags {

    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.of(Registries.BLOCK.getKey(), new VineryIdentifier("allows_cooking_on_pot"));
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.of(Registries.BLOCK.getKey(), new VineryIdentifier("can_not_connect"));
    public static final TagKey<Block> WINE_RACK = TagKey.of(Registries.BLOCK.getKey(), new VineryIdentifier("wine_racks"));
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.of(Registries.ITEM.getKey(), new VineryIdentifier("ignore_block_item"));
    public static final TagKey<Item> CHERRY_LOGS = TagKey.of(Registries.ITEM.getKey(), new VineryIdentifier("cherry_logs"));
    public static final TagKey<Item> JAMS = TagKey.of(Registries.ITEM.getKey(), new VineryIdentifier("jams"));
    public static final TagKey<Item> SMALL_FLOWER = TagKey.of(Registries.ITEM.getKey(), new VineryIdentifier( "small_flower"));
    public static final TagKey<Item> BIG_FLOWER = TagKey.of(Registries.ITEM.getKey(), new VineryIdentifier("big_flower"));
    public static final TagKey<Item> WINE = TagKey.of(Registries.ITEM.getKey(), new VineryIdentifier("wine"));
}
