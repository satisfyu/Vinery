package satisfyu.vinery.forge;

import net.minecraftforge.fml.loading.FMLPaths;
import satisfyu.vinery.VineryExpectPlatform;

import java.nio.file.Path;

public class ExampleExpectPlatformImpl {
    /**
     * This is our actual method to {@link VineryExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
