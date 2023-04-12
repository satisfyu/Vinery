package satisfyu.vinery.world;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.feature.JungleRedBushFeature;
import satisfyu.vinery.feature.JungleWhiteBushFeature;

public class VineryFeatures {

    public static final Feature<DefaultFeatureConfig> JUNGLE_RED_GRAPE_FEATURE = register("jungle_red_grape_feature", new JungleRedBushFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> JUNGLE_WHITE_GRAPE_FEATURE = register("jungle_white_grape_feature", new JungleWhiteBushFeature(DefaultFeatureConfig.CODEC));

    public static void init(){
        Vinery.LOGGER.debug("Registering Features!");
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, name, feature);
    }

}
