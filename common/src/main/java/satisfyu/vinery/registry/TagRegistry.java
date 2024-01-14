package satisfyu.vinery.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryIdentifier;

public class TagRegistry {

    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.create(Registries.BLOCK, new VineryIdentifier("can_not_connect"));
    public static final TagKey<Block> WINE_RACK = TagKey.create(Registries.BLOCK, new VineryIdentifier("wine_racks"));
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registries.ITEM, new VineryIdentifier("ignore_block_item"));
    public static final TagKey<Item> CHERRY_LOGS = TagKey.create(Registries.ITEM, new VineryIdentifier("cherry_logs"));
    public static final TagKey<Item> SMALL_FLOWER = TagKey.create(Registries.ITEM, new VineryIdentifier( "small_flower"));
    public static final TagKey<Item> WINE = TagKey.create(Registries.ITEM, new VineryIdentifier("wine"));
}
