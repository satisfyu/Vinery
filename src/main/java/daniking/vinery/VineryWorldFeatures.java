package daniking.vinery;

import daniking.vinery.block.GrapeBush;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class VineryWorldFeatures {

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> RED_GRAPE_BUSH_PATCH = ConfiguredFeatures.register(getStringIdentifier("red_grape_bush"), Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ObjectRegistry.RED_GRAPE_BUSH.getDefaultState().with(GrapeBush.AGE, 3))), List.of(Blocks.GRASS_BLOCK)));
    public static final RegistryEntry<PlacedFeature> RED_GRAPE_PATCH_COMMON = PlacedFeatures.register(getStringIdentifier("red_grape_bush_common"), RED_GRAPE_BUSH_PATCH, RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    static String getStringIdentifier(String path) {
        return (Vinery.MODID + ":" + path);
    }
    public static void init() {
        System.out.println(getStringIdentifier("red_grape_bush"));
        BiomeModification world = BiomeModifications.create(new VineryIdentifier("world_features"));
        world.add(ModificationPhase.ADDITIONS, BiomeSelectors.all(),  ctx -> ctx.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, RED_GRAPE_PATCH_COMMON.value()));
    }
}
