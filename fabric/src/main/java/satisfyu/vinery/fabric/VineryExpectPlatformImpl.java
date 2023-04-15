package satisfyu.vinery.fabric;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import satisfyu.vinery.VineryExpectPlatform;

import java.nio.file.Path;

public class VineryExpectPlatformImpl {
    /**
     * This is our actual method to {@link VineryExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();

    }
}
