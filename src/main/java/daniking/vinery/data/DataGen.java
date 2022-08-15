package daniking.vinery.data;

import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.*;
import daniking.vinery.data.recipe.ExtendedShapedRecipeJsonBuilder;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VineryBoatTypes;
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
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static daniking.vinery.data.recipe.ExtendedShapedRecipeJsonBuilder.createExtended;

public class DataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(gen -> new FabricRecipeProvider(gen) {
            @Override
            protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
                shapeless(exporter, "banner", ObjectRegistry.BANNER, "has_red_grape", ObjectRegistry.RED_GRAPE, Items.WHITE_BANNER, ObjectRegistry.RED_GRAPE);
                shaped(exporter, "big_bottle", ObjectRegistry.BIG_BOTTLE, "has_glass", Items.GLASS, "##", "##", "##", '#', Items.GLASS);
                shaped(exporter, "flower_pot", ObjectRegistry.FLOWER_POT, "has_brick", Items.BRICK, "# #", "# #", " # ", '#', Items.BRICK);
                shaped(exporter, "big_table", ObjectRegistry.BIG_TABLE, "has_iron_ingot", Items.IRON_INGOT, "iii", "SSS", 'i', Items.IRON_INGOT, 'S', Items.SPRUCE_PLANKS);
                shaped(exporter, "boat", VineryBoatTypes.cherry.getItem(), "has_cherry_planks", ObjectRegistry.CHERRY_PLANKS, "# #", "###", '#', ObjectRegistry.CHERRY_PLANKS);
                shapeless(exporter, "wooden_button", ObjectRegistry.CHERRY_BUTTON, "has_cherry_planks", ObjectRegistry.CHERRY_PLANKS, ObjectRegistry.CHERRY_PLANKS);
                shaped(exporter, "door", ObjectRegistry.CHERRY_DOOR, "has_cherry_planks", ObjectRegistry.CHERRY_PLANKS, "##", "##", "##", '#', ObjectRegistry.CHERRY_PLANKS);
                shapeless(exporter, "door", ObjectRegistry.CHERRY_DOOR_WITH_IRON_BARS, "has_iron_bars", Items.IRON_BARS, ObjectRegistry.CHERRY_DOOR, Items.IRON_BARS);
                shaped(exporter, "wooden_fence", ObjectRegistry.CHERRY_FENCE, 3, "has_stick", Items.STICK, "W#W", "W#W", 'W', ObjectRegistry.CHERRY_PLANKS, '#', Items.STICK);
                shaped(exporter, "wooden_fence_gate", ObjectRegistry.CHERRY_FENCE_GATE, 3, "has_stick", Items.STICK, "#W#", "#W#", 'W', ObjectRegistry.CHERRY_PLANKS, '#', Items.STICK);
                shaped(exporter, "floorboard", ObjectRegistry.CHERRY_FLOORBOARD, "has_cherry_wood", ObjectRegistry.CHERRY_WOOD, "#  ", "###", "  #", '#', ObjectRegistry.CHERRY_WOOD);
                shaped(exporter, "cooking_pot", ObjectRegistry.COOKING_POT, "has_iron_ingot", Items.IRON_INGOT, "ISI", "III", 'I', Items.IRON_INGOT, 'S', Items.WOODEN_SHOVEL);
                shaped(exporter, "cherry_jar", ObjectRegistry.CHERRY_JAR, "has_glass", Items.GLASS, "S", "G", "G", 'S', Items.OAK_SLAB, 'G', Items.GLASS);
                shapeless(exporter, "planks", ObjectRegistry.CHERRY_PLANKS, 4, "has_cherry_logs", Vinery.CHERRY_LOGS, Vinery.CHERRY_LOGS);
                shaped(exporter, "wooden_pressure_plate", ObjectRegistry.CHERRY_PRESSURE_PLATE, "has_cherry_planks", ObjectRegistry.CHERRY_PLANKS, "##", '#', ObjectRegistry.CHERRY_PLANKS);
                shaped(exporter, "wooden_sign", ObjectRegistry.CHERRY_SIGN, 3, "has_cherry_planks", ObjectRegistry.CHERRY_PLANKS, "###", "###", " X ", '#', ObjectRegistry.CHERRY_PLANKS, 'X', Items.STICK);
                shaped(exporter, "slab", ObjectRegistry.CHERRY_SLAB, 6, "has_cherry_planks", ObjectRegistry.CHERRY_PLANKS, "###", '#', ObjectRegistry.CHERRY_PLANKS);
                shaped(exporter, "stairs", ObjectRegistry.CHERRY_STAIRS, 4, "has_cherry_planks", ObjectRegistry.CHERRY_PLANKS, "#  ", "## ", "###", '#', ObjectRegistry.CHERRY_PLANKS);
                shaped(exporter, "wooden_trapdoor", ObjectRegistry.CHERRY_TRAPDOOR, 4, "has_cherry_planks", ObjectRegistry.CHERRY_PLANKS, "###", "###", '#', ObjectRegistry.CHERRY_PLANKS);
                shaped(exporter, "wood", ObjectRegistry.CHERRY_WOOD, 3, "has_cherry_wood", ObjectRegistry.CHERRY_LOG, "##", "##", '#', ObjectRegistry.CHERRY_LOG);
                shapeless(exporter, "slab", ObjectRegistry.COARSE_DIRT_SLAB, "has_coarse_dirt", Items.COARSE_DIRT, Items.COARSE_DIRT, Items.COARSE_DIRT, Items.COARSE_DIRT);
                shapeless(exporter, "slab", ObjectRegistry.DIRT_SLAB, "has_dirt", Items.DIRT, Items.DIRT, Items.DIRT, Items.DIRT);
                shaped(exporter, "faucet", ObjectRegistry.FAUCET, "has_iron_ingot", Items.IRON_INGOT, "iii", "i i", "  W", 'i', Items.IRON_INGOT, 'W', Items.WATER_BUCKET);
                shaped(exporter, "fermentation_barrel", ObjectRegistry.FERMENTATION_BARREL, "has_barrel", Items.BARREL, " B ", "S S", 'B', Items.BARREL, 'S', Items.STICK);
                shaped(exporter, "flower_box", ObjectRegistry.FLOWER_BOX, "has_podzol", Items.PODZOL, "###", "SSS", '#', Items.PODZOL, 'S', Items.SPRUCE_PLANKS);
                shaped(exporter, "grapevine_pot", ObjectRegistry.GRAPEVINE_POT, "has_spruce_planks", Items.SPRUCE_PLANKS, "_ _", "SSS", '_', Items.SPRUCE_SLAB, 'S', Items.SPRUCE_PLANKS);
                shaped(exporter, "grapevine_stem", ObjectRegistry.GRAPEVINE_STEM, 4, "has_oak_log", Items.OAK_LOG, "#", "#", '#', Items.OAK_LOG);
                shapeless(exporter, "slab", ObjectRegistry.GRASS_SLAB, "has_grass_block", Items.GRASS_BLOCK, Items.GRASS_BLOCK, Items.GRASS_BLOCK, Items.GRASS_BLOCK);
                shaped(exporter, "kitchen_sink", ObjectRegistry.KITCHEN_SINK, "has_cauldron", Items.CAULDRON, "iCi", "BBB", 'i', Items.IRON_INGOT, 'C', Items.CAULDRON, 'B', Items.BRICKS);
                shaped(exporter, "loam", ObjectRegistry.LOAM, 2, "has_clay_ball", Items.CLAY_BALL, "#S", "S#", '#', Items.CLAY_BALL, 'S', Items.SAND);
                shaped(exporter, "wood", ObjectRegistry.OLD_CHERRY_WOOD, 3, "has_old_cherry_wood", ObjectRegistry.OLD_CHERRY_LOG, "##", "##", '#', ObjectRegistry.OLD_CHERRY_LOG);
                shaped(exporter, "grape_crate", ObjectRegistry.RED_GRAPE_CRATE, "has_red_grape", ObjectRegistry.RED_GRAPE, "###", "###", "###", '#', ObjectRegistry.RED_GRAPE);
                shaped(exporter, "stackable_logs", ObjectRegistry.STACKABLE_LOG, "has_oak_log", Items.OAK_LOG, "SSS", "###", 'S',Items.STICK, '#', Items.OAK_LOG);
                shaped(exporter, "stove", ObjectRegistry.STOVE, "has_campfire", Items.CAMPFIRE, "III", "BCB", 'I', Items.IRON_INGOT, 'B', Items.BRICKS, 'C', Items.CAMPFIRE);
                shaped(exporter, "stripped_wood", ObjectRegistry.STRIPPED_CHERRY_WOOD, 3, "has_stripped_cherry_log", ObjectRegistry.STRIPPED_CHERRY_LOG, "##", "##", '#', ObjectRegistry.STRIPPED_CHERRY_LOG);
                shaped(exporter, "stripped_wood", ObjectRegistry.STRIPPED_OLD_CHERRY_LOG, 3, "has_stripped_old_cherry_log", ObjectRegistry.STRIPPED_OLD_CHERRY_LOG, "##", "##", '#', ObjectRegistry.STRIPPED_OLD_CHERRY_LOG);
                shaped(exporter, "straw_hat", ObjectRegistry.STRAW_HAT, "has_wheat", Items.WHEAT, " X ", " G ", " X ", 'X', Items.WHEAT, 'G', Items.RED_WOOL);
                shaped(exporter, "vinemaker_apron", ObjectRegistry.VINEMAKER_APRON, "has_red_wool", Items.RED_WOOL, "# #", "###", "###", '#', Items.RED_WOOL);
                shaped(exporter, "vinemaker_boots", ObjectRegistry.VINEMAKER_BOOTS, "has_brown_wool", Items.BROWN_WOOL, "# #", "# #", '#', Items.BROWN_WOOL);
                shaped(exporter, "vinemaker_leggings", ObjectRegistry.VINEMAKER_LEGGINGS, "has_gold_ingot", Items.GOLD_INGOT, "XUX", "I N", "N I", 'X', Items.LEATHER, 'U', Items.GOLD_INGOT, 'I', Items.WHITE_WOOL, 'N', Items.BLUE_WOOL);
                shaped(exporter, "grape_crate", ObjectRegistry.WHITE_GRAPE_CRATE, "has_white_grape", ObjectRegistry.WHITE_GRAPE, "###", "###", "###", '#', ObjectRegistry.WHITE_GRAPE);
                shaped(exporter, "wine_bottle", ObjectRegistry.WINE_BOTTLE, "has_glass_bottle", Items.GLASS_BOTTLE, "#", "#", '#', Items.GLASS_BOTTLE);
                shaped(exporter, "wine_box", ObjectRegistry.WINE_BOX, "has_glass", Items.GLASS, "_G_", "___", '_', Items.SPRUCE_SLAB, 'G', Items.GLASS);
                shaped(exporter, "wine_press", ObjectRegistry.WINE_PRESS, "has_iron_trapdoor", Items.IRON_TRAPDOOR, " # ", "/B/", "/ /", '#', Items.IRON_TRAPDOOR, '/', Items.STICK, 'B', Items.BARREL);
                shaped(exporter, "wine_rack", ObjectRegistry.WINE_RACK_1, "has_spruce_planks", Items.SPRUCE_PLANKS, "#_#", "_O_", "#_#", '#', Items.SPRUCE_PLANKS, '_', Items.SPRUCE_SLAB, 'O', Items.OAK_TRAPDOOR);
                shaped(exporter, "wine_rack", ObjectRegistry.WINE_RACK_2, "has_spruce_planks", Items.SPRUCE_PLANKS, "# #", " _ ", "# #", '#', Items.SPRUCE_PLANKS, '_', Items.SPRUCE_SLAB);
                shaped(exporter, "wine_rack", ObjectRegistry.WINE_RACK_3, "has_spruce_planks", Items.SPRUCE_PLANKS, "# #", " B ", "# #", '#', Items.SPRUCE_PLANKS, 'B', Items.BARREL);
                shaped(exporter, "wine_rack", ObjectRegistry.WINE_RACK_4, "has_spruce_planks", Items.SPRUCE_PLANKS, "#_#", "_ _", "#_#", '#', Items.SPRUCE_PLANKS, '_', Items.SPRUCE_SLAB);
                shaped(exporter, "wine_rack", ObjectRegistry.WINE_RACK_5, "has_spruce_planks", Items.SPRUCE_PLANKS, "#_#", "_B_", "#B#", '#', Items.SPRUCE_PLANKS, '_', Items.SPRUCE_SLAB, 'B', Items.BARREL);
                shaped(exporter, "window", ObjectRegistry.WINDOW_1, "has_black_stained_glass_pane", Items.BLACK_STAINED_GLASS_PANE, "XOI", "OOO", "IOX", 'X', Items.YELLOW_STAINED_GLASS_PANE, 'O', Items.BLACK_STAINED_GLASS_PANE, 'I', Items.ORANGE_STAINED_GLASS_PANE);
                shaped(exporter, "window", ObjectRegistry.WINDOW_2, "has_black_stained_glass_pane", Items.BLACK_STAINED_GLASS_PANE, "XXX", "XXX", "XXX", 'X', Items.BLACK_STAINED_GLASS_PANE);
            }

            public ExtendedShapedRecipeJsonBuilder shaped(String group, ItemConvertible output, String criterionName, Object criterionItem) {
                return createExtended(output).group(group).criterion(criterionName, criterionItem);
            }

            private ExtendedShapedRecipeJsonBuilder shaped(String group, ItemConvertible output, int count, String criterionName, Object criterionItem) {
                return createExtended(output, count).group(group).criterion(criterionName, criterionItem);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, char name1, Object item1) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).input(name1, item1).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, int count, String criterionName, Object criterionItem, String row1, char name1, Object item1) {
                shaped(group, output, count, criterionName, criterionItem).pattern(row1).input(name1, item1).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, char name1, Object item1, char name2, Object item2) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).input(name1, item1).input(name2, item2).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, char name1, Object item1, char name2, Object item2, char name3, Object item3) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).input(name1, item1).input(name2, item2).input(name3, item3).offerTo(exporter);
            }

            public ShapedRecipeJsonBuilder shaped(String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2) {
                return shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, char name1, Object item1) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).input(name1, item1).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, int count, String criterionName, Object criterionItem, String row1, String row2, char name1, Object item1) {
                shaped(group, output, count, criterionName, criterionItem).pattern(row1).pattern(row2).input(name1, item1).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, char name1, Object item1, char name2, Object item2) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).input(name1, item1).input(name2, item2).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, int count, String criterionName, Object criterionItem, String row1, String row2, char name1, Object item1, char name2, Object item2) {
                shaped(group, output, count, criterionName, criterionItem).pattern(row1).pattern(row2).input(name1, item1).input(name2, item2).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, char name1, Object item1, char name2, Object item2, char name3, Object item3) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).input(name1, item1).input(name2, item2).input(name3, item3).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4, char name5, Object item5) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).input(name5, item5).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4, char name5, Object item5, char name6, Object item6) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).input(name5, item5).input(name6, item6).offerTo(exporter);
            }

            public ShapedRecipeJsonBuilder shaped(String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3) {
                return shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, int count, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1) {
                shaped(group, output, count, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, int count, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2) {
                shaped(group, output, count, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2, char name3, Object item3) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).input(name3, item3).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4, char name5, Object item5) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).input(name5, item5).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4, char name5, Object item5, char name6, Object item6) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).input(name5, item5).input(name6, item6).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4, char name5, Object item5, char name6, Object item6, char name7, Object item7) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).input(name5, item5).input(name6, item6).input(name7, item7).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4, char name5, Object item5, char name6, Object item6, char name7, Object item7, char name8, Object item8) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).input(name5, item5).input(name6, item6).input(name7, item7).input(name8, item8).offerTo(exporter);
            }

            public void shaped(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, String row1, String row2, String row3, char name1, Object item1, char name2, Object item2, char name3, Object item3, char name4, Object item4, char name5, Object item5, char name6, Object item6, char name7, Object item7, char name8, Object item8, char name9, Object item9) {
                shaped(group, output, criterionName, criterionItem).pattern(row1).pattern(row2).pattern(row3).input(name1, item1).input(name2, item2).input(name3, item3).input(name4, item4).input(name5, item5).input(name6, item6).input(name7, item7).input(name8, item8).input(name9, item9).offerTo(exporter);
            }

            public void shapeless(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, String criterionName, Object criterionItem, Object... items) {
                shapeless(exporter, group, output, 1, criterionName, criterionItem, items);
            }

            public void shapeless(Consumer<RecipeJsonProvider> exporter, String group, ItemConvertible output, int count, String criterionName, Object criterionItem, Object... items) {
                ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(output).group(group);

                if(criterionItem instanceof ItemConvertible item) builder.criterion(criterionName, RecipeProvider.conditionsFromItem(item));
                if(criterionItem instanceof TagKey key) builder.criterion(criterionName, RecipeProvider.conditionsFromTag(key));
                Stream.of(items).map(a -> {
                    if(a instanceof ItemConvertible item) return Ingredient.ofItems(item);
                    if(a instanceof TagKey tag) return Ingredient.fromTag(tag);
                    return Ingredient.empty();
                }).forEach(builder::input);
                builder.offerTo(exporter);
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

                generator.blockStateCollector.accept(
                        VariantsBlockStateSupplier
                                .create(ObjectRegistry.CRUSTY_BREAD)
                                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                                .coordinate(BlockStateVariantMap.create(BreadBlock.JAM, BreadBlock.BITES)
                                        .register((jam, bite) -> BlockStateVariant.create().put(VariantSettings.MODEL, new VineryIdentifier("block/bread_" + (jam ? "jam_" : "") + bite)))));

                registerLeaves(generator, ObjectRegistry.CHERRY_LEAVES);
                registerLeaves(generator, ObjectRegistry.PINK_CHERRY_LEAVES);
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
                registerItem(generator, ObjectRegistry.CRUSTY_BREAD);
            }

            private void registerLeaves(BlockStateModelGenerator generator, Block block) {
                generator.blockStateCollector.accept(
                        VariantsBlockStateSupplier
                                .create(block)
                                .coordinate(BlockStateVariantMap.create(VariantLeavesBlock.VARIANT)
                                        .register(variant -> BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, TextureMap.getSubId(block, (variant !=0 ? "_variant" : ""))))));
            }

            private void registerWine(BlockStateModelGenerator generator, Block block) {
                registerWine(generator, block, true);
            }

            private void registerWine(BlockStateModelGenerator generator, Block block, boolean isStack) {
                String suffix = isStack ? "_stack" : "";

                generator.blockStateCollector.accept(
                        VariantsBlockStateSupplier
                                .create(block).coordinate(createWestDefaultHorizontalRotationStates())
                                .coordinate(BlockStateVariantMap.create(StackableBlock.STACK).register(stack -> BlockStateVariant.create().put(VariantSettings.MODEL, TextureMap.getSubId(block, suffix + stack)))));


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
                getOrCreateTagBuilder(Vinery.WINE_RACK)
                        .add(
                                ObjectRegistry.WINE_RACK_1,
                                ObjectRegistry.WINE_RACK_2,
                                ObjectRegistry.WINE_RACK_3,
                                ObjectRegistry.WINE_RACK_4,
                                ObjectRegistry.WINE_RACK_5
                        );

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

                getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(
                        ObjectRegistry.RED_GRAPE_BUSH,
                        ObjectRegistry.WHITE_GRAPE_BUSH,
                        ObjectRegistry.GRAPEVINE_STEM,
                        ObjectRegistry.CHERRY_PLANKS,
                        ObjectRegistry.CHERRY_LOG,
                        ObjectRegistry.OLD_CHERRY_LOG,
                        ObjectRegistry.CHERRY_PLANKS,
                        ObjectRegistry.STACKABLE_LOG,
                        ObjectRegistry.FERMENTATION_BARREL,
                        ObjectRegistry.CHERRY_FLOORBOARD,
                        ObjectRegistry.RED_GRAPE_CRATE,
                        ObjectRegistry.WHITE_GRAPE_CRATE,
                        ObjectRegistry.FLOWER_BOX,
                        ObjectRegistry.WINE_PRESS,
                        ObjectRegistry.WINE_BOX
                ).addTag(Vinery.WINE_RACK);

                getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
                        ObjectRegistry.ROCKS,
                        ObjectRegistry.ROCKS_VARIANT_B,
                        ObjectRegistry.ROCKS_VARIANT_C,
                        ObjectRegistry.STOVE,
                        ObjectRegistry.WINDOW_2,
                        ObjectRegistry.WINDOW_1,
                        ObjectRegistry.BIG_TABLE,
                        ObjectRegistry.KITCHEN_SINK,
                        ObjectRegistry.FLOWER_POT,
                        ObjectRegistry.COOKING_POT
                );

                getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(
                        ObjectRegistry.LOAM,
                        ObjectRegistry.COARSE_DIRT_SLAB,
                        ObjectRegistry.DIRT_SLAB,
                        ObjectRegistry.GRASS_SLAB
                );
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
                addDrop(ObjectRegistry.GRASS_SLAB, createSlab(ObjectRegistry.GRASS_SLAB));
                addDrop(ObjectRegistry.RED_GRAPE_CRATE);
                addDrop(ObjectRegistry.WHITE_GRAPE_CRATE);
                addDrop(ObjectRegistry.FLOWER_POT, drops(ObjectRegistry.FLOWER_POT)
                        .pool(BlockLootTableGenerator.addSurvivesExplosionCondition(ObjectRegistry.FLOWER_POT, LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                        .with(flowerPot(EnumTallFlower.NONE))
                                        .with(flowerPot(EnumTallFlower.LILAC))
                                        .with(flowerPot(EnumTallFlower.ROSE_BUSH))
                                        .with(flowerPot(EnumTallFlower.PEONY)))
                                .build()
                ));
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
                addDrop(ObjectRegistry.BOLVAR_WINE);
                addDrop(ObjectRegistry.CHERRY_WINE);
                addDrop(ObjectRegistry.CHERRY_FLOORBOARD);
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

            private LeafEntry.Builder<?> flowerPot(EnumTallFlower flower) {
                return ItemEntry.builder(flower.getFlower())
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))
                                .conditionally(BlockStatePropertyLootCondition.builder(ObjectRegistry.FLOWER_POT)
                                        .properties(StatePredicate.Builder.create().exactMatch(FlowerPotBlock.CONTENT, flower))));
            }

            private void addWineRack(Block wineRack, int i) {
                LootTable.Builder builder = drops(wineRack);

                LootPool.Builder builderLootTable = BlockLootTableGenerator.addSurvivesExplosionCondition(wineRack, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)));

                for (int stage = 1; stage < i+1; stage++) {
                    builderLootTable.with(ItemEntry.builder(ObjectRegistry.BIG_BOTTLE)
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(i))
                                    .conditionally(BlockStatePropertyLootCondition.builder(wineRack)
                                            .properties(StatePredicate.Builder.create().exactMatch(WineRackBlock.STAGE, i)))));
                }

                builder.pool(builderLootTable);

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
