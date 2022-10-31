package daniking.vinery.registry;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.StrippableLogBlock;
import daniking.vinery.GrapeBushSeedItem;
import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.*;
import daniking.vinery.block.FacingBlock;
import daniking.vinery.block.FlowerPotBlock;
import daniking.vinery.item.DrinkBlockItem;
import daniking.vinery.item.FaucetItem;
import daniking.vinery.item.StrawHatItem;
import daniking.vinery.item.WinemakerArmorItem;
import daniking.vinery.util.GrapevineType;
import daniking.vinery.world.VineryConfiguredFeatures;
import net.fabricmc.fabric.api.networking.v1.S2CPlayChannelEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static daniking.vinery.registry.VineryEntites.MULE;
import static daniking.vinery.registry.VineryEntites.WANDERING_WINEMAKER;

public class ObjectRegistry {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
    // Red Grapes
    public static final Block RED_GRAPE_BUSH = register("red_grape_bush", new GrapeBush(getBushSettings(), GrapevineType.RED), false);
    public static final Item RED_GRAPE_SEEDS = register("red_grape_seeds", new GrapeBushSeedItem(RED_GRAPE_BUSH, getSettings(), GrapevineType.RED));

    public static final Item RED_GRAPE = register("red_grape", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.RED));

    public static final Block WHITE_GRAPE_BUSH = register("white_grape_bush", new GrapeBush(getBushSettings(), GrapevineType.WHITE), false);
    public static final Item WHITE_GRAPE_SEEDS = register("white_grape_seeds", new GrapeBushSeedItem(WHITE_GRAPE_BUSH, getSettings(), GrapevineType.WHITE));
    public static final Item WHITE_GRAPE = register("white_grape", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.WHITE));

    public static final Item CHERRY = register("cherry", new Item(getSettings().food(FoodComponents.COOKIE)));

    public static final Block CHERRY_SAPLING = register("cherry_sapling", new SaplingBlock(new SaplingGenerator() {
        @Nullable
        @Override
        protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
            if (random.nextBoolean()) return VineryConfiguredFeatures.CHERRY;
            return VineryConfiguredFeatures.CHERRY_VARIANT;
        }
    }, AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));

    public static final Block OLD_CHERRY_SAPLING = register("old_cherry_sapling", new SaplingBlock(new SaplingGenerator() {
        @Nullable
        @Override
        protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
            if (random.nextBoolean()) {
                if (bees) return VineryConfiguredFeatures.OLD_CHERRY_BEE;
                return VineryConfiguredFeatures.OLD_CHERRY;
            } else {
                if (bees) return VineryConfiguredFeatures.OLD_CHERRY_VARIANT_WITH_BEE;
                return VineryConfiguredFeatures.OLD_CHERRY_VARIANT;
            }
        }
    }, AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), true);

    public static final Block GRAPEVINE_LEAVES = register("grapevine_leaves", new GrapevineLeaves(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));

    public static final Block CHERRY_LEAVES = register("cherry_leaves", new VariantLeavesBlock());





    public static final Block WHITE_GRAPE_CRATE = register("white_grape_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block RED_GRAPE_CRATE = register("red_grape_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));

    public static final Block CHERRY_CRATE = register("cherry_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));

    public static final Block GRAPEVINE_STEM = register("grapevine_stem", new GrapevineStemBlock(getGrapevineSettings()));

    public static final Block GRAPEVINE_POT = register("grapevine_pot", new GrapevinePotBlock(FabricBlockSettings.copyOf(Blocks.BARREL)));

    public static final Block FERMENTATION_BARREL = register("fermentation_barrel", new FermentationBarrelBlock(AbstractBlock.Settings.copy(Blocks.BARREL).nonOpaque()));

    public static final Block WINE_PRESS = register("wine_press", new WinePressBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block TABLE = register("table", new TableBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)));
    public static final Block CHAIR = register("chair", new ChairBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block WOOD_FIRED_OVEN = register("wood_fired_oven", new WoodFiredOvenBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(state -> state.get(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final Block STOVE = register("stove", new StoveBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(12)));

    public static final Block KITCHEN_SINK = register("kitchen_sink", new KitchenSinkBlock(FabricBlockSettings.copy(Blocks.STONE).nonOpaque()));

    public static final Block WINE_RACK_1 = register("wine_rack_1", new WineRackBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), 12));
    public static final Block WINE_RACK_2 = register("wine_rack_2", new WineRackBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), 4));
    public static final Block WINE_RACK_3 = register("wine_rack_3", new WineRackStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), VinerySoundEvents.WINE_RACK_3_OPEN, VinerySoundEvents.WINE_RACK_3_CLOSE));
    public static final Block WINE_RACK_4 = register("wine_rack_4", new FacingBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block WINE_RACK_5 = register("wine_rack_5", new WineRackStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), VinerySoundEvents.WINE_RACK_5_OPEN, VinerySoundEvents.WINE_RACK_5_CLOSE));

    public static final Block STRIPPED_CHERRY_LOG = registerLog("stripped_cherry_log");
    public static final Block CHERRY_LOG = register("cherry_log", new StrippableLogBlock(() -> STRIPPED_CHERRY_LOG, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_CHERRY_WOOD = registerLog("stripped_cherry_wood");
    public static final Block CHERRY_WOOD = register("cherry_wood", new StrippableLogBlock(() -> STRIPPED_CHERRY_WOOD, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_OLD_CHERRY_LOG = registerLog("stripped_old_cherry_log");
    public static final Block OLD_CHERRY_LOG = register("old_cherry_log", new StrippableLogBlock(() -> STRIPPED_OLD_CHERRY_LOG, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_OLD_CHERRY_WOOD = registerLog("stripped_old_cherry_wood");
    public static final Block OLD_CHERRY_WOOD = register("old_cherry_wood", new StrippableLogBlock(() -> STRIPPED_OLD_CHERRY_WOOD, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block CHERRY_PLANKS = register("cherry_planks", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block CHERRY_FLOORBOARD = register("cherry_floorboard", new Block(FabricBlockSettings.copy(CHERRY_PLANKS)));
    public static final Block CHERRY_STAIRS = register("cherry_stairs", new StairsBlock(CHERRY_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(CHERRY_PLANKS)));
    public static final Block CHERRY_SLAB = register("cherry_slab", new SlabBlock(getSlabSettings()));
    public static final Block CHERRY_FENCE = register("cherry_fence", new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
    public static final Block CHERRY_FENCE_GATE = register("cherry_fence_gate", new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
    public static final Block CHERRY_BUTTON = register("cherry_button", new WoodenButtonBlock(AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)));
    public static final Block CHERRY_PRESSURE_PLATE = register("cherry_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)));
    public static final Block CHERRY_DOOR = register("cherry_door", new DoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_DOOR)));
    public static final Block CHERRY_TRAPDOOR = register("cherry_trapdoor", new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)));
    private static final Identifier CHERRY_SIGN_TEXTURE = new VineryIdentifier("entity/sign/cherry");
    public static final TerraformSignBlock CHERRY_SIGN = register("cherry_sign", new TerraformSignBlock(CHERRY_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)), false);
    public static final Block CHERRY_WALL_SIGN = register("cherry_wall_sign", new TerraformWallSignBlock(CHERRY_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)), false);
    public static final Item CHERRY_SIGN_ITEM = register("cherry_sign", new SignItem(getSettings().maxCount(16), CHERRY_SIGN, CHERRY_WALL_SIGN));
    public static final Block WINDOW = register("window", new WindowBlock(FabricBlockSettings.copy(Blocks.GLASS_PANE)));

    public static final Block LOAM = register("loam", new Block(FabricBlockSettings.of(Material.SOIL).strength(2.0F, 3.0F).sounds(BlockSoundGroup.MUD)));
    public static final Block LOAM_STAIRS = register("loam_stairs", new StairsBlock(LOAM.getDefaultState(), FabricBlockSettings.of(Material.SOIL).strength(2.0F, 3.0F).sounds(BlockSoundGroup.MUD)));
    public static final Block LOAM_SLAB = register("loam_slab", new SlabBlock(FabricBlockSettings.of(Material.SOIL).strength(2.0F, 3.0F).sounds(BlockSoundGroup.MUD)));
    public static final Block COARSE_DIRT_SLAB = register("coarse_dirt_slab", new VariantSlabBlock(FabricBlockSettings.copy(Blocks.COARSE_DIRT)));
    public static final Block DIRT_SLAB = register("dirt_slab", new VariantSlabBlock(FabricBlockSettings.copy(Blocks.DIRT)));
    public static final Block GRASS_SLAB = register("grass_slab", new SnowyVariantSlabBlock(FabricBlockSettings.copy(Blocks.GRASS_BLOCK)));

    public static final Block WINE_BOTTLE = register("wine_bottle", new EmptyWineBottleBlock(AbstractBlock.Settings.copy(Blocks.GLASS).breakInstantly().nonOpaque()));
    public static final Block RED_GRAPEJUICE_WINE_BOTTLE = registerWine("red_grapejuice_wine_bottle", new RedGrapejuiceWineBottle(getWineSettings()), null);
    public static final Block WHITE_GRAPEJUICE_WINE_BOTTLE = registerWine("white_grapejuice_wine_bottle", new WhiteGrapejuiceWineBottle(getWineSettings()), null);

    public static final Block CHENET_WINE = registerWine("chenet_wine", new ChenetBottleBlock(getWineSettings()), StatusEffects.JUMP_BOOST);
    public static final Block KING_DANIS_WINE = registerWine("king_danis_wine", new FacingBlock(getWineSettings()), StatusEffects.LUCK);
    public static final Block NOIR_WINE = registerWine("noir_wine", new WineBottleBlock(getWineSettings()), StatusEffects.WATER_BREATHING);
    public static final Block CLARK_WINE = registerWine("clark_wine", new WineBottleBlock(getWineSettings()), StatusEffects.FIRE_RESISTANCE);
    public static final Block MELLOHI_WINE = registerWine("mellohi_wine", new FacingBlock(getWineSettings()), StatusEffects.STRENGTH);
    public static final Block BOLVAR_WINE = registerWine("bolvar_wine", new WineBottleBlock(getWineSettings()), StatusEffects.GLOWING);
    public static final Block CHERRY_WINE = registerWine("cherry_wine", new WineBottleBlock(getWineSettings()), StatusEffects.SPEED);
    public static final Block CHERRY_JAR = register("cherry_jar", new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque()));
    public static final Block CHERRY_JAM = register("cherry_jam", new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque()));
    public static final Block WINE_BOX = register("wine_box", new WineBoxBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).nonOpaque()));
    public static final Block BIG_TABLE = register("big_table", new BigTableBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 2.0F)));

    public static final Block FLOWER_BOX = register("flower_box", new FlowerBoxBlock(Blocks.AIR,FabricBlockSettings.copy(Blocks.FLOWER_POT)));
    public static final Block FLOWER_BOX_ALLIUM = register("flower_box_allium", new FlowerBoxBlock(Blocks.ALLIUM,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_AZURE_BLUET = register("flower_box_azure_bluet", new FlowerBoxBlock(Blocks.AZURE_BLUET,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_ORCHID = register("flower_box_blue_orchid", new FlowerBoxBlock(Blocks.BLUE_ORCHID,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_CORNFLOWER = register("flower_box_cornflower", new FlowerBoxBlock(Blocks.CORNFLOWER,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_DANDELION = register("flower_box_dandelion", new FlowerBoxBlock(Blocks.DANDELION,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_LILY_OF_THE_VALLEY = register("flower_box_lily_of_the_valley", new FlowerBoxBlock(Blocks.LILY_OF_THE_VALLEY,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_ORANGE_TULIP = register("flower_box_orange_tulip", new FlowerBoxBlock(Blocks.ORANGE_TULIP,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_OXEYE_DAISY = register("flower_box_oxeye_daisy", new FlowerBoxBlock(Blocks.OXEYE_DAISY,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_PINK_TULIP = register("flower_box_pink_tulip", new FlowerBoxBlock(Blocks.PINK_TULIP,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_POPPY = register("flower_box_poppy", new FlowerBoxBlock(Blocks.POPPY,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_RED_TULIP = register("flower_box_red_tulip", new FlowerBoxBlock(Blocks.RED_TULIP,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_WHITE_TULIP = register("flower_box_white_tulip", new FlowerBoxBlock(Blocks.WHITE_TULIP,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);
    public static final Block FLOWER_BOX_BLUE_WHITER_ROSE = register("flower_box_whiter_rose", new FlowerBoxBlock(Blocks.WITHER_ROSE,FabricBlockSettings.copy(Blocks.FLOWER_POT)), false);

    public static final Block FLOWER_POT = register("flower_pot", new FlowerPotBlock(FabricBlockSettings.copy(Blocks.FLOWER_POT)));

    public static final Block BASKET = register("basket", new Block(FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()));
    public static final Block COOKING_POT = register("cooking_pot", new CookingPotBlock(FabricBlockSettings.of(Material.STONE).breakInstantly().nonOpaque()));
    public static final Block STACKABLE_LOG = register("stackable_log", new StackableLogBlock(getLogBlockSettings().nonOpaque()));
    public static final Item FAUCET = register("faucet", new FaucetItem(getSettings()));

    public static final Item STRAW_HAT = register("straw_hat", new StrawHatItem(getSettings()));
    public static final Item VINEMAKER_APRON = register("vinemaker_apron", new WinemakerArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.CHEST, getSettings()));
    public static final Item VINEMAKER_LEGGINGS = register("vinemaker_leggings", new WinemakerArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.LEGS, getSettings()));
    public static final Item VINEMAKER_BOOTS = register("vinemaker_boots", new WinemakerArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.FEET, getSettings()));
    public static final Item CHOCOLATE_BREAD = register("chocolate_bread", new Item(getSettings().food(FoodComponents.BREAD)));
    public static final Item TOAST = register("toast", new Item(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final Item DONUT = register("donut", new Item(getSettings().food(FoodComponents.CARROT)));
    public static final Item MILK_BREAD = register("milk_bread", new Item(getSettings().food(FoodComponents.COOKIE)));
    public static final Item GLOVES = register("gloves", new Item(getSettings()));
    public static final Block CRUSTY_BREAD = register("crusty_bread", new BreadBlock(AbstractBlock.Settings.copy(Blocks.CAKE).nonOpaque()));

    public static final Item MULE_SPAWN_EGG = register("mule_spawn_egg", new SpawnEggItem(MULE, 0x8b7867, 0x5a4e43, getSettings()));
    public static final Item WANDERING_WINEMAKER_SPAWN_EGG = register("wandering_winemaker_spawn_egg", new SpawnEggItem(WANDERING_WINEMAKER, 0xb78272, 0x3c4a73, getSettings()));


    private static PillarBlock registerLog(String path) {
        return register(path, new PillarBlock(getLogBlockSettings()));
    }

    private static <T extends Block> T register(String path, T block) {
        return register(path, block, true);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem) {
        return register(path, block, registerItem, settings -> {});
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem, Consumer<Item.Settings> consumer) {
        return register(path, block, registerItem, BlockItem::new, consumer);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem, BiFunction<T, Item.Settings, ? extends BlockItem> function,  Consumer<Item.Settings> consumer) {
        final Identifier id = new VineryIdentifier(path);
        BLOCKS.put(id, block);
        if (registerItem) {
            ITEMS.put(id, function.apply(block, getSettings(consumer)));
        }
        return block;
    }

    private static <T extends Block> T registerWine(String path, T block, StatusEffect effect) {
        return register(path, block, true, DrinkBlockItem::new, settings -> settings.food(wineFoodComponent(effect)));
    }

    private static FoodComponent wineFoodComponent(StatusEffect effect) {
        FoodComponent.Builder component = new FoodComponent.Builder().hunger(1);
        if(effect != null) component.statusEffect(new StatusEffectInstance(effect, 45 * 20), 1.0f);
        return component.build();
    }

    private static <T extends Item> T register(String path, T item) {
        final Identifier id = new VineryIdentifier(path);
        ITEMS.put(id, item);
        return item;
    }

    public static void init() {
        for (Map.Entry<Identifier, Block> entry : BLOCKS.entrySet()) {
            Registry.register(Registry.BLOCK, entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Identifier, Item> entry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, entry.getKey(), entry.getValue());
        }
        FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
        flammableRegistry.add(CHERRY_PLANKS, 5, 20);
        flammableRegistry.add(STRIPPED_CHERRY_LOG, 5, 5);
        flammableRegistry.add(STRIPPED_OLD_CHERRY_LOG, 5, 5);
        flammableRegistry.add(CHERRY_LOG, 5, 5);
        flammableRegistry.add(OLD_CHERRY_LOG, 5, 5);
        flammableRegistry.add(STRIPPED_CHERRY_WOOD, 5, 5);
        flammableRegistry.add(CHERRY_WOOD, 5, 5);
        flammableRegistry.add(OLD_CHERRY_WOOD, 5, 5);
        flammableRegistry.add(STRIPPED_OLD_CHERRY_WOOD, 5, 5);
        flammableRegistry.add(CHERRY_SLAB, 5, 20);
        flammableRegistry.add(CHERRY_STAIRS, 5, 20);
        flammableRegistry.add(CHERRY_FENCE, 5, 20);
        flammableRegistry.add(CHERRY_FENCE_GATE, 5, 20);
        FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
        fuelRegistry.add(CHERRY_FENCE, 300);
        fuelRegistry.add(CHERRY_FENCE_GATE, 300);
        fuelRegistry.add(STACKABLE_LOG, 300);
        fuelRegistry.add(FERMENTATION_BARREL, 300);
    }

    private static Item.Settings getSettings(Consumer<Item.Settings> consumer) {
        Item.Settings settings = new Item.Settings().group(Vinery.CREATIVE_TAB);
        consumer.accept(settings);
        return settings;
    }

    private static Item.Settings getSettings() {
        return getSettings(settings -> {});
    }

    private static Block.Settings getVineSettings() {
        return FabricBlockSettings.copyOf(Blocks.VINE);
    }

    private static Block.Settings getBushSettings() {
        return FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH);
    }


    private static AbstractBlock.Settings getGrassSettings() {
        return FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).nonOpaque();
    }

    private static AbstractBlock.Settings getGrapevineSettings() {
        return FabricBlockSettings.of(Material.WOOD).strength(2.0F).ticksRandomly().sounds(BlockSoundGroup.WOOD);
    }

    private static AbstractBlock.Settings getLogBlockSettings() {
        return AbstractBlock.Settings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD);
    }

    private static AbstractBlock.Settings getSlabSettings() {
        return getLogBlockSettings().resistance(3.0F);
    }

    private static AbstractBlock.Settings getWineSettings() {
        return AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque().breakInstantly();
    }

    public static Map<Identifier, Block> getBlocks() {
        return Collections.unmodifiableMap(BLOCKS);
    }

    public static Map<Identifier, Item> getItems() {
        return Collections.unmodifiableMap(ITEMS);
    }

}
