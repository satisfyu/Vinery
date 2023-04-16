package satisfyu.vinery.world;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

public class VineryPlacedFeatures {
    public static final ResourceKey<PlacedFeature> TREE_CHERRY_PLACED_KEY = registerKey("tree_cherry");
    public static final ResourceKey<PlacedFeature> TREE_CHERRY_OLD_PLACED_KEY = registerKey("tree_cherry_old");


    public static final ResourceKey<PlacedFeature> RED_GRAPE_PATCH_CHANCE_KEY = registerKey("red_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("white_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> TAIGA_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("taiga_red_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> TAIGA_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("taiga_white_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> SAVANNA_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("savanna_red_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> SAVANNA_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("savanna_white_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> JUNGLE_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("jungle_red_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> JUNGLE_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("jungle_white_grape_bush_chance");


    public static void bootstrap(BootstapContext<PlacedFeature> c) {
        var configuredFeatureRegistryEntryLookup = c.lookup(Registries.CONFIGURED_FEATURE);

        register(c, TREE_CHERRY_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.CHERRY_KEY), VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.01f, 1), ObjectRegistry.CHERRY_SAPLING.get()));
        register(c, TREE_CHERRY_OLD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.OLD_CHERRY_KEY), VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.01f, 1), ObjectRegistry.OLD_CHERRY_SAPLING.get()));

        register(c, RED_GRAPE_PATCH_CHANCE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.RED_GRAPE_BUSH_PATCH_KEY), getGrapeModifiers());
        register(c, WHITE_GRAPE_PATCH_CHANCE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.WHITE_GRAPE_BUSH_PATCH_KEY), getGrapeModifiers());
        register(c, TAIGA_RED_GRAPE_PATCH_CHANCE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.TAIGA_RED_GRAPE_BUSH_PATCH_KEY), getGrapeModifiers());
        register(c, TAIGA_WHITE_GRAPE_PATCH_CHANCE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.TAIGA_WHITE_GRAPE_BUSH_PATCH_KEY), getGrapeModifiers());
        register(c, SAVANNA_RED_GRAPE_PATCH_CHANCE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.SAVANNA_RED_GRAPE_BUSH_PATCH_KEY), getGrapeModifiers());
        register(c, SAVANNA_WHITE_GRAPE_PATCH_CHANCE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.SAVANNA_WHITE_GRAPE_BUSH_PATCH_KEY), getGrapeModifiers());
        register(c, JUNGLE_RED_GRAPE_PATCH_CHANCE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.JUNGLE_RED_GRAPE_BUSH_PATCH_KEY), getGrapeModifiers());
        register(c, JUNGLE_WHITE_GRAPE_PATCH_CHANCE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.JUNGLE_WHITE_GRAPE_BUSH_PATCH_KEY), getGrapeModifiers());
    }

    private static List<PlacementModifier> getGrapeModifiers(){
        return List.of(RarityFilter.onAverageOnceEvery(92), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new VineryIdentifier(name));
    }
}
