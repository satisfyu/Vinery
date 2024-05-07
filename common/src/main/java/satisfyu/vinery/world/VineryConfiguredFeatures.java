package satisfyu.vinery.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import satisfyu.vinery.util.VineryIdentifier;

public class VineryConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> DARK_CHERRY_KEY = registerKey("dark_cherry");
    public static final ResourceKey<ConfiguredFeature<?,?>> DARK_CHERRY_VARIANT_KEY = registerKey("dark_cherry_variant");
    public static final ResourceKey<ConfiguredFeature<?,?>> APPLE_KEY = registerKey("apple");
    public static final ResourceKey<ConfiguredFeature<?,?>> APPLE_VARIANT_KEY = registerKey("apple_variant");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new VineryIdentifier(name));
    }

}

