package satisfyu.vinery.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import satisfyu.vinery.VineryIdentifier;

import java.util.function.Predicate;

public class VineryBiomeModification {

    public static void init() {
        BiomeModification world = BiomeModifications.create(new VineryIdentifier("world_features"));
        Predicate<BiomeSelectionContext> plainsBiomes = BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.PLAINS, BiomeKeys.SWAMP, BiomeKeys.BIRCH_FOREST, BiomeKeys.MEADOW, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.RIVER);
        Predicate<BiomeSelectionContext> savannaBiomes = BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.WOODED_BADLANDS);
        Predicate<BiomeSelectionContext> taigaBiomes = BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.SNOWY_TAIGA);
        Predicate<BiomeSelectionContext> jungleBiomes = BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.WOODED_BADLANDS);


        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.WHITE_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, savannaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.SAVANNA_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, savannaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.SAVANNA_WHITE_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.TAIGA_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.TAIGA_WHITE_GRAPE_PATCH_CHANCE_KEY));




        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.JUNGLE_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.JUNGLE_WHITE_GRAPE_PATCH_CHANCE_KEY));

        world.add(ModificationPhase.ADDITIONS, getTreesSelector(), ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.TREE_CHERRY_PLACED_KEY));
        world.add(ModificationPhase.ADDITIONS, getTreesSelector(), ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, VineryPlacedFeatures.TREE_CHERRY_OLD_PLACED_KEY));

    }

    private static Predicate<BiomeSelectionContext> getTreesSelector() {
        return BiomeSelectors.tag(TagKey.of(RegistryKeys.BIOME, new Identifier("vinery:has_structure/tree")));
    }

}
