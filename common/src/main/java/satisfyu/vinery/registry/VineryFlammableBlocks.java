package satisfyu.vinery.registry;

import de.cristelknight.doapi.DoApiExpectPlatform;
import net.minecraft.world.level.block.Block;

public class VineryFlammableBlocks {

    public static void init(){
        add(5, 20, ObjectRegistry.CHERRY_PLANKS.get(), ObjectRegistry.CHERRY_SLAB.get(), ObjectRegistry.CHERRY_STAIRS.get(), ObjectRegistry.CHERRY_FENCE.get(),
                ObjectRegistry.CHERRY_FENCE_GATE.get());

        add(5, 5, ObjectRegistry.STRIPPED_CHERRY_LOG.get(), ObjectRegistry.STRIPPED_OLD_CHERRY_LOG.get(), ObjectRegistry.CHERRY_LOG.get(), ObjectRegistry.OLD_CHERRY_LOG.get(),
                ObjectRegistry.STRIPPED_CHERRY_WOOD.get(), ObjectRegistry.CHERRY_WOOD.get(), ObjectRegistry.OLD_CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_OLD_CHERRY_WOOD.get());

        add(30, 60, ObjectRegistry.CHERRY_LEAVES.get(), ObjectRegistry.GRAPEVINE_LEAVES.get());
    }

    private static void add(int burnOdd, int igniteOdd, Block... blocks){
        DoApiExpectPlatform.addFlammable(burnOdd, igniteOdd, blocks);
    }

}
