package net.satisfy.vinery.util;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.Vinery;

public final class VineryIdentifier {
    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, path);
    }

    public static ResourceLocation asResourceLocation(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
