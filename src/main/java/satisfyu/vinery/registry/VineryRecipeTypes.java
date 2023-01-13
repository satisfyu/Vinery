package satisfyu.vinery.registry;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.recipe.CookingPotRecipe;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.recipe.WoodFiredOvenRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class VineryRecipeTypes {

    private static final Map<Identifier, RecipeSerializer<?>> RECIPE_SERIALIZERS = new HashMap<>();
    private static final Map<Identifier, RecipeType<?>> RECIPE_TYPES = new HashMap<>();
    public static final RecipeType<WoodFiredOvenRecipe> WOOD_FIRED_OVEN_RECIPE_TYPE = create("wood_fired_oven_cooking");
    public static final RecipeSerializer<WoodFiredOvenRecipe> WOOD_FIRED_OVEN_RECIPE_SERIALIZER = create("wood_fired_oven_cooking", new WoodFiredOvenRecipe.Serializer());
    public static final RecipeType<FermentationBarrelRecipe> FERMENTATION_BARREL_RECIPE_TYPE = create("wine_fermentation");
    public static final RecipeSerializer<FermentationBarrelRecipe> FERMENTATION_BARREL_RECIPE_SERIALIZER = create("wine_fermentation", new FermentationBarrelRecipe.Serializer());
    public static final RecipeType<CookingPotRecipe> COOKING_POT_RECIPE_TYPE = create("pot_cooking");
    public static final RecipeSerializer<CookingPotRecipe> COOKING_POT_RECIPE_SERIALIZER = create("pot_cooking", new CookingPotRecipe.Serializer());

    private static <T extends Recipe<?>> RecipeSerializer<T> create(String name, RecipeSerializer<T> serializer) {
        RECIPE_SERIALIZERS.put(new VineryIdentifier(name), serializer);
        return serializer;
    }

    private static <T extends Recipe<?>> RecipeType<T> create(String name) {
        final RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        RECIPE_TYPES.put(new VineryIdentifier(name), type);
        return type;
    }

    public static void init() {
        for (Map.Entry<Identifier, RecipeSerializer<?>> entry : RECIPE_SERIALIZERS.entrySet()) {
            Registry.register(Registry.RECIPE_SERIALIZER, entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Identifier, RecipeType<?>> entry : RECIPE_TYPES.entrySet()) {
            Registry.register(Registry.RECIPE_TYPE, entry.getKey(), entry.getValue());
        }
    }


}
