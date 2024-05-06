package satisfyu.vinery.registry;

import de.cristelknight.doapi.common.util.datafixer.DataFixers;
import de.cristelknight.doapi.common.util.datafixer.StringPairs;
import satisfyu.vinery.Vinery;

public class DataFixerRegistry {

    public static void init(){
        StringPairs p = DataFixers.getOrCreate(Vinery.MOD_ID);
        p.add("flower_pot", "flower_pot_big");
        p.add("cherry_cabinet", "dark_cherry_cabinet");
        p.add("cherry_drawer", "dark_cherry_drawer");
        p.add("cherry_wood", "dark_cherry_wood");
        p.add("cherry_planks", "dark_cherry_planks");
        p.add("cherry_log", "dark_cherry_log");
        p.add("cherry_beam", "dark_cherry_beam");
        p.add("cherry_floorboard", "dark_cherry_floorboard");
        p.add("cherry_stairs", "dark_cherry_stairs");
        p.add("cherry_slab", "dark_cherry_slab");
        p.add("cherry_fence", "dark_cherry_fence");
        p.add("cherry_fence_gate", "dark_cherry_fence_gate");
        p.add("cherry_button", "dark_cherry_button");
        p.add("cherry_pressure_plate", "dark_cherry_pressure_plate");
        p.add("cherry_door", "dark_cherry_door");
        p.add("cherry_trapdoor", "dark_cherry_trapdoor");
        p.add("cherry_leaves", "dark_cherry_leaves");
        p.add("stripped_cherry_log", "stripped_dark_cherry_log");
        p.add("stripped_cherry_wood", "stripped_dark_cherry_wood");
        p.add("potted_cherry_tree_sapling", "potted_dark_cherry_tree_sapling");
        p.add("cherry_sapling", "dark_cherry_sapling");
        p.add("chair", "dark_cherry_chair");
        p.add("table", "dark_cherry_table");
        p.add("barrel", "dark_cherry_barrel");
        p.add("big_table", "dark_cherry_big_table");

        //p.add("cherry_wine_rack_big", "dark_cherry_wine_rack_big");
        //p.add("cherry_wine_rack_small", "dark_cherry_wine_rack_small");
        //p.add("cherry_wine_rack_mid", "dark_cherry_wine_rack_mid");
    }
}
