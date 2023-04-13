package satisfyu.vinery.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

public class VineryPlacedFeatures {
    public static final RegistryKey<PlacedFeature> TREE_CHERRY_PLACED_KEY = registerKey("tree_cherry");
    public static final RegistryKey<PlacedFeature> TREE_CHERRY_OLD_PLACED_KEY = registerKey("tree_cherry_old");


    public static final RegistryKey<PlacedFeature> RED_GRAPE_PATCH_CHANCE_KEY = registerKey("red_grape_bush_chance");
    public static final RegistryKey<PlacedFeature> WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("white_grape_bush_chance");
    public static final RegistryKey<PlacedFeature> TAIGA_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("taiga_red_grape_bush_chance");
    public static final RegistryKey<PlacedFeature> TAIGA_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("taiga_white_grape_bush_chance");
    public static final RegistryKey<PlacedFeature> SAVANNA_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("savanna_red_grape_bush_chance");
    public static final RegistryKey<PlacedFeature> SAVANNA_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("savanna_white_grape_bush_chance");
    public static final RegistryKey<PlacedFeature> JUNGLE_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("jungle_red_grape_bush_chance");
    public static final RegistryKey<PlacedFeature> JUNGLE_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("jungle_white_grape_bush_chance");


    public static void bootstrap(Registerable<PlacedFeature> c) {
        var configuredFeatureRegistryEntryLookup = c.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(c, TREE_CHERRY_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.CHERRY_KEY), VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1), ObjectRegistry.CHERRY_SAPLING));
        register(c, TREE_CHERRY_OLD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(VineryConfiguredFeatures.OLD_CHERRY_KEY), VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.01f, 1), ObjectRegistry.OLD_CHERRY_SAPLING));

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
        return List.of(RarityFilterPlacementModifier.of(92), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new VineryIdentifier(name));
    }
}
