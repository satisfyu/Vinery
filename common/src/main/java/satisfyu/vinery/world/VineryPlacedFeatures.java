package satisfyu.vinery.world;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import satisfyu.vinery.VineryIdentifier;

public class VineryPlacedFeatures {
    public static final ResourceKey<PlacedFeature> TREE_CHERRY_PLACED_KEY = registerKey("tree_cherry");
    public static final ResourceKey<PlacedFeature> TREE_APPLE_PLACED_KEY = registerKey("tree_apple");


    public static final ResourceKey<PlacedFeature> RED_GRAPE_PATCH_CHANCE_KEY = registerKey("red_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("white_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> TAIGA_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("taiga_red_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> TAIGA_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("taiga_white_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> SAVANNA_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("savanna_red_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> SAVANNA_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("savanna_white_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> JUNGLE_RED_GRAPE_PATCH_CHANCE_KEY = registerKey("jungle_red_grape_bush_chance");
    public static final ResourceKey<PlacedFeature> JUNGLE_WHITE_GRAPE_PATCH_CHANCE_KEY = registerKey("jungle_white_grape_bush_chance");


    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new VineryIdentifier(name));
    }
}
