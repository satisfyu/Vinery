package satisfyu.vinery.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryIdentifier;

public class VineryTags {

    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.create(Registries.BLOCK, new VineryIdentifier("allows_cooking_on_pot"));
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.create(Registries.BLOCK, new VineryIdentifier("can_not_connect"));
    public static final TagKey<Block> WINE_RACK = TagKey.create(Registries.BLOCK, new VineryIdentifier("wine_racks"));
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registries.ITEM, new VineryIdentifier("ignore_block_item"));
    public static final TagKey<Item> CHERRY_LOGS = TagKey.create(Registries.ITEM, new VineryIdentifier("cherry_logs"));
    public static final TagKey<Item> JAMS = TagKey.create(Registries.ITEM, new VineryIdentifier("jams"));
    public static final TagKey<Item> SMALL_FLOWER = TagKey.create(Registries.ITEM, new VineryIdentifier( "small_flower"));
    public static final TagKey<Item> BIG_FLOWER = TagKey.create(Registries.ITEM, new VineryIdentifier("big_flower"));
    public static final TagKey<Item> WINE = TagKey.create(Registries.ITEM, new VineryIdentifier("wine"));
    public static final TagKey<Item> FAUCET = TagKey.create(Registries.ITEM, new VineryIdentifier("faucet"));
}
