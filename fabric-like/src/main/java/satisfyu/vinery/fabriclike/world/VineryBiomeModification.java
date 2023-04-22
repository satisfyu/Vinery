package satisfyu.vinery.fabriclike.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.world.VineryPlacedFeatures;

import java.util.function.Predicate;


public class VineryBiomeModification {

    public static void init() {
        BiomeModification world = BiomeModifications.create(new VineryIdentifier("world_features"));
        Predicate<BiomeSelectionContext> plainsBiomes = BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.PLAINS, Biomes.SWAMP, Biomes.BIRCH_FOREST, Biomes.MEADOW, Biomes.SUNFLOWER_PLAINS, Biomes.RIVER);
        Predicate<BiomeSelectionContext> savannaBiomes = BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA, Biomes.WOODED_BADLANDS);
        Predicate<BiomeSelectionContext> taigaBiomes = BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA);
        Predicate<BiomeSelectionContext> jungleBiomes = BiomeSelectors.includeByKey(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.WOODED_BADLANDS);


        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.WHITE_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, savannaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.SAVANNA_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, savannaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.SAVANNA_WHITE_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TAIGA_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TAIGA_WHITE_GRAPE_PATCH_CHANCE_KEY));




        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.JUNGLE_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.JUNGLE_WHITE_GRAPE_PATCH_CHANCE_KEY));

        world.add(ModificationPhase.ADDITIONS, getTreesSelector(), ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TREE_CHERRY_PLACED_KEY));
        world.add(ModificationPhase.ADDITIONS, getTreesSelector(), ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TREE_CHERRY_OLD_PLACED_KEY));
    }

    private static Predicate<BiomeSelectionContext> getTreesSelector() {
        return BiomeSelectors.tag(TagKey.create(Registries.BIOME, new ResourceLocation("vinery:has_structure/tree")));
    }



}
