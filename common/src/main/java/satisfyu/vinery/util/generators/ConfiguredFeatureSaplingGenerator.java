package satisfyu.vinery.util.generators;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public abstract class ConfiguredFeatureSaplingGenerator extends DynamicSaplingGenerator {
	protected abstract @Nullable ResourceKey<ConfiguredFeature<?, ?>> getTreeConfiguredFeature(Random random, boolean bees);

	protected @Nullable Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(ServerLevel world, Random random, boolean bees) {
		return world.registryAccess().registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getHolder(
				getTreeConfiguredFeature(random, bees)).get();
	}
}