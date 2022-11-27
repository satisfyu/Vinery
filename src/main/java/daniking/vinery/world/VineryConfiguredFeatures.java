package daniking.vinery.world;

import com.google.common.collect.ImmutableList;
import daniking.vinery.block.VariantLeavesBlock;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.GrapeBush;
import daniking.vinery.world.feature.VineryVinesFeature;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;
import java.util.function.Predicate;

public class VineryConfiguredFeatures {

    public static final Feature<SimpleBlockFeatureConfig> VINES_FEATURE = new VineryVinesFeature(SimpleBlockFeatureConfig.CODEC);

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> RED_GRAPE_BUSH_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("red_grape_bush"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.RED_GRAPE_BUSH.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> RED_GRAPE_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("red_grape_bush_chance"), RED_GRAPE_BUSH_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> WHITE_GRAPE_BUSH_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("white_grape_bush"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.WHITE_GRAPE_BUSH.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> WHITE_GRAPE_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("white_grape_bush_chance"), WHITE_GRAPE_BUSH_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> CHERRY = ConfiguredFeatures.register(VineryIdentifier.asString("cherry"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.CHERRY_LOG), new StraightTrunkPlacer(5, 2, 0), SimpleBlockStateProvider.of(ObjectRegistry.CHERRY_LEAVES.getDefaultState()), new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> CHERRY_VARIANT = ConfiguredFeatures.register(VineryIdentifier.asString("cherry_variant"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.CHERRY_LOG), new StraightTrunkPlacer(5, 2, 0), SimpleBlockStateProvider.of(ObjectRegistry.CHERRY_LEAVES.getDefaultState().with(VariantLeavesBlock.VARIANT, 1)), new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> OLD_CHERRY = ConfiguredFeatures.register(VineryIdentifier.asString("old_cherry"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.OLD_CHERRY_LOG), new LargeOakTrunkPlacer(4, 14, 2), SimpleBlockStateProvider.of(ObjectRegistry.CHERRY_LEAVES.getDefaultState()), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> OLD_CHERRY_BEE = ConfiguredFeatures.register(VineryIdentifier.asString("old_cherry_bee"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.OLD_CHERRY_LOG), new LargeOakTrunkPlacer(4, 14, 2), SimpleBlockStateProvider.of(ObjectRegistry.CHERRY_LEAVES.getDefaultState()), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f))).forceDirt().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> OLD_CHERRY_VARIANT = ConfiguredFeatures.register(VineryIdentifier.asString("old_cherry_variant"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.OLD_CHERRY_LOG), new LargeOakTrunkPlacer(4, 14, 2), SimpleBlockStateProvider.of(ObjectRegistry.CHERRY_LEAVES.getDefaultState().with(VariantLeavesBlock.VARIANT, 1)), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of()).forceDirt().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> OLD_CHERRY_VARIANT_WITH_BEE = ConfiguredFeatures.register(VineryIdentifier.asString("old_cherry_variant_with_bee"), Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ObjectRegistry.OLD_CHERRY_LOG), new LargeOakTrunkPlacer(4, 14, 2), SimpleBlockStateProvider.of(ObjectRegistry.CHERRY_LEAVES.getDefaultState().with(VariantLeavesBlock.VARIANT, 1)), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f))).forceDirt().build());
    
    public static final RegistryEntry<PlacedFeature> TREE_CHERRY = PlacedFeatures.register("tree_cherry", CHERRY, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1), ObjectRegistry.CHERRY_SAPLING));
    public static final RegistryEntry<PlacedFeature> TREE_CHERRY_OLD = PlacedFeatures.register("tree_cherry_old", OLD_CHERRY, VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1), ObjectRegistry.OLD_CHERRY_SAPLING));
    
    private static RandomPatchFeatureConfig getFlowerGrassConfig(Block flowerGrass) {
        return new RandomPatchFeatureConfig(8, 2, 0, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(flowerGrass.getDefaultState())), createBlockPredicate(List.of(Blocks.GRASS_BLOCK))));
    }
    
    public static void init() {
        Registry.register(Registry.FEATURE, new VineryIdentifier("vines_feature"), VINES_FEATURE);
        BiomeModification world = BiomeModifications.create(new VineryIdentifier("world_features"));
        Predicate<BiomeSelectionContext> bushBiomes = BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.PLAINS, BiomeKeys.TAIGA, BiomeKeys.RIVER, BiomeKeys.SWAMP, BiomeKeys.JUNGLE);
        Predicate<BiomeSelectionContext> grassFlowerBiomes = BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.TAIGA, BiomeKeys.RIVER, BiomeKeys.SWAMP);


        world.add(ModificationPhase.ADDITIONS, bushBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, RED_GRAPE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, bushBiomes, ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, WHITE_GRAPE_PATCH_CHANCE.value()));

        world.add(ModificationPhase.ADDITIONS, getTreesSelector(), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, TREE_CHERRY.value()));
        world.add(ModificationPhase.ADDITIONS, getTreesSelector(), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, TREE_CHERRY_OLD.value()));
    }
 
	private static Predicate<BiomeSelectionContext> getTreesSelector() {
        return BiomeSelectors.tag(TagKey.of(Registry.BIOME_KEY, new Identifier("vinery:has_structure/tree")));
	}

    private static BlockPredicate createBlockPredicate(List<Block> validGround) {
        return !validGround.isEmpty() ? BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(validGround)) : BlockPredicate.IS_AIR;
    }

}

