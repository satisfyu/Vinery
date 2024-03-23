package satisfyu.vinery.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {
    public static void registerCompostable() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.WHITE_GRAPE.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.WHITE_GRAPE_SEEDS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.RED_GRAPE.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.RED_GRAPE_SEEDS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHERRY_LEAVES.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.GRAPEVINE_LEAVES.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHERRY.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROTTEN_CHERRY.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.APPLE_TREE_SAPLING.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.APPLE_LEAVES.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHERRY_SAPLING.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.APPLE_MASH.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.STRAW_HAT.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.JUNGLE_RED_GRAPE.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.JUNGLE_WHITE_GRAPE.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TAIGA_RED_GRAPE_SEEDS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TAIGA_RED_GRAPE.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TAIGA_WHITE_GRAPE.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SAVANNA_RED_GRAPE.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SAVANNA_WHITE_GRAPE.get(), 0.4F);
    }
}
