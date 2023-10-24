package satisfyu.vinery.util;

import satisfyu.vinery.registry.GrapeTypes;

public class VineryPre {
    public static void preInit() {
        GrapeTypes.register();
    }
}
