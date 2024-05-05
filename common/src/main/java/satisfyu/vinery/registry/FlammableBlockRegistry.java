package satisfyu.vinery.registry;

import de.cristelknight.doapi.DoApiCommonEP;
import net.minecraft.world.level.block.Block;

public class FlammableBlockRegistry {

    public static void init(){
        add(5, 20, ObjectRegistry.DARK_CHERRY_PLANKS.get(), ObjectRegistry.DARK_CHERRY_SLAB.get(), ObjectRegistry.DARK_CHERRY_STAIRS.get(), ObjectRegistry.DARK_CHERRY_FENCE.get(),
                ObjectRegistry.DARK_CHERRY_FENCE_GATE.get());

        add(5, 5, ObjectRegistry.STRIPPED_DARK_CHERRY_LOG.get(), ObjectRegistry.DARK_CHERRY_LOG.get(), ObjectRegistry.APPLE_LOG.get(),
                ObjectRegistry.STRIPPED_DARK_CHERRY_WOOD.get(), ObjectRegistry.DARK_CHERRY_WOOD.get(), ObjectRegistry.APPLE_WOOD.get());

        add(30, 60, ObjectRegistry.DARK_CHERRY_LEAVES.get(), ObjectRegistry.GRAPEVINE_LEAVES.get());
    }

    private static void add(int burnOdd, int igniteOdd, Block... blocks){
        DoApiCommonEP.addFlammable(burnOdd, igniteOdd, blocks);
    }

}
