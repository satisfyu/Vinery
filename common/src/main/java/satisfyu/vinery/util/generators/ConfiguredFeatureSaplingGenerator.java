package satisfyu.vinery.util.generators;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public abstract class ConfiguredFeatureSaplingGenerator extends DynamicSaplingGenerator {

    protected abstract @Nullable ResourceKey<ConfiguredFeature<?, ?>> getTreeConfiguredFeature(RandomSource random, boolean bees);
    @Override
    protected @Nullable Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(ServerLevel world, RandomSource random, boolean bees) {
        return world.registryAccess().registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getHolder(getTreeConfiguredFeature(random, bees)).get();
    }
}