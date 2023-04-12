package satisfyu.vinery.registry;

import com.mojang.datafixers.util.Pair;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.StrippableLogBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.FlowerPotBlock;
import satisfyu.vinery.block.*;
import satisfyu.vinery.block.crops.TomatoCropBlock;
import satisfyu.vinery.block.grape.GrapeBush;
import satisfyu.vinery.block.grape.GrapeVineBlock;
import satisfyu.vinery.block.grape.SavannaGrapeBush;
import satisfyu.vinery.block.grape.TaigaGrapeBush;
import satisfyu.vinery.block.stem.LatticeStemBlock;
import satisfyu.vinery.block.stem.PaleStemBlock;
import satisfyu.vinery.item.*;
import satisfyu.vinery.util.GrapevineType;
import satisfyu.vinery.util.VineryFoodComponent;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ObjectRegistry {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
    public static final Item CHERRY = register("cherry", new CherryItem(getSettings().food(FoodComponents.COOKIE)));
    public static final Item ROTTEN_CHERRY = register("rotten_cherry", new RottenCherryItem(getSettings().food(FoodComponents.POISONOUS_POTATO)));
    public static final Block RED_GRAPE_BUSH = register("red_grape_bush", new GrapeBush(getBushSettings(), GrapevineType.RED), false);
    public static final Item RED_GRAPE_SEEDS = register("red_grape_seeds", new GrapeBushSeedItem(RED_GRAPE_BUSH, getSettings(), GrapevineType.RED));
    public static final Item RED_GRAPE = register("red_grape", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.RED, ObjectRegistry.RED_GRAPE_SEEDS.getDefaultStack()));
    public static final Block WHITE_GRAPE_BUSH = register("white_grape_bush", new GrapeBush(getBushSettings(), GrapevineType.WHITE), false);
    public static final Item WHITE_GRAPE_SEEDS = register("white_grape_seeds", new GrapeBushSeedItem(WHITE_GRAPE_BUSH, getSettings(), GrapevineType.WHITE));
    public static final Item WHITE_GRAPE = register("white_grape", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.WHITE, ObjectRegistry.WHITE_GRAPE_SEEDS.getDefaultStack()));

    public static final Block SAVANNA_RED_GRAPE_BUSH = register("savanna_grape_bush_red", new SavannaGrapeBush(getBushSettings(), GrapevineType.SAVANNA_RED), false);
    public static final Item SAVANNA_RED_GRAPE_SEEDS = register("savanna_grape_seeds_red", new GrapeBushSeedItem(SAVANNA_RED_GRAPE_BUSH, getSettings(), GrapevineType.SAVANNA_RED));
    public static final Item SAVANNA_RED_GRAPE = register("savanna_grapes_red", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.SAVANNA_RED, ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS.getDefaultStack()));
    public static final Block SAVANNA_WHITE_GRAPE_BUSH = register("savanna_grape_bush_white", new SavannaGrapeBush(getBushSettings(), GrapevineType.SAVANNA_WHITE), false);
    public static final Item SAVANNA_WHITE_GRAPE_SEEDS = register("savanna_grape_seeds_white", new GrapeBushSeedItem(SAVANNA_WHITE_GRAPE_BUSH, getSettings(), GrapevineType.SAVANNA_WHITE));
    public static final Item SAVANNA_WHITE_GRAPE = register("savanna_grapes_white", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.SAVANNA_WHITE, ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS.getDefaultStack()));

    public static final Block TAIGA_RED_GRAPE_BUSH = register("taiga_grape_bush_red", new TaigaGrapeBush(getBushSettings(), GrapevineType.TAIGA_RED), false);
    public static final Item TAIGA_RED_GRAPE_SEEDS = register("taiga_grape_seeds_red", new GrapeBushSeedItem(TAIGA_RED_GRAPE_BUSH, getSettings(), GrapevineType.TAIGA_RED));
    public static final Item TAIGA_RED_GRAPE = register("taiga_grapes_red", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.TAIGA_RED, ObjectRegistry.TAIGA_RED_GRAPE_SEEDS.getDefaultStack()));
    public static final Block TAIGA_WHITE_GRAPE_BUSH = register("taiga_grape_bush_white", new TaigaGrapeBush(getBushSettings(), GrapevineType.TAIGA_WHITE), false);
    public static final Item TAIGA_WHITE_GRAPE_SEEDS = register("taiga_grape_seeds_white", new GrapeBushSeedItem(TAIGA_WHITE_GRAPE_BUSH, getSettings(), GrapevineType.TAIGA_WHITE));
    public static final Item TAIGA_WHITE_GRAPE = register("taiga_grapes_white", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.TAIGA_WHITE,ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS.getDefaultStack()));

    public static final Block JUNGLE_RED_GRAPE_BUSH = register("jungle_grape_bush_red", new GrapeVineBlock(getBushSettings(), GrapevineType.JUNGLE_RED), false);
    public static final Item JUNGLE_RED_GRAPE_SEEDS = register("jungle_grape_seeds_red", new GrapeBushSeedItem(JUNGLE_RED_GRAPE_BUSH, getSettings(), GrapevineType.JUNGLE_RED));
    public static final Item JUNGLE_RED_GRAPE = register("jungle_grapes_red", new GrapeItem(getSettings().food(FoodComponents.BAKED_POTATO), GrapevineType.JUNGLE_RED, ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS.getDefaultStack()));
    public static final Block JUNGLE_WHITE_GRAPE_BUSH = register("jungle_grape_bush_white", new GrapeVineBlock(getBushSettings(), GrapevineType.JUNGLE_WHITE), false);
    public static final Item JUNGLE_WHITE_GRAPE_SEEDS = register("jungle_grape_seeds_white", new GrapeBushSeedItem(JUNGLE_WHITE_GRAPE_BUSH, getSettings(), GrapevineType.JUNGLE_WHITE));
    public static final Item JUNGLE_WHITE_GRAPE = register("jungle_grapes_white", new GrapeItem(getSettings().food(FoodComponents.BAKED_POTATO), GrapevineType.JUNGLE_WHITE, ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS.getDefaultStack()));
    /*
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

     */
    public static final Block GRAPEVINE_LEAVES = register("grapevine_leaves", new GrapevineLeaves(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));
    public static final Block CHERRY_LEAVES = register("cherry_leaves", new CherryLeaves(FabricBlockSettings.copy(Blocks.OAK_LEAVES)));
    public static final Block WHITE_GRAPE_CRATE = register("white_grape_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block RED_GRAPE_CRATE = register("red_grape_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block CHERRY_CRATE = register("cherry_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block APPLE_CRATE = register("apple_crate", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block GRAPEVINE_POT = register("grapevine_pot", new GrapevinePotBlock(FabricBlockSettings.copyOf(Blocks.BARREL)));
    public static final Block FERMENTATION_BARREL = register("fermentation_barrel", new FermentationBarrelBlock(AbstractBlock.Settings.copy(Blocks.BARREL).nonOpaque()));
    public static final Block WINE_PRESS = register("wine_press", new WinePressBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block CHAIR = register("chair", new ChairBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)));
    public static final Block TABLE = register("table", new TableBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)));
    public static final Block WOOD_FIRED_OVEN = register("wood_fired_oven", new WoodFiredOvenBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(state -> state.get(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final Block STOVE = register("stove", new StoveBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(12)));
    public static final Block KITCHEN_SINK = register("kitchen_sink", new KitchenSinkBlock(FabricBlockSettings.copy(Blocks.STONE).nonOpaque()));
    public static final Block WINE_RACK_1 = register("wine_rack_1", new NineBottleStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block WINE_RACK_2 = register("wine_rack_2", new FourBottleStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block WINE_RACK_3 = register("wine_rack_3", new WineRackStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), VinerySoundEvents.WINE_RACK_3_OPEN, VinerySoundEvents.WINE_RACK_3_CLOSE));
    public static final Block WINE_RACK_5 = register("wine_rack_5", new WineRackStorageBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), VinerySoundEvents.WINE_RACK_5_OPEN, VinerySoundEvents.WINE_RACK_5_CLOSE));
    public static final Block BARREL = register("barrel", new BarrelBlock(FabricBlockSettings.copyOf(Blocks.BARREL)));
    public static final Block STRIPPED_CHERRY_LOG = registerLog("stripped_cherry_log");
    public static final Block CHERRY_LOG = register("cherry_log", new StrippableLogBlock(() -> STRIPPED_CHERRY_LOG, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_CHERRY_WOOD = registerLog("stripped_cherry_wood");
    public static final Block CHERRY_WOOD = register("cherry_wood", new StrippableLogBlock(() -> STRIPPED_CHERRY_WOOD, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_OLD_CHERRY_LOG = registerLog("stripped_old_cherry_log");
    public static final Block OLD_CHERRY_LOG = register("old_cherry_log", new StrippableLogBlock(() -> STRIPPED_OLD_CHERRY_LOG, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_OLD_CHERRY_WOOD = registerLog("stripped_old_cherry_wood");
    public static final Block OLD_CHERRY_WOOD = register("old_cherry_wood", new StrippableLogBlock(() -> STRIPPED_OLD_CHERRY_WOOD, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block CHERRY_BEAM = registerLog("cherry_beam");
    public static final Block CHERRY_PLANKS = register("cherry_planks", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block CHERRY_FLOORBOARD = register("cherry_floorboard", new Block(FabricBlockSettings.copy(CHERRY_PLANKS)));
    public static final Block CHERRY_STAIRS = register("cherry_stairs", new StairsBlock(CHERRY_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(CHERRY_PLANKS)));
    public static final Block CHERRY_SLAB = register("cherry_slab", new SlabBlock(getSlabSettings()));
    public static final Block CHERRY_FENCE = register("cherry_fence", new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
    public static final Block CHERRY_FENCE_GATE = register("cherry_fence_gate", new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE), SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN));
    public static final Block CHERRY_BUTTON = register("cherry_button", createWoodenButtonBlock());
    public static final Block CHERRY_PRESSURE_PLATE = register("cherry_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE), SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN));
    public static final Block CHERRY_DOOR = register("cherry_door", new DoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_DOOR), SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN));
    public static final Block CHERRY_TRAPDOOR = register("cherry_trapdoor", new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR), SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN));
    private static final Identifier CHERRY_SIGN_TEXTURE = new Identifier("entity/signs/cherry");
    public static final TerraformSignBlock CHERRY_SIGN = register("cherry_sign", new TerraformSignBlock(CHERRY_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)), false);
    public static final Block CHERRY_WALL_SIGN = register("cherry_wall_sign", new TerraformWallSignBlock(CHERRY_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)), false);
    public static final Item CHERRY_SIGN_ITEM = register("cherry_sign", new SignItem(getSettings().maxCount(16), CHERRY_SIGN, CHERRY_WALL_SIGN));
    public static final Block WINDOW = register("window", new WindowBlock(FabricBlockSettings.copy(Blocks.GLASS_PANE)));
    public static final Block GRAPEVINE_LATTICE = register("grapevine_lattice", new LatticeStemBlock(getGrapevineSettings()));
    public static final Block LOAM = register("loam", new Block(FabricBlockSettings.of(Material.SOIL).strength(2.0F, 3.0F).sounds(BlockSoundGroup.MUD)));
    public static final Block LOAM_STAIRS = register("loam_stairs", new StairsBlock(LOAM.getDefaultState(), FabricBlockSettings.of(Material.SOIL).strength(2.0F, 3.0F).sounds(BlockSoundGroup.MUD)));
    public static final Block LOAM_SLAB = register("loam_slab", new SlabBlock(FabricBlockSettings.of(Material.SOIL).strength(2.0F, 3.0F).sounds(BlockSoundGroup.MUD)));
    public static final Block COARSE_DIRT_SLAB = register("coarse_dirt_slab", new VariantSlabBlock(FabricBlockSettings.copy(Blocks.COARSE_DIRT)));
    public static final Block DIRT_SLAB = register("dirt_slab", new VariantSlabBlock(FabricBlockSettings.copy(Blocks.DIRT)));
    public static final Block GRASS_SLAB = register("grass_slab", new SnowyVariantSlabBlock(FabricBlockSettings.copy(Blocks.GRASS_BLOCK)));
    public static final Block WINE_BOTTLE = register("wine_bottle", new EmptyWineBottleBlock(AbstractBlock.Settings.copy(Blocks.GLASS).breakInstantly().nonOpaque()));
    public static final JuiceItem RED_GRAPEJUICE_WINE_BOTTLE = register("red_grapejuice_wine_bottle", new JuiceItem(getSettings()));
    public static final JuiceItem WHITE_GRAPEJUICE_WINE_BOTTLE = register("white_grapejuice_wine_bottle", new JuiceItem(getSettings()));
    public static final JuiceItem SAVANNA_RED_GRAPEJUICE_BOTTLE = register("savanna_red_grapejuice_bottle",  new JuiceItem(getSettings()));
    public static final JuiceItem SAVANNA_WHITE_GRAPEJUICE_BOTTLE = register("savanna_white_grapejuice_bottle",  new JuiceItem(getSettings()));
    public static final JuiceItem TAIGA_RED_GRAPEJUICE_BOTTLE = register("taiga_red_grapejuice_bottle",  new JuiceItem(getSettings()));
    public static final JuiceItem TAIGA_WHITE_GRAPEJUICE_BOTTLE = register("taiga_white_grapejuice_bottle", new JuiceItem(getSettings()));
    public static final JuiceItem JUNGLE_RED_GRAPEJUICE_BOTTLE = register("jungle_red_grapejuice_bottle",  new JuiceItem(getSettings()));
    public static final JuiceItem JUNGLE_WHITE_GRAPEJUICE_BOTTLE = register("jungle_white_grapejuice_bottle",  new JuiceItem(getSettings()));
    public static final JuiceItem APPLE_JUICE = register("apple_juice",  new JuiceItem(getSettings()));
    //normal - light effects, small util
    public static final Block CHORUS_WINE = registerBigWine("chorus_wine", new WineBottleBlock(getWineSettings(), 1), VineryEffects.TELEPORT, 1);
    public static final Block CHERRY_WINE = registerWine("cherry_wine", new WineBottleBlock(getWineSettings(), 3), StatusEffects.REGENERATION);
    public static final Block MAGNETIC_WINE = registerBigWine("magnetic_wine", new WineBottleBlock(getWineSettings(), 1), VineryEffects.MAGNET);
    public static final Block NOIR_WINE = registerWine("noir_wine", new WineBottleBlock(getWineSettings(), 3), StatusEffects.INSTANT_HEALTH, 1);
    //jungle - absorption and heal
    public static final Block KING_DANIS_WINE = registerBigWine("king_danis_wine", new WineBottleBlock(getWineSettings(), 1), VineryEffects.IMPROVED_INSTANT_HEALTH);
    public static final Block MELLOHI_WINE = registerBigWine("mellohi_wine", new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_FIRE_RESISTANCE);
    public static final Block STAL_WINE = registerWine("stal_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_REGENERATION);
    public static final Block STRAD_WINE = registerBigWine("strad_wine", new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_ABSORBTION);
    //taiga wine - util, strength
    public static final Block SOLARIS_WINE = registerWine("solaris_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_STRENGTH);
    public static final Block BOLVAR_WINE = registerWine("bolvar_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_HASTE);
    //savanna wine - util, speed / water breathing
    public static final Block AEGIS_WINE = registerBigWine("aegis_wine", new WineBottleBlock(getWineSettings(), 3), StatusEffects.NIGHT_VISION);
    public static final Block CLARK_WINE = registerWine("clark_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_JUMP_BOOST);
    public static final Block CHENET_WINE = registerBigWine("chenet_wine", new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_SPEED);
    public static final Block KELP_CIDER = registerWine("kelp_cider", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_WATER_BREATHING);
    //apple wine - heal
    public static final Block APPLE_WINE = registerBigWine("apple_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_INSTANT_HEALTH, 1);
    public static final Block APPLE_CIDER = registerBigWine("apple_cider", new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_REGENERATION);
    //rare wine
    public static final Block JELLIE_WINE = registerBigWine("jellie_wine", new WineBottleBlock(getWineSettings(), 1), VineryEffects.JELLIE);


    public static final Block CHERRY_JAR = register("cherry_jar", new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque().sounds(BlockSoundGroup.GLASS)));
    public static final Block CHERRY_JAM = register("cherry_jam", new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque().sounds(BlockSoundGroup.GLASS)));
    public static final Block APPLE_JAM = register("apple_jam", new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque().sounds(BlockSoundGroup.GLASS)));
    public static final Block SWEETBERRY_JAM = register("sweetberry_jam", new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque().sounds(BlockSoundGroup.GLASS)));
    public static final Block GRAPE_JAM = register("grape_jam", new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque().sounds(BlockSoundGroup.GLASS)));
    public static final Block PALE_STEM_BLOCK = register("grapevine_stem", new PaleStemBlock(getGrapevineSettings()));
    public static final Block STORAGE_POT = register("storage_pot", new StoragePotBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), SoundEvents.ITEM_DYE_USE, SoundEvents.ITEM_DYE_USE));
    public static final Block WINE_BOX = register("wine_box", new WineBox(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).nonOpaque()));
    public static final Block BIG_TABLE = register("big_table", new BigTableBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 2.0F)));
    public static final Block SHELF = register("shelf", new ShelfBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block FLOWER_BOX = register("flower_box", new FlowerBoxBlock(FabricBlockSettings.copy(Blocks.FLOWER_POT)));
    public static final Block FLOWER_POT = register("flower_pot", new FlowerPotBlock(FabricBlockSettings.copy(Blocks.FLOWER_POT)));
    public static final Block BASKET = register("basket", new BasketBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque(), 1));
    public static final Block COOKING_POT = register("cooking_pot", new CookingPotBlock(FabricBlockSettings.of(Material.METAL).nonOpaque()));
    public static final Block STACKABLE_LOG = register("stackable_log", new StackableLogBlock(getLogBlockSettings().nonOpaque().luminance(state -> state.get(StackableLogBlock.FIRED) ? 13 : 0)));
    public static final Item FAUCET = register("faucet", new FaucetItem(getSettings()));
    public static final Item STRAW_HAT = register("straw_hat", new StrawHatItem(getSettings().rarity(Rarity.COMMON)));
    public static final Item VINEMAKER_APRON = register("vinemaker_apron", new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.CHEST, getSettings().rarity(Rarity.EPIC)));
    public static final Item VINEMAKER_LEGGINGS = register("vinemaker_leggings", new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.LEGS, getSettings().rarity(Rarity.RARE)));
    public static final Item VINEMAKER_BOOTS = register("vinemaker_boots", new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.FEET, getSettings().rarity(Rarity.EPIC)));
    public static final Item GLOVES = register("gloves", new GlovesItem(getSettings().rarity(Rarity.RARE)));
    public static final Item DOUGH = register("dough", new CherryItem(getSettings()));
    public static final Item CHOCOLATE_BREAD = register("chocolate_bread", new ChocolateBreadItem(getSettings().food(FoodComponents.BREAD)));
    public static final Item TOAST = register("toast", new ToastItem(getSettings().food(FoodComponents.BEETROOT_SOUP)));
    public static final Item DONUT = register("donut", new DoughnutItem(getSettings().food(FoodComponents.CARROT)));
    public static final Item MILK_BREAD = register("milk_bread", new MilkBreadItem(getSettings().food(FoodComponents.COOKIE)));
    public static final Block CRUSTY_BREAD = register("crusty_bread", new BreadBlock(AbstractBlock.Settings.copy(Blocks.CAKE).nonOpaque()));
    public static final Item BREAD_SLICE = register("bread_slice", new Item(getSettings().food(FoodComponents.BAKED_POTATO)));
    public static final Item APPLE_CUPCAKE = register("apple_cupcake", new CupcakeItem(getSettings().food(FoodComponents.GOLDEN_CARROT)));
    public static final Item APPLE_PIE_SLICE = register("apple_pie_slice", new Item(getSettings().food(FoodComponents.GOLDEN_CARROT)));
    public static final Block APPLE_PIE = register("apple_pie", new PieBlock((FabricBlockSettings.copyOf(Blocks.CAKE)), ObjectRegistry.APPLE_PIE_SLICE));
    public static final Item APPLE_MASH = register("apple_mash", new CherryItem(getSettings().food(FoodComponents.APPLE)));
    public static final Block TOMATO_CROP = register("tomato_crop", new TomatoCropBlock(getBushSettings()), false);
    public static final Item TOMATO_SEEDS = register("tomato_seeds", new GrapeBushSeedItem(TOMATO_CROP, getSettings(), GrapevineType.TOMATO));
    public static final Item APPLESAUCE = register("applesauce", new AppleSauceItem(getSettings().food(FoodComponents.BAKED_POTATO)));
    public static final Item MULE_SPAWN_EGG = register("mule_spawn_egg", new SpawnEggItem(VineryEntites.MULE, 0x8b7867, 0x5a4e43, getSettings()));
    public static final Item WANDERING_WINEMAKER_SPAWN_EGG = register("wandering_winemaker_spawn_egg", new SpawnEggItem(VineryEntites.WANDERING_WINEMAKER, 0xb78272, 0x3c4a73, getSettings()));

    public static final Item TOMATO = register("tomato", new JuiceItem(getSettings().food(FoodComponents.APPLE)));



    private static PillarBlock registerLog(String path) {
        return register(path, new PillarBlock(getLogBlockSettings()));
    }


    private static <T extends Block> T register(String path, T block) {
        return register(path, block, true);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem) {
        return register(path, block, registerItem, settings -> {
        });
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem, Consumer<Item.Settings> consumer) {
        return register(path, block, registerItem, BlockItem::new, consumer);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem, BiFunction<T, Item.Settings, ? extends BlockItem> function, Consumer<Item.Settings> consumer) {
        final Identifier id = new VineryIdentifier(path);
        BLOCKS.put(id, block);
        if (registerItem) {
            ITEMS.put(id, function.apply(block, getSettings(consumer)));
        }
        return block;
    }

    private static <T extends Block> T registerWine(String path, T block, StatusEffect effect) {
        return registerWine(path, block, effect, 45 * 20);
    }

    private static <T extends Block> T registerWine(String path, T block, StatusEffect effect, int duration) {
        return register(path, block, true, DrinkBlockSmallItem::new, settings -> settings.food(wineFoodComponent(effect, duration)));
    }

    private static <T extends Block> T registerBigWine(String path, T block, StatusEffect effect) {
        return registerBigWine(path, block, effect, 45 * 20);
    }

    private static <T extends Block> T registerBigWine(String path, T block, StatusEffect effect, int duration) {
        return register(path, block, true, DrinkBlockBigItem::new, settings -> settings.food(wineFoodComponent(effect, duration)));
    }


    private static FoodComponent wineFoodComponent(StatusEffect effect, int duration) {
        List<Pair<StatusEffectInstance, Float>> statusEffects = new ArrayList<>();
        statusEffects.add(new Pair<>(new StatusEffectInstance(effect, duration), 1.0f));
        return new VineryFoodComponent(statusEffects);
    }

    private static <T extends Item> T register(String path, T item) {
        final Identifier id = new VineryIdentifier(path);
        ITEMS.put(id, item);
        return item;
    }

    public static void init() {
        for (Map.Entry<Identifier, Block> entry : BLOCKS.entrySet()) {
            Registry.register(Registries.BLOCK, entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Identifier, Item> entry : ITEMS.entrySet()) {
            Registry.register(Registries.ITEM, entry.getKey(), entry.getValue());
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
        Item.Settings settings = new Item.Settings();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Settings getSettings() {
        return getSettings(settings -> {
        });
    }

    private static Block.Settings getBushSettings() {
        return FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH);
    }


    private static AbstractBlock.Settings getGrassSettings() {
        return FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).nonOpaque();
    }

    private static AbstractBlock.Settings getGrapevineSettings() {
        return FabricBlockSettings.of(Material.WOOD).strength(2.0F).ticksRandomly().sounds(BlockSoundGroup.WOOD).nonOpaque();
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

    public static List<ItemConvertible> getItemConvertibles() {
        List<ItemConvertible> list = new ArrayList<>();
        for (Block entry : BLOCKS.values()) {
            if (entry.asItem() != null) {
                list.add(entry);
            }
        }
        list.addAll(ITEMS.values());
        return list;
    }

    public static Map<Identifier, Block> getBlocks() {
        return Collections.unmodifiableMap(BLOCKS);
    }

    public static Map<Identifier, Item> getItems() {
        return Collections.unmodifiableMap(ITEMS);
    }

    private static ButtonBlock createWoodenButtonBlock() {
        return new ButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5f).sounds(BlockSoundGroup.WOOD), 30, true, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON);
    }

}
