package net.satisfy.vinery.util;

import net.satisfy.vinery.registry.GrapeTypeRegistry;

public class PreInit {
    public static void preInit() {
        GrapeTypeRegistry.register();
    }
}