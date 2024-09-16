package net.satisfy.vinery.fabric.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.satisfy.vinery.util.VineryIdentifier;
import net.satisfy.vinery.world.VineryPlacedFeatures;

import java.util.function.Predicate;


public class VineryBiomeModification {

    public static void init() {
        BiomeModification world = BiomeModifications.create(VineryIdentifier.of("world_features"));
        Predicate<BiomeSelectionContext> plainsBiomes = getVinerySelector("spawns_grape");
        Predicate<BiomeSelectionContext> savannaBiomes = getVinerySelector("spawns_savanna_grape");
        Predicate<BiomeSelectionContext> taigaBiomes = getVinerySelector("spawns_taiga_grape");
        Predicate<BiomeSelectionContext> jungleBiomes = getVinerySelector("spawns_jungle_grape");

        Predicate<BiomeSelectionContext> treeBiomes = getVinerySelector("spawns_cherry_tree");


        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.WHITE_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, savannaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.SAVANNA_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, savannaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.SAVANNA_WHITE_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TAIGA_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TAIGA_WHITE_GRAPE_PATCH_CHANCE_KEY));




        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.JUNGLE_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.JUNGLE_WHITE_GRAPE_PATCH_CHANCE_KEY));

        world.add(ModificationPhase.ADDITIONS, treeBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TREE_CHERRY_PLACED_KEY));
        world.add(ModificationPhase.ADDITIONS, treeBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TREE_APPLE_PLACED_KEY));
    }

    private static Predicate<BiomeSelectionContext> getVinerySelector(String path) {
        return BiomeSelectors.tag(TagKey.create(Registries.BIOME, VineryIdentifier.of(path)));
    }



}
