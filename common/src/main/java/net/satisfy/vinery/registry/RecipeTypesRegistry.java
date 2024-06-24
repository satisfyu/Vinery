package net.satisfy.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.recipe.ApplePressRecipe;
import net.satisfy.vinery.recipe.FermentationBarrelRecipe;
import net.satisfy.vinery.util.VineryIdentifier;

import java.util.function.Supplier;

public class RecipeTypesRegistry {

    private static final Registrar<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Vinery.MOD_ID, Registries.RECIPE_TYPE).getRegistrar();
    private static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Vinery.MOD_ID, Registries.RECIPE_SERIALIZER).getRegistrar();

    public static final RegistrySupplier<RecipeType<FermentationBarrelRecipe>> FERMENTATION_BARREL_RECIPE_TYPE = create("wine_fermentation");
    public static final RegistrySupplier<RecipeSerializer<FermentationBarrelRecipe>> FERMENTATION_BARREL_RECIPE_SERIALIZER = create("wine_fermentation", FermentationBarrelRecipe.Serializer::new);

    public static final RegistrySupplier<RecipeType<ApplePressRecipe>> APPLE_PRESS_RECIPE_TYPE = create("apple_mashing");
    public static final RegistrySupplier<RecipeSerializer<ApplePressRecipe>> APPLE_PRESS_RECIPE_SERIALIZER = create("apple_mashing", ApplePressRecipe.Serializer::new);

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
