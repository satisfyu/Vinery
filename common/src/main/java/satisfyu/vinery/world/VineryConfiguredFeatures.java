package satisfyu.vinery.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.CherryLeaves;
import satisfyu.vinery.block.grape.GrapeBush;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

import static satisfyu.vinery.block.CherryLeaves.VARIANT;


public class VineryConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> CHERRY_KEY = registerKey("cherry");
    public static final ResourceKey<ConfiguredFeature<?,?>> CHERRY_VARIANT_KEY = registerKey("cherry_variant");
    public static final ResourceKey<ConfiguredFeature<?,?>> OLD_CHERRY_KEY = registerKey("old_cherry");
    public static final ResourceKey<ConfiguredFeature<?,?>> OLD_CHERRY_BEE_KEY = registerKey("old_cherry_bee");
    public static final ResourceKey<ConfiguredFeature<?,?>> OLD_CHERRY_VARIANT_KEY = registerKey("old_cherry_variant");
    public static final ResourceKey<ConfiguredFeature<?,?>> OLD_CHERRY_VARIANT_WITH_BEE_KEY = registerKey("old_cherry_variant_with_bee");


    public static final ResourceKey<ConfiguredFeature<?,?>> RED_GRAPE_BUSH_PATCH_KEY = registerKey("red_grape_bush");
    public static final ResourceKey<ConfiguredFeature<?,?>> WHITE_GRAPE_BUSH_PATCH_KEY = registerKey("white_grape_bush");
    public static final ResourceKey<ConfiguredFeature<?,?>> TAIGA_RED_GRAPE_BUSH_PATCH_KEY = registerKey("taiga_red_grape_bush");
    public static final ResourceKey<ConfiguredFeature<?,?>> TAIGA_WHITE_GRAPE_BUSH_PATCH_KEY = registerKey("taiga_white_grape_bush");
    public static final ResourceKey<ConfiguredFeature<?,?>> SAVANNA_RED_GRAPE_BUSH_PATCH_KEY = registerKey("savanna_red_grape_bush");
    public static final ResourceKey<ConfiguredFeature<?,?>> SAVANNA_WHITE_GRAPE_BUSH_PATCH_KEY = registerKey("savanna_white_grape_bush");
    public static final ResourceKey<ConfiguredFeature<?,?>> JUNGLE_RED_GRAPE_BUSH_PATCH_KEY = registerKey("jungle_red_grape_bush_patch");
    public static final ResourceKey<ConfiguredFeature<?,?>> JUNGLE_WHITE_GRAPE_BUSH_PATCH_KEY = registerKey("jungle_white_grape_bush_patch");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> c) {
        register(c, CHERRY_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ObjectRegistry.CHERRY_LOG.get()), new StraightTrunkPlacer(5, 2, 0), cherryLeaveProvider(), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        register(c, CHERRY_VARIANT_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ObjectRegistry.CHERRY_LOG.get()), new StraightTrunkPlacer(5, 2, 0), cherryLeaveProvider(), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        register(c, OLD_CHERRY_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ObjectRegistry.OLD_CHERRY_LOG.get()), new FancyTrunkPlacer(4, 14, 2), cherryLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
        register(c, OLD_CHERRY_BEE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ObjectRegistry.OLD_CHERRY_LOG.get()), new FancyTrunkPlacer(4, 14, 2), cherryLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveDecorator(0.5f))).forceDirt().build());
        register(c, OLD_CHERRY_VARIANT_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ObjectRegistry.OLD_CHERRY_LOG.get()), new FancyTrunkPlacer(4, 14, 2), cherryLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
        register(c, OLD_CHERRY_VARIANT_WITH_BEE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ObjectRegistry.OLD_CHERRY_LOG.get()), new FancyTrunkPlacer(4, 14, 2), cherryLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveDecorator(0.5f))).forceDirt().build());


        register(c, RED_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.RED_GRAPE_BUSH.get(), 3));
        register(c, WHITE_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.WHITE_GRAPE_BUSH.get(), 3));

        register(c, TAIGA_RED_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.TAIGA_RED_GRAPE_BUSH.get(), 2));
        register(c, TAIGA_WHITE_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.TAIGA_WHITE_GRAPE_BUSH.get(), 2));

        register(c, SAVANNA_RED_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.SAVANNA_RED_GRAPE_BUSH.get(), 3));
        register(c, SAVANNA_WHITE_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.SAVANNA_WHITE_GRAPE_BUSH.get(), 3));


        register(c, JUNGLE_RED_GRAPE_BUSH_PATCH_KEY, VineryFeatures.JUNGLE_RED_GRAPE_FEATURE.get(), new NoneFeatureConfiguration());
        register(c, JUNGLE_WHITE_GRAPE_BUSH_PATCH_KEY, VineryFeatures.JUNGLE_WHITE_GRAPE_FEATURE.get(), new NoneFeatureConfiguration());
    }

    public static RandomPatchConfiguration createRandomPatchFeatureConfig(Block block, int age){
        return FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block.defaultBlockState().setValue(GrapeBush.AGE, age))), List.of(Blocks.GRASS_BLOCK), 36);
    }









    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new VineryIdentifier(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static WeightedStateProvider cherryLeaveProvider(){
        return new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ObjectRegistry.CHERRY_LEAVES.get().defaultBlockState(), 10).add(ObjectRegistry.CHERRY_LEAVES.get().defaultBlockState().setValue(VARIANT, true), 4).add(ObjectRegistry.CHERRY_LEAVES.get().defaultBlockState().setValue(VARIANT, true).setValue(CherryLeaves.HAS_CHERRIES, true), 2));
    }
 


    private static BlockPredicate createBlockPredicate(List<Block> validGround) {
        return !validGround.isEmpty() ? BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(validGround)) : BlockPredicate.ONLY_IN_AIR_PREDICATE;
    }
}

