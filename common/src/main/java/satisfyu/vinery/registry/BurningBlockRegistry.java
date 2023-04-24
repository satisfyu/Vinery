package satisfyu.vinery.registry;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BurningBlockRegistry {
    private static Map<Block, Pair<Integer, Integer>> INSTANCE = new HashMap<>();

    public static void add(int burnOdd, int igniteOdd, Block... blocks){
        for(Block b : blocks) INSTANCE.put(b, new Pair<>(burnOdd, igniteOdd));
    }

    public static Map<Block, Pair<Integer, Integer>> getInstance(){
        return INSTANCE;
    }

    public static int getIgniteOdd(Block block){
        return INSTANCE.get(block).getSecond();
    }

    public static int getBurnOdd(Block block){
        return INSTANCE.get(block).getFirst();
    }

}
