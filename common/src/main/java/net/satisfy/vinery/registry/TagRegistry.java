package net.satisfy.vinery.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.util.VineryIdentifier;

public class TagRegistry {
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.create(Registries.BLOCK, VineryIdentifier.of("can_not_connect"));
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registries.ITEM, VineryIdentifier.of("ignore_block_item"));
    public static final TagKey<Item> WINE = TagKey.create(Registries.ITEM, VineryIdentifier.of("wine"));
    public static final TagKey<Item> BASKET_BLACKLIST = TagKey.create(Registries.ITEM, VineryIdentifier.of("basket_blacklist"));
}
