package satisfyu.vinery.world;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.feature.JungleRedBushFeature;
import satisfyu.vinery.feature.JungleWhiteBushFeature;

import java.util.function.Supplier;

public class VineryFeatures {

    private static final Registrar<Feature<?>> FEATURES = Vinery.REGISTRIES.get(Registries.FEATURE);
    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> JUNGLE_RED_GRAPE_FEATURE = register("jungle_red_grape_feature", () -> new JungleRedBushFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> JUNGLE_WHITE_GRAPE_FEATURE = register("jungle_white_grape_feature", () -> new JungleWhiteBushFeature(NoneFeatureConfiguration.CODEC));

    public static void init(){
        Vinery.LOGGER.debug("Registering Features!");
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistrySupplier<F> register(String name, Supplier<F> feature) {
        return FEATURES.register(new VineryIdentifier(name), feature);
    }

}
