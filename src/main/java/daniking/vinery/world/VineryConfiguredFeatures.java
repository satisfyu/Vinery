package daniking.vinery.world;

import daniking.vinery.ObjectRegistry;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.GrapeBush;
import daniking.vinery.world.feature.VineryVinesFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class VineryConfiguredFeatures {

    public static final Feature<SimpleBlockFeatureConfig> VINES_FEATURE = new VineryVinesFeature(SimpleBlockFeatureConfig.CODEC);

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> RED_GRAPE_BUSH_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("red_grape_bush"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.RED_GRAPE_BUSH.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> RED_GRAPE_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("red_grape_bush_chance"), RED_GRAPE_BUSH_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> WHITE_GRAPE_BUSH_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("white_grape_bush"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.WHITE_GRAPE_BUSH.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> WHITE_GRAPE_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("white_grape_bush_chance"), WHITE_GRAPE_BUSH_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ROCKS_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("rocks"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.ROCKS)), List.of(Blocks.GRASS_BLOCK, Blocks.STONE), 36));
    public static final RegistryEntry<PlacedFeature> ROCKS_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("rocks_chance"), ROCKS_PATCH, RarityFilterPlacementModifier.of(128), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ROCKS_VARIANT_B_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("rocks_variant_b"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.ROCKS_VARIANT_B)), List.of(Blocks.GRASS_BLOCK, Blocks.STONE), 36));
    public static final RegistryEntry<PlacedFeature> ROCKS_VARIANT_B_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("rocks_variant_b_chance"), ROCKS_VARIANT_B_PATCH, RarityFilterPlacementModifier.of(128), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ROCKS_VARIANT_C_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("rocks_variant_c"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.ROCKS_VARIANT_C)), List.of(Blocks.GRASS_BLOCK, Blocks.STONE), 36));
    public static final RegistryEntry<PlacedFeature> ROCKS_VARIANT_C_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("rocks_variant_c_chance"), ROCKS_VARIANT_C_PATCH, RarityFilterPlacementModifier.of(128), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> RED_GRASS_FLOWERS = ConfiguredFeatures.register(VineryIdentifier.asString("red_grass_flower"), Feature.RANDOM_PATCH, getFlowerGrassConfig(ObjectRegistry.RED_GRASS_FLOWER));
    public static final RegistryEntry<PlacedFeature> RED_GRASS_FLOWERS_PATCH = PlacedFeatures.register(VineryIdentifier.asString("red_grass_flower_chance"), RED_GRASS_FLOWERS, RarityFilterPlacementModifier.of(144), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PINK_GRASS_FLOWERS = ConfiguredFeatures.register(VineryIdentifier.asString("pink_grass_flower"), Feature.RANDOM_PATCH, getFlowerGrassConfig(ObjectRegistry.PINK_GRASS_FLOWER));
    public static final RegistryEntry<PlacedFeature> PINK_GRASS_FLOWERS_PATCH = PlacedFeatures.register(VineryIdentifier.asString("pink_grass_flower_chance"), PINK_GRASS_FLOWERS, RarityFilterPlacementModifier.of(144), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> WHITE_GRASS_FLOWERS = ConfiguredFeatures.register(VineryIdentifier.asString("white_grass_flower"),  Feature.RANDOM_PATCH, getFlowerGrassConfig(ObjectRegistry.WHITE_GRASS_FLOWER));
    public static final RegistryEntry<PlacedFeature> WHITE_GRASS_FLOWERS_PATCH = PlacedFeatures.register(VineryIdentifier.asString("white_grass_flower_chance"), WHITE_GRASS_FLOWERS, RarityFilterPlacementModifier.of(144), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<SimpleBlockFeatureConfig, ?>> EMPTY_RED_VINES = ConfiguredFeatures.register(VineryIdentifier.asString("empty_red_vines"), VINES_FEATURE, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.EMPTY_RED_VINE)));
    public static final RegistryEntry<PlacedFeature> EMPTY_RED_VINES_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("empty_red_vines_chance"), EMPTY_RED_VINES, CountPlacementModifier.of(14), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(64), YOffset.fixed(100)), BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(createBlockPredicate(List.of(Blocks.GRASS_BLOCK, Blocks.STONE))));

    private static RandomPatchFeatureConfig getFlowerGrassConfig(Block flowerGrass) {
        return new RandomPatchFeatureConfig(8, 2, 0, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(flowerGrass.getDefaultState())), createBlockPredicate(List.of(Blocks.GRASS_BLOCK))));
    }
    public static void init() {
        Registry.register(Registry.FEATURE, new VineryIdentifier("vines_feature"), VINES_FEATURE);
        BiomeModification world = BiomeModifications.create(new VineryIdentifier("world_features"));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, RED_GRAPE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()),  ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, WHITE_GRAPE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ROCKS_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ROCKS_VARIANT_B_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ROCKS_VARIANT_C_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getGrassFlowerCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, RED_GRASS_FLOWERS_PATCH.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getGrassFlowerCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, PINK_GRASS_FLOWERS_PATCH.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getGrassFlowerCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, WHITE_GRASS_FLOWERS_PATCH.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getVinesCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, EMPTY_RED_VINES_CHANCE.value()));
    }

    private static Biome.Category[] getBushCategories() {
        return new Biome.Category[] {
                Biome.Category.FOREST,
                Biome.Category.PLAINS,
                Biome.Category.TAIGA,
                Biome.Category.RIVER,
                Biome.Category.SWAMP,
                Biome.Category.JUNGLE,
        };
    }

    private static Biome.Category[] getGrassFlowerCategories() {
        return new Biome.Category[] {
                Biome.Category.PLAINS,
                Biome.Category.MOUNTAIN,
                Biome.Category.TAIGA,
                Biome.Category.RIVER,
                Biome.Category.SWAMP,
        };
    }

    private static Biome.Category[] getVinesCategories() {
        return new Biome.Category[] {
                Biome.Category.PLAINS,
                Biome.Category.FOREST,
                Biome.Category.MOUNTAIN,
                Biome.Category.TAIGA,
                Biome.Category.RIVER,
        };
    }

    private static BlockPredicate createBlockPredicate(List<Block> validGround) {
        return !validGround.isEmpty() ? BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(validGround, new BlockPos(0,-1, 0))) : BlockPredicate.IS_AIR;
    }

}

