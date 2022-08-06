package daniking.vinery.data;

import daniking.vinery.registry.ObjectRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

import java.util.function.Consumer;

public class DataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(gen -> new FabricRecipeProvider(gen) {
            @Override
            protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
                ShapedRecipeJsonBuilder.create(ObjectRegistry.STRAW_HAT)
                        .group("straw_hat")
                        .pattern(" X ")
                        .pattern(" G ")
                        .pattern("XXX")
                        .criterion("has_wheat", RecipeProvider.conditionsFromItem(Items.WHEAT))
                        .input('X', Ingredient.ofItems(Items.WHEAT))
                        .input('G', Ingredient.ofItems(Items.RED_WOOL))
                        .offerTo(exporter);

            }
        });
    }
}
