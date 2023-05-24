package satisfyu.vinery.registry;

import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryExpectPlatform;

public class VineryFlammableBlocks {
	public static void init() {
		add(5, 20, BlockRegistry
.CHERRY_PLANKS.get(), BlockRegistry.CHERRY_SLAB.get(),
				BlockRegistry.CHERRY_STAIRS.get(), BlockRegistry.CHERRY_FENCE.get(),
				BlockRegistry.CHERRY_FENCE_GATE.get());
		add(5, 5, BlockRegistry.STRIPPED_CHERRY_LOG.get(), BlockRegistry.STRIPPED_OLD_CHERRY_LOG.get(),
				BlockRegistry.CHERRY_LOG.get(), BlockRegistry.OLD_CHERRY_LOG.get(),
				BlockRegistry.STRIPPED_CHERRY_WOOD.get(), BlockRegistry.CHERRY_WOOD.get(),
				BlockRegistry.OLD_CHERRY_WOOD.get(), BlockRegistry.STRIPPED_OLD_CHERRY_WOOD.get());
		add(30, 60, BlockRegistry.CHERRY_LEAVES.get(), BlockRegistry.GRAPEVINE_LEAVES.get());
	}

	private static void add(int burnOdd, int igniteOdd, Block... blocks) {
		VineryExpectPlatform.addFlammable(burnOdd, igniteOdd, blocks);
	}
}
