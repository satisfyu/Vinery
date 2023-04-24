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
        Predicate<BiomeSelectionContext> plainsBiomes = getVinerySelector("spanws_grape");
        Predicate<BiomeSelectionContext> savannaBiomes = getVinerySelector("spanws_savanna_grape");
        Predicate<BiomeSelectionContext> taigaBiomes = getVinerySelector("spanws_taiga_grape");
        Predicate<BiomeSelectionContext> jungleBiomes = getVinerySelector("spanws_jungle_grape");

        Predicate<BiomeSelectionContext> treeBiomes = getVinerySelector("spanws_cherry_tree");


        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, plainsBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.WHITE_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, savannaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.SAVANNA_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, savannaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.SAVANNA_WHITE_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TAIGA_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, taigaBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TAIGA_WHITE_GRAPE_PATCH_CHANCE_KEY));




        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.JUNGLE_RED_GRAPE_PATCH_CHANCE_KEY));
        world.add(ModificationPhase.ADDITIONS, jungleBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.JUNGLE_WHITE_GRAPE_PATCH_CHANCE_KEY));

        world.add(ModificationPhase.ADDITIONS, treeBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TREE_CHERRY_PLACED_KEY));
        world.add(ModificationPhase.ADDITIONS, treeBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VineryPlacedFeatures.TREE_CHERRY_OLD_PLACED_KEY));
    }

    private static Predicate<BiomeSelectionContext> getVinerySelector(String path) {
        return BiomeSelectors.tag(TagKey.create(Registries.BIOME, new VineryIdentifier(path)));
    }



}
