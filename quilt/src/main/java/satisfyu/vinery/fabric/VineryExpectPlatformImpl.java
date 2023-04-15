package satisfyu.vinery.fabric;

import org.quiltmc.loader.api.QuiltLoader;
import satisfyu.vinery.VineryExpectPlatform;

import java.nio.file.Path;

public class VineryExpectPlatformImpl {
    /**
     * This is our actual method to {@link VineryExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }
}
