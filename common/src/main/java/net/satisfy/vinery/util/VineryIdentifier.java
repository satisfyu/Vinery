package net.satisfy.vinery.util;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.Vinery;

public class VineryIdentifier extends ResourceLocation {

    public VineryIdentifier(String path) {
        super(Vinery.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Vinery.MOD_ID + ":" + path);
    }
}
