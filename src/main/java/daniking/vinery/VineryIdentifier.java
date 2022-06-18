package daniking.vinery;

import net.minecraft.util.Identifier;


/**
 * Namespace for Vinery mod
 */
class VineryIdentifier extends Identifier {

    public VineryIdentifier(String path) {
        super(Vinery.MODID, path);
    }

    public static String asString(String path) {
        return (Vinery.MODID + ":" + path);
    }
}
