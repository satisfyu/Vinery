package satisfyu.vinery.world;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import satisfyu.vinery.feature.JungleRedBushFeature;
import satisfyu.vinery.feature.JungleWhiteBushFeature;

public class VineryFeatures {

    public static final Feature<NoneFeatureConfiguration> JUNGLE_RED_GRAPE_FEATURE = register("jungle_red_grape_feature", new JungleRedBushFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> JUNGLE_WHITE_GRAPE_FEATURE = register("jungle_white_grape_feature", new JungleWhiteBushFeature(NoneFeatureConfiguration.CODEC));

    public static void init(){
        VineryO.LOGGER.debug("Registering Features!");
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(BuiltInRegistries.FEATURE, name, feature);
    }

}
