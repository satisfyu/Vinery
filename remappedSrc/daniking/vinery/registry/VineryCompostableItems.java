package daniking.vinery.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

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

    public static void registerCompostableItem(ItemLike item, float chance) {
        if (item.asItem() != Items.AIR) {
            ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
        }
    }
}
