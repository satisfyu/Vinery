package daniking.vinery.registry;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

public class VineryCompostableItems {

    public static void init() {
        registerCompostableItem(ObjectRegistry.WHITE_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.WHITE_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.RED_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.RED_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY_LEAVES, 0.4F);
        registerCompostableItem(ObjectRegistry.GRAPEVINE_LEAVES, 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY, 0.4F);
        registerCompostableItem(ObjectRegistry.OLD_CHERRY_SAPLING, 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY_SAPLING, 0.4F);
        registerCompostableItem(ObjectRegistry.APPLE_MASH, 0.4F);
        registerCompostableItem(ObjectRegistry.STRAW_HAT, 0.4F);

    }

    public static void registerCompostableItem(ItemConvertible item, float chance) {
        if (item.asItem() != Items.AIR) {
            ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), chance);
        }
    }
}
