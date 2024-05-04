package satisfyu.vinery.registry;

import de.cristelknight.doapi.common.util.datafixer.DataFixers;
import de.cristelknight.doapi.common.util.datafixer.StringPairs;
import satisfyu.vinery.Vinery;

public class DataFixerRegistry {

    public static void init(){
        StringPairs p = DataFixers.getOrCreate(Vinery.MOD_ID);
        p.add("flower_pot", "flower_pot_big");
    }
}
