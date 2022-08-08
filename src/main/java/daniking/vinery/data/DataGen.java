package daniking.vinery.data;

import daniking.vinery.Vinery;
import daniking.vinery.block.StackableBlock;
import daniking.vinery.registry.ObjectRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.data.client.*;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

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

        fabricDataGenerator.addProvider(gen -> new FabricModelProvider(gen) {
            @Override
            public void generateBlockStateModels(BlockStateModelGenerator generator) {
                registerWine(generator, ObjectRegistry.BOLVAR_WINE);
                registerWine(generator, ObjectRegistry.CHENET_WINE);
                registerWine(generator, ObjectRegistry.CHERRY_WINE);
                registerWine(generator, ObjectRegistry.CLARK_WINE);
                registerWine(generator, ObjectRegistry.KING_DANIS_WINE);
                registerWine(generator, ObjectRegistry.MELLOHI_WINE);
                registerWine(generator, ObjectRegistry.NOIR_WINE);
            }

            @Override
            public void generateItemModels(ItemModelGenerator generator) {
                registerItem(generator, ObjectRegistry.BOLVAR_WINE);
                registerItem(generator, ObjectRegistry.CHENET_WINE);
                registerItem(generator, ObjectRegistry.CHERRY_WINE);
                registerItem(generator, ObjectRegistry.CLARK_WINE);
                registerItem(generator, ObjectRegistry.KING_DANIS_WINE);
                registerItem(generator, ObjectRegistry.MELLOHI_WINE);
                registerItem(generator, ObjectRegistry.NOIR_WINE);
            }

            private void registerWine(BlockStateModelGenerator generator, Block block) {
                generator.blockStateCollector.accept(
                        VariantsBlockStateSupplier
                                .create(block).coordinate(createWestDefaultHorizontalRotationStates())
                                .coordinate(BlockStateVariantMap.create(StackableBlock.STACK).register(stack -> BlockStateVariant.create().put(VariantSettings.MODEL, TextureMap.getSubId(ObjectRegistry.KING_DANIS_WINE, "_stack" + stack)))));
            }

            private <T extends ItemConvertible> void registerItem(ItemModelGenerator generator, T t) {
                generator.register(t.asItem(), Models.GENERATED);
            }

            public static BlockStateVariantMap createWestDefaultHorizontalRotationStates() {
                return BlockStateVariantMap.create(Properties.HORIZONTAL_FACING).register(Direction.WEST, BlockStateVariant.create()).register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90)).register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180)).register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270));
            }
        });

        fabricDataGenerator.addProvider(generator -> new FabricTagProvider.BlockTagProvider(generator) {
            @Override
            protected void generateTags() {
                getOrCreateTagBuilder(Vinery.CAN_NOT_CONNECT).add(
                        ObjectRegistry.BIG_BOTTLE,
                        ObjectRegistry.WINE_BOTTLE,
                        ObjectRegistry.BOLVAR_WINE,
                        ObjectRegistry.CHENET_WINE,
                        ObjectRegistry.CHERRY_WINE,
                        ObjectRegistry.CLARK_WINE,
                        ObjectRegistry.KING_DANIS_WINE,
                        ObjectRegistry.MELLOHI_WINE,
                        ObjectRegistry.NOIR_WINE,
                        ObjectRegistry.RED_GRAPEJUICE_WINE_BOTTLE,
                        ObjectRegistry.WHITE_GRAPEJUICE_WINE_BOTTLE);
            }
        });
    }
}
