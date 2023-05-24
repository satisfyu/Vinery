package satisfyu.vinery.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class VineryCompostableItems {

    public static void init() {
        registerCompostableItem(ObjectRegistry.WHITE_GRAPE.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.WHITE_GRAPE_SEEDS.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.RED_GRAPE.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.RED_GRAPE_SEEDS.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY_LEAVES.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.GRAPEVINE_LEAVES.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.ROTTEN_CHERRY.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.APPLE_TREE_SAPLING.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.APPLE_LEAVES.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY_SAPLING.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.APPLE_MASH.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.STRAW_HAT.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.JUNGLE_RED_GRAPE.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.JUNGLE_WHITE_GRAPE.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.TAIGA_RED_GRAPE_SEEDS.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.TAIGA_RED_GRAPE.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.TAIGA_WHITE_GRAPE.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.SAVANNA_RED_GRAPE.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS.get(), 0.4F);
        registerCompostableItem(ObjectRegistry.SAVANNA_WHITE_GRAPE.get(), 0.4F);


    }

    public static void registerCompostableItem(ItemLike item, float chance) {
        if (item.asItem() != Items.AIR) {
            ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
        }
    }
}
