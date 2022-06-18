package daniking.vinery;

import daniking.vinery.block.GrapeBush;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModWorldFeatures {

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> RED_GRAPE_BUSH_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("red_grape_bush"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.RED_GRAPE_BUSH.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> RED_GRAPE_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("red_grape_bush_chance"), RED_GRAPE_BUSH_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> WHITE_GRAPE_BUSH_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("white_grape_bush"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.WHITE_GRAPE_BUSH.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK), 36));
    public static final RegistryEntry<PlacedFeature> WHITE_GRAPE_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("white_grape_bush_chance"), WHITE_GRAPE_BUSH_PATCH, RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ROCKS_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("rocks"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.ROCKS)), List.of(Blocks.GRASS_BLOCK, Blocks.STONE), 36));
    public static final RegistryEntry<PlacedFeature> ROCKS_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("rocks_chance"), ROCKS_PATCH, RarityFilterPlacementModifier.of(128), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ROCKS_VARIANT_B_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("rocks_variant_b"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.ROCKS_VARIANT_B)), List.of(Blocks.GRASS_BLOCK, Blocks.STONE), 36));
    public static final RegistryEntry<PlacedFeature> ROCKS_VARIANT_B_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("rocks_variant_b_chance"), ROCKS_PATCH, RarityFilterPlacementModifier.of(128), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ROCKS_VARIANT_C_PATCH = ConfiguredFeatures.register(VineryIdentifier.asString("rocks_variant_c"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.ROCKS_VARIANT_C)), List.of(Blocks.GRASS_BLOCK, Blocks.STONE), 36));
    public static final RegistryEntry<PlacedFeature> ROCKS_VARIANT_C_PATCH_CHANCE = PlacedFeatures.register(VineryIdentifier.asString("rocks_variant_c_chance"), ROCKS_PATCH, RarityFilterPlacementModifier.of(128), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static void init() {
        BiomeModification world = BiomeModifications.create(new VineryIdentifier("world_features"));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, RED_GRAPE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()),  ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, WHITE_GRAPE_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ROCKS_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ROCKS_VARIANT_B_PATCH_CHANCE.value()));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(getBushCategories()), ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ROCKS_VARIANT_C_PATCH_CHANCE.value()));

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
}

