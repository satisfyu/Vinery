package satisfyu.vinery.world;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.CherryLeaves;
import satisfyu.vinery.block.grape.GrapeBush;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;
import java.util.function.Predicate;

import static satisfyu.vinery.block.CherryLeaves.VARIANT;


public class VineryConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> CHERRY_KEY = registerKey("cherry");
    public static final RegistryKey<ConfiguredFeature<?,?>> CHERRY_VARIANT_KEY = registerKey("cherry_variant");
    public static final RegistryKey<ConfiguredFeature<?,?>> OLD_CHERRY_KEY = registerKey("old_cherry");
    public static final RegistryKey<ConfiguredFeature<?,?>> OLD_CHERRY_BEE_KEY = registerKey("old_cherry_bee");
    public static final RegistryKey<ConfiguredFeature<?,?>> OLD_CHERRY_VARIANT_KEY = registerKey("old_cherry_variant");
    public static final RegistryKey<ConfiguredFeature<?,?>> OLD_CHERRY_VARIANT_WITH_BEE_KEY = registerKey("old_cherry_variant_with_bee");


    public static final RegistryKey<ConfiguredFeature<?,?>> RED_GRAPE_BUSH_PATCH_KEY = registerKey("red_grape_bush");
    public static final RegistryKey<ConfiguredFeature<?,?>> WHITE_GRAPE_BUSH_PATCH_KEY = registerKey("white_grape_bush");
    public static final RegistryKey<ConfiguredFeature<?,?>> TAIGA_RED_GRAPE_BUSH_PATCH_KEY = registerKey("taiga_red_grape_bush");
    public static final RegistryKey<ConfiguredFeature<?,?>> TAIGA_WHITE_GRAPE_BUSH_PATCH_KEY = registerKey("taiga_white_grape_bush");
    public static final RegistryKey<ConfiguredFeature<?,?>> SAVANNA_RED_GRAPE_BUSH_PATCH_KEY = registerKey("savanna_red_grape_bush");
    public static final RegistryKey<ConfiguredFeature<?,?>> SAVANNA_WHITE_GRAPE_BUSH_PATCH_KEY = registerKey("savanna_white_grape_bush");
    public static final RegistryKey<ConfiguredFeature<?,?>> JUNGLE_RED_GRAPE_BUSH_PATCH_KEY = registerKey("jungle_red_grape_bush_patch");
    public static final RegistryKey<ConfiguredFeature<?,?>> JUNGLE_WHITE_GRAPE_BUSH_PATCH_KEY = registerKey("jungle_white_grape_bush_patch");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> c) {
        register(c, CHERRY_KEY, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.CHERRY_LOG), new StraightTrunkPlacer(5, 2, 0), cherryLeaveProvider(), new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        register(c, CHERRY_VARIANT_KEY, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.CHERRY_LOG), new StraightTrunkPlacer(5, 2, 0), cherryLeaveProvider(), new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        register(c, OLD_CHERRY_KEY, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.OLD_CHERRY_LOG), new LargeOakTrunkPlacer(4, 14, 2), cherryLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
        register(c, OLD_CHERRY_BEE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.OLD_CHERRY_LOG), new LargeOakTrunkPlacer(4, 14, 2), cherryLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f))).forceDirt().build());
        register(c, OLD_CHERRY_VARIANT_KEY, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.OLD_CHERRY_LOG), new LargeOakTrunkPlacer(4, 14, 2), cherryLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
        register(c, OLD_CHERRY_VARIANT_WITH_BEE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.OLD_CHERRY_LOG), new LargeOakTrunkPlacer(4, 14, 2), cherryLeaveProvider(), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f))).forceDirt().build());


        register(c, RED_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.RED_GRAPE_BUSH, 3));
        register(c, WHITE_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.WHITE_GRAPE_BUSH, 3));

        register(c, TAIGA_RED_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.TAIGA_RED_GRAPE_BUSH, 2));
        register(c, TAIGA_WHITE_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.TAIGA_WHITE_GRAPE_BUSH, 2));

        register(c, SAVANNA_RED_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.SAVANNA_RED_GRAPE_BUSH, 3));
        register(c, SAVANNA_WHITE_GRAPE_BUSH_PATCH_KEY, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(ObjectRegistry.SAVANNA_WHITE_GRAPE_BUSH, 3));


        register(c, JUNGLE_RED_GRAPE_BUSH_PATCH_KEY, VineryFeatures.JUNGLE_RED_GRAPE_FEATURE, new DefaultFeatureConfig());
        register(c, JUNGLE_WHITE_GRAPE_BUSH_PATCH_KEY, VineryFeatures.JUNGLE_WHITE_GRAPE_FEATURE, new DefaultFeatureConfig());
    }

    public static RandomPatchFeatureConfig createRandomPatchFeatureConfig(Block block, int age){
        return ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(block.getDefaultState().with(GrapeBush.AGE, age))), List.of(Blocks.GRASS_BLOCK), 36);
    }









    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new VineryIdentifier(name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static WeightedBlockStateProvider cherryLeaveProvider(){
        return new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ObjectRegistry.CHERRY_LEAVES.getDefaultState(), 10).add(ObjectRegistry.CHERRY_LEAVES.getDefaultState().with(VARIANT, true), 4).add(ObjectRegistry.CHERRY_LEAVES.getDefaultState().with(VARIANT, true).with(CherryLeaves.HAS_CHERRIES, true), 2));
    }
 


    private static BlockPredicate createBlockPredicate(List<Block> validGround) {
        return !validGround.isEmpty() ? BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(validGround)) : BlockPredicate.IS_AIR;
    }
}

