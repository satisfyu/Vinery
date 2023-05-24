package satisfyu.vinery.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class VineryCompostableItems {
	public static void init() {
		registerCompostableItem(ItemRegistry.WHITE_GRAPE.get(), 0.4F);
		registerCompostableItem(ItemRegistry.WHITE_GRAPE_SEEDS.get(), 0.4F);
		registerCompostableItem(ItemRegistry.RED_GRAPE.get(), 0.4F);
		registerCompostableItem(ItemRegistry.RED_GRAPE_SEEDS.get(), 0.4F);
		registerCompostableItem(BlockRegistry.CHERRY_LEAVES.get(), 0.4F);
		registerCompostableItem(BlockRegistry.GRAPEVINE_LEAVES.get(), 0.4F);
		registerCompostableItem(ItemRegistry.CHERRY.get(), 0.4F);
		registerCompostableItem(BlockRegistry.OLD_CHERRY_SAPLING.get(), 0.4F);
		registerCompostableItem(BlockRegistry.CHERRY_SAPLING.get(), 0.4F);
		registerCompostableItem(ItemRegistry.APPLE_MASH.get(), 0.4F);
		registerCompostableItem(ItemRegistry.STRAW_HAT.get(), 0.4F);
		registerCompostableItem(ItemRegistry.JUNGLE_RED_GRAPE_SEEDS.get(), 0.4F);
		registerCompostableItem(ItemRegistry.JUNGLE_RED_GRAPE.get(), 0.4F);
		registerCompostableItem(ItemRegistry.JUNGLE_WHITE_GRAPE_SEEDS.get(), 0.4F);
		registerCompostableItem(ItemRegistry.JUNGLE_WHITE_GRAPE.get(), 0.4F);
		registerCompostableItem(ItemRegistry.TAIGA_RED_GRAPE_SEEDS.get(), 0.4F);
		registerCompostableItem(ItemRegistry.TAIGA_RED_GRAPE.get(), 0.4F);
		registerCompostableItem(ItemRegistry.TAIGA_WHITE_GRAPE_SEEDS.get(), 0.4F);
		registerCompostableItem(ItemRegistry.TAIGA_WHITE_GRAPE.get(), 0.4F);
		registerCompostableItem(ItemRegistry.SAVANNA_RED_GRAPE_SEEDS.get(), 0.4F);
		registerCompostableItem(ItemRegistry.SAVANNA_RED_GRAPE.get(), 0.4F);
		registerCompostableItem(ItemRegistry.SAVANNA_WHITE_GRAPE_SEEDS.get(), 0.4F);
		registerCompostableItem(ItemRegistry.SAVANNA_WHITE_GRAPE.get(), 0.4F);
	}

	public static void registerCompostableItem(ItemLike item, float chance) {
		if (item.asItem() != Items.AIR) {
			ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
		}
	}
}
