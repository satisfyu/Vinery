package satisfyu.vinery.util;

import net.minecraft.resources.ResourceLocation;
import satisfyu.vinery.Vinery;

public class VineryIdentifier extends ResourceLocation {

    public VineryIdentifier(String path) {
        super(Vinery.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Vinery.MOD_ID + ":" + path);
    }
}
