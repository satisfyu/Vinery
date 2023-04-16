package satisfyu.vinery.registry;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.recipe.CookingPotRecipe;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.recipe.WoodFiredOvenRecipe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class VineryRecipeTypes {

    private static final Registrar<RecipeType<?>> RECIPE_TYPES = Vinery.REGISTRIES.get(Registries.RECIPE_TYPE);
    private static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = Vinery.REGISTRIES.get(Registries.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeType<WoodFiredOvenRecipe>> WOOD_FIRED_OVEN_RECIPE_TYPE = create("wood_fired_oven_cooking");
    public static final RegistrySupplier<RecipeSerializer<WoodFiredOvenRecipe>> WOOD_FIRED_OVEN_RECIPE_SERIALIZER = create("wood_fired_oven_cooking", WoodFiredOvenRecipe.Serializer::new);
    public static final RegistrySupplier<RecipeType<FermentationBarrelRecipe>> FERMENTATION_BARREL_RECIPE_TYPE = create("wine_fermentation");
    public static final RegistrySupplier<RecipeSerializer<FermentationBarrelRecipe>> FERMENTATION_BARREL_RECIPE_SERIALIZER = create("wine_fermentation", FermentationBarrelRecipe.Serializer::new);
    public static final RegistrySupplier<RecipeType<CookingPotRecipe>> COOKING_POT_RECIPE_TYPE = create("pot_cooking");
    public static final RegistrySupplier<RecipeSerializer<CookingPotRecipe>> COOKING_POT_RECIPE_SERIALIZER = create("pot_cooking", CookingPotRecipe.Serializer::new);

    private static <T extends Recipe<?>> RegistrySupplier<RecipeSerializer<T>> create(String name, Supplier<RecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(new VineryIdentifier(name), serializer);
    }

    private static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> create(String name) {
        Supplier<RecipeType<T>> type = () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        return RECIPE_TYPES.register(new VineryIdentifier(name), type);
    }

    public static void init() {
    }
}
