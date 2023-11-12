package satisfyu.vinery.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class VineryCompostables {
    public static void registerCompostable() {
        registerCompostableItem(ObjectRegistry.WHITE_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.WHITE_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.RED_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.RED_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY_LEAVES, 0.4F);
        registerCompostableItem(ObjectRegistry.GRAPEVINE_LEAVES, 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY, 0.4F);
        registerCompostableItem(ObjectRegistry.ROTTEN_CHERRY, 0.4F);
        registerCompostableItem(ObjectRegistry.APPLE_TREE_SAPLING, 0.4F);
        registerCompostableItem(ObjectRegistry.APPLE_LEAVES, 0.4F);
        registerCompostableItem(ObjectRegistry.CHERRY_SAPLING, 0.4F);
        registerCompostableItem(ObjectRegistry.APPLE_MASH, 0.4F);
        registerCompostableItem(ObjectRegistry.STRAW_HAT, 0.4F);
        registerCompostableItem(ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.JUNGLE_RED_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.JUNGLE_WHITE_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.TAIGA_RED_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.TAIGA_RED_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.TAIGA_WHITE_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.SAVANNA_RED_GRAPE, 0.4F);
        registerCompostableItem(ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS, 0.4F);
        registerCompostableItem(ObjectRegistry.SAVANNA_WHITE_GRAPE, 0.4F);
    }

    public static <T extends ItemLike> void registerCompostableItem(RegistrySupplier<T> item, float chance) {
        if (item.get().asItem() != Items.AIR) {
            ComposterBlock.COMPOSTABLES.put(item.get(), chance);
        }
    }
}
