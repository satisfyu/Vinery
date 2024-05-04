package satisfyu.vinery.world;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.util.VineryIdentifier;

import java.util.function.Supplier;

public class VineryFeatures {

    private static final Registrar<Feature<?>> FEATURES = DeferredRegister.create(Vinery.MOD_ID, Registries.FEATURE).getRegistrar();
    public static final RegistrySupplier<Feature<BlockStateConfiguration>> JUNGLE_GRAPE_FEATURE = register("jungle_grape_feature", () -> new JungleGrapeFeature(BlockStateConfiguration.CODEC));
    public static void init(){
        Vinery.LOGGER.debug("Registering Features!");
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistrySupplier<F> register(String name, Supplier<F> feature) {
        return FEATURES.register(new VineryIdentifier(name), feature);
    }

}
