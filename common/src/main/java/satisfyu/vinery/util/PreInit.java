package satisfyu.vinery.util;

import satisfyu.vinery.registry.GrapeTypeRegistry;

public class PreInit {
    public static void preInit() {
        GrapeTypeRegistry.register();
    }
}