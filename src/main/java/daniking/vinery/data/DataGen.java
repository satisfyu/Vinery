package daniking.vinery.data;

import daniking.vinery.Vinery;
import daniking.vinery.block.FlowerPotBlock;
import daniking.vinery.block.StackableBlock;
import daniking.vinery.block.WineRackBlock;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.util.EnumTallFlower;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.*;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
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

        fabricDataGenerator.addProvider(generator -> new FabricBlockLootTableProvider(generator) {
            @Override
            protected void generateBlockLootTables() {
                addDrop(ObjectRegistry.BANNER);
                addDrop(ObjectRegistry.BIG_TABLE);
                addDrop(ObjectRegistry.BIG_BOTTLE);
                addDrop(ObjectRegistry.COARSE_DIRT_SLAB, createSlab(ObjectRegistry.COARSE_DIRT_SLAB));
                addDrop(ObjectRegistry.DIRT_SLAB, createSlab(ObjectRegistry.DIRT_SLAB));
                addDrop(ObjectRegistry.RED_GRAPE_CRATE);
                addDrop(ObjectRegistry.WHITE_GRAPE_CRATE);
                addDrop(ObjectRegistry.FLOWER_POT, drops(ObjectRegistry.FLOWER_POT)
                        .pool(BlockLootTableGenerator.addSurvivesExplosionCondition(ObjectRegistry.FLOWER_POT, LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                .with(ItemEntry.builder(Blocks.LILAC)
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))
                                                .conditionally(BlockStatePropertyLootCondition.builder(ObjectRegistry.FLOWER_POT)
                                                        .properties(StatePredicate.Builder.create().exactMatch(FlowerPotBlock.CONTENT, EnumTallFlower.LILAC)))))
                                .with(ItemEntry.builder(Blocks.PEONY)
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))
                                                .conditionally(BlockStatePropertyLootCondition.builder(ObjectRegistry.FLOWER_POT)
                                                        .properties(StatePredicate.Builder.create().exactMatch(FlowerPotBlock.CONTENT, EnumTallFlower.PEONY)))))
                                .with(ItemEntry.builder(Blocks.ROSE_BUSH)
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))
                                                .conditionally(BlockStatePropertyLootCondition.builder(ObjectRegistry.FLOWER_POT)
                                                        .properties(StatePredicate.Builder.create().exactMatch(FlowerPotBlock.CONTENT, EnumTallFlower.ROSE_BUSH))))
                                        .build()
                ))));
                addDrop(ObjectRegistry.FLOWER_BOX);
                addDrop(ObjectRegistry.FLOWER_BOX_ALLIUM, flowerBox(Blocks.ALLIUM));
                addDrop(ObjectRegistry.FLOWER_BOX_AZURE_BLUET, flowerBox(Blocks.AZURE_BLUET));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_ORCHID, flowerBox(Blocks.BLUE_ORCHID));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_CORNFLOWER, flowerBox(Blocks.CORNFLOWER));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_DANDELION, flowerBox(Blocks.DANDELION));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_LILY_OF_THE_VALLEY, flowerBox(Blocks.LILY_OF_THE_VALLEY));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_ORANGE_TULIP, flowerBox(Blocks.ORANGE_TULIP));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_OXEYE_DAISY, flowerBox(Blocks.OXEYE_DAISY));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_PINK_TULIP, flowerBox(Blocks.PINK_TULIP));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_POPPY, flowerBox(Blocks.POPPY));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_RED_TULIP, flowerBox(Blocks.RED_TULIP));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_WHITE_TULIP, flowerBox(Blocks.WHITE_TULIP));
                addDrop(ObjectRegistry.FLOWER_BOX_BLUE_WHITER_ROSE, flowerBox(Blocks.POTTED_WITHER_ROSE));

                addDropWithSilkTouch(ObjectRegistry.WINDOW_1);
                addDropWithSilkTouch(ObjectRegistry.WINDOW_2);

                addDrop(ObjectRegistry.LOAM);
                addDrop(ObjectRegistry.KITCHEN_SINK);
                addDrop(ObjectRegistry.WINE_PRESS);
                addDrop(ObjectRegistry.WINE_BOX);

                addWineRack(ObjectRegistry.WINE_RACK_1, 12);
                addWineRack(ObjectRegistry.WINE_RACK_2, 4);
                addDrop(ObjectRegistry.WINE_RACK_3, nameableContainerDrops(ObjectRegistry.WINE_RACK_3));
                addDrop(ObjectRegistry.WINE_RACK_4);
                addDrop(ObjectRegistry.WINE_RACK_5, nameableContainerDrops(ObjectRegistry.WINE_RACK_5));
            }

            private void addWineRack(Block wineRack, int i) {
                LootTable.Builder builder = drops(wineRack);

                for (int stage = 1; stage < i+1; stage++) {
                    builder.pool(BlockLootTableGenerator.addSurvivesExplosionCondition(wineRack, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ObjectRegistry.BIG_BOTTLE)
                                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(i))
                                            .conditionally(BlockStatePropertyLootCondition.builder(wineRack)
                                                    .properties(StatePredicate.Builder.create().exactMatch(WineRackBlock.STAGE, i)))))
                    ));
                }

                addDrop(wineRack, builder);
            }

            private LootTable.Builder createSlab(Block t) {
                return LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .rolls(ConstantLootNumberProvider.create(1.0f))
                                        .with(BlockLootTableGenerator.applyExplosionDecay(t, ItemEntry.builder(t)
                                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))
                                                        .conditionally(BlockStatePropertyLootCondition.builder(t)
                                                                .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)))))
                        ));
            }

            public static LootTable.Builder flowerBox(ItemConvertible plant) {
                return LootTable.builder()
                        .pool(BlockLootTableGenerator.addSurvivesExplosionCondition(
                                ObjectRegistry.FLOWER_BOX, LootPool.builder()
                                        .rolls(ConstantLootNumberProvider.create(1.0f))
                                        .with(ItemEntry.builder(ObjectRegistry.FLOWER_BOX))))
                        .pool(BlockLootTableGenerator.addSurvivesExplosionCondition(plant, LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                .with(ItemEntry.builder(plant))));
            }
        });
    }
}
