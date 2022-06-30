package daniking.vinery.registry;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.recipe.StoveCookingRecipe;
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

    public static final RecipeType<StoveCookingRecipe> STOVE_RECIPE_TYPE = create("stove_cooking");
    public static final RecipeSerializer<StoveCookingRecipe> STOVE_COOKING_RECIPE_SERIALIZER = create("stove_cooking", new StoveCookingRecipe.Serializer());

    private static <T extends Recipe<?>> RecipeSerializer<T> create(String name, RecipeSerializer<T> serializer) {
        RECIPE_SERIALIZERS.put(new VineryIdentifier(name), serializer);
        return serializer;
    }

    private static <T extends Recipe<?>> RecipeType<T> create(String name) {
        RecipeType<T> type = new RecipeType<>() {
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
