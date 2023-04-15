package satisfyu.vinery.registry;

import com.mojang.datafixers.util.Pair;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import satisfyu.vinery.VineryIdentifier;
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
import satisfyu.vinery.world.VineryConfiguredFeatures;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static satisfyu.vinery.Vinery.REGISTRIES;

public class ObjectRegistry {

    public static Registrar<Item> ITEMS = REGISTRIES.get(Registries.ITEM);
    public static Registrar<Block> BLOCKS = REGISTRIES.get(Registries.BLOCK);


    public static final Item CHERRY = register("cherry", new CherryItem(getSettings().food(Foods.COOKIE)));
    public static final Item ROTTEN_CHERRY = register("rotten_cherry", new RottenCherryItem(getSettings().food(Foods.POISONOUS_POTATO)));
    public static final Block RED_GRAPE_BUSH = register("red_grape_bush", new GrapeBush(getBushSettings(), GrapevineType.RED), false);
    public static final Item RED_GRAPE_SEEDS = register("red_grape_seeds", new GrapeBushSeedItem(RED_GRAPE_BUSH, getSettings(), GrapevineType.RED));
    public static final Item RED_GRAPE = register("red_grape", new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.RED, ObjectRegistry.RED_GRAPE_SEEDS.getDefaultInstance()));
    public static final Block WHITE_GRAPE_BUSH = register("white_grape_bush", new GrapeBush(getBushSettings(), GrapevineType.WHITE), false);
    public static final Item WHITE_GRAPE_SEEDS = register("white_grape_seeds", new GrapeBushSeedItem(WHITE_GRAPE_BUSH, getSettings(), GrapevineType.WHITE));
    public static final Item WHITE_GRAPE = register("white_grape", new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.WHITE, ObjectRegistry.WHITE_GRAPE_SEEDS.getDefaultInstance()));

    public static final Block SAVANNA_RED_GRAPE_BUSH = register("savanna_grape_bush_red", new SavannaGrapeBush(getBushSettings(), GrapevineType.SAVANNA_RED), false);
    public static final Item SAVANNA_RED_GRAPE_SEEDS = register("savanna_grape_seeds_red", new GrapeBushSeedItem(SAVANNA_RED_GRAPE_BUSH, getSettings(), GrapevineType.SAVANNA_RED));
    public static final Item SAVANNA_RED_GRAPE = register("savanna_grapes_red", new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.SAVANNA_RED, ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS.getDefaultInstance()));
    public static final Block SAVANNA_WHITE_GRAPE_BUSH = register("savanna_grape_bush_white", new SavannaGrapeBush(getBushSettings(), GrapevineType.SAVANNA_WHITE), false);
    public static final Item SAVANNA_WHITE_GRAPE_SEEDS = register("savanna_grape_seeds_white", new GrapeBushSeedItem(SAVANNA_WHITE_GRAPE_BUSH, getSettings(), GrapevineType.SAVANNA_WHITE));
    public static final Item SAVANNA_WHITE_GRAPE = register("savanna_grapes_white", new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.SAVANNA_WHITE, ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS.getDefaultInstance()));

    public static final Block TAIGA_RED_GRAPE_BUSH = register("taiga_grape_bush_red", new TaigaGrapeBush(getBushSettings(), GrapevineType.TAIGA_RED), false);
    public static final Item TAIGA_RED_GRAPE_SEEDS = register("taiga_grape_seeds_red", new GrapeBushSeedItem(TAIGA_RED_GRAPE_BUSH, getSettings(), GrapevineType.TAIGA_RED));
    public static final Item TAIGA_RED_GRAPE = register("taiga_grapes_red", new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.TAIGA_RED, ObjectRegistry.TAIGA_RED_GRAPE_SEEDS.getDefaultInstance()));
    public static final Block TAIGA_WHITE_GRAPE_BUSH = register("taiga_grape_bush_white", new TaigaGrapeBush(getBushSettings(), GrapevineType.TAIGA_WHITE), false);
    public static final Item TAIGA_WHITE_GRAPE_SEEDS = register("taiga_grape_seeds_white", new GrapeBushSeedItem(TAIGA_WHITE_GRAPE_BUSH, getSettings(), GrapevineType.TAIGA_WHITE));
    public static final Item TAIGA_WHITE_GRAPE = register("taiga_grapes_white", new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.TAIGA_WHITE,ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS.getDefaultInstance()));

    public static final Block JUNGLE_RED_GRAPE_BUSH = register("jungle_grape_bush_red", new GrapeVineBlock(getBushSettings(), GrapevineType.JUNGLE_RED), false);
    public static final Item JUNGLE_RED_GRAPE_SEEDS = register("jungle_grape_seeds_red", new GrapeBushSeedItem(JUNGLE_RED_GRAPE_BUSH, getSettings(), GrapevineType.JUNGLE_RED));
    public static final Item JUNGLE_RED_GRAPE = register("jungle_grapes_red", new GrapeItem(getSettings().food(Foods.BAKED_POTATO), GrapevineType.JUNGLE_RED, ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS.getDefaultInstance()));
    public static final Block JUNGLE_WHITE_GRAPE_BUSH = register("jungle_grape_bush_white", new GrapeVineBlock(getBushSettings(), GrapevineType.JUNGLE_WHITE), false);
    public static final Item JUNGLE_WHITE_GRAPE_SEEDS = register("jungle_grape_seeds_white", new GrapeBushSeedItem(JUNGLE_WHITE_GRAPE_BUSH, getSettings(), GrapevineType.JUNGLE_WHITE));
    public static final Item JUNGLE_WHITE_GRAPE = register("jungle_grapes_white", new GrapeItem(getSettings().food(Foods.BAKED_POTATO), GrapevineType.JUNGLE_WHITE, ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS.getDefaultInstance()));

    public static final Block CHERRY_SAPLING = register("cherry_sapling", new SaplingBlock(new AbstractTreeGrower() {
        @Nullable
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(net.minecraft.util.RandomSource random, boolean bees) {
            if (random.nextBoolean()) return VineryConfiguredFeatures.CHERRY_KEY;
            return VineryConfiguredFeatures.CHERRY_VARIANT_KEY;
        }
    }, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final Block OLD_CHERRY_SAPLING = register("old_cherry_sapling", new SaplingBlock(new AbstractTreeGrower() {
        @Nullable
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(net.minecraft.util.RandomSource random, boolean bees) {
            if (random.nextBoolean()) {
                if (bees) return VineryConfiguredFeatures.OLD_CHERRY_BEE_KEY;
                return VineryConfiguredFeatures.OLD_CHERRY_KEY;
            } else {
                if (bees) return VineryConfiguredFeatures.OLD_CHERRY_VARIANT_WITH_BEE_KEY;
                return VineryConfiguredFeatures.OLD_CHERRY_VARIANT_KEY;
            }
        }
    }, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), true);


    public static final Block GRAPEVINE_LEAVES = register("grapevine_leaves", new GrapevineLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final Block CHERRY_LEAVES = register("cherry_leaves", new CherryLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final Block WHITE_GRAPE_CRATE = register("white_grape_crate", new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Block RED_GRAPE_CRATE = register("red_grape_crate", new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Block CHERRY_CRATE = register("cherry_crate", new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Block APPLE_CRATE = register("apple_crate", new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Block GRAPEVINE_POT = register("grapevine_pot", new GrapevinePotBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final Block FERMENTATION_BARREL = register("fermentation_barrel", new FermentationBarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final Block WINE_PRESS = register("wine_press", new WinePressBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final Block CHAIR = register("chair", new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block TABLE = register("table", new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final Block WOOD_FIRED_OVEN = register("wood_fired_oven", new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));
    public static final Block STOVE = register("stove", new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).luminance(12)));
    public static final Block KITCHEN_SINK = register("kitchen_sink", new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final Block WINE_RACK_1 = register("wine_rack_1", new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final Block WINE_RACK_2 = register("wine_rack_2", new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final Block WINE_RACK_3 = register("wine_rack_3", new WineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), VinerySoundEvents.WINE_RACK_3_OPEN, VinerySoundEvents.WINE_RACK_3_CLOSE));
    public static final Block WINE_RACK_5 = register("wine_rack_5", new WineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), VinerySoundEvents.WINE_RACK_5_OPEN, VinerySoundEvents.WINE_RACK_5_CLOSE));
    public static final Block BARREL = register("barrel", new BarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final Block STRIPPED_CHERRY_LOG = registerLog("stripped_cherry_log");
    public static final Block CHERRY_LOG = register("cherry_log", new StrippableLogBlock(() -> STRIPPED_CHERRY_LOG, MaterialColor.WOOD, getLogBlockSettings()));
    public static final Block STRIPPED_CHERRY_WOOD = registerLog("stripped_cherry_wood");
    public static final Block CHERRY_WOOD = register("cherry_wood", new StrippableLogBlock(() -> STRIPPED_CHERRY_WOOD, MaterialColor.WOOD, getLogBlockSettings()));
    public static final Block STRIPPED_OLD_CHERRY_LOG = registerLog("stripped_old_cherry_log");
    public static final Block OLD_CHERRY_LOG = register("old_cherry_log", new StrippableLogBlock(() -> STRIPPED_OLD_CHERRY_LOG, MaterialColor.WOOD, getLogBlockSettings()));
    public static final Block STRIPPED_OLD_CHERRY_WOOD = registerLog("stripped_old_cherry_wood");
    public static final Block OLD_CHERRY_WOOD = register("old_cherry_wood", new StrippableLogBlock(() -> STRIPPED_OLD_CHERRY_WOOD, MaterialColor.WOOD, getLogBlockSettings()));
    public static final Block CHERRY_BEAM = registerLog("cherry_beam");
    public static final Block CHERRY_PLANKS = register("cherry_planks", new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Block CHERRY_FLOORBOARD = register("cherry_floorboard", new Block(BlockBehaviour.Properties.copy(CHERRY_PLANKS)));
    public static final Block CHERRY_STAIRS = register("cherry_stairs", new StairBlock(CHERRY_PLANKS.defaultBlockState(), BlockBehaviour.Properties.copy(CHERRY_PLANKS)));
    public static final Block CHERRY_SLAB = register("cherry_slab", new SlabBlock(getSlabSettings()));
    public static final Block CHERRY_FENCE = register("cherry_fence", new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final Block CHERRY_FENCE_GATE = register("cherry_fence_gate", new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE), WoodType.CHERRY));
    public static final Block CHERRY_BUTTON = register("cherry_button", createWoodenButtonBlock());
    public static final Block CHERRY_PRESSURE_PLATE = register("cherry_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.CHERRY));
    public static final Block CHERRY_DOOR = register("cherry_door", new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.CHERRY));
    public static final Block CHERRY_TRAPDOOR = register("cherry_trapdoor", new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.CHERRY));
    private static final ResourceLocation CHERRY_SIGN_TEXTURE = new ResourceLocation("entity/signs/cherry");
    public static final TerraformSignBlock CHERRY_SIGN = register("cherry_sign", new TerraformSignBlock(CHERRY_SIGN_TEXTURE, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN)), false);
    public static final Block CHERRY_WALL_SIGN = register("cherry_wall_sign", new TerraformWallSignBlock(CHERRY_SIGN_TEXTURE, BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN)), false);
    public static final Item CHERRY_SIGN_ITEM = register("cherry_sign", new SignItem(getSettings().stacksTo(16), CHERRY_SIGN, CHERRY_WALL_SIGN));
    public static final Block WINDOW = register("window", new WindowBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE)));
    public static final Block GRAPEVINE_LATTICE = register("grapevine_lattice", new LatticeStemBlock(getGrapevineSettings()));
    public static final Block LOAM = register("loam", new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final Block LOAM_STAIRS = register("loam_stairs", new StairBlock(LOAM.defaultBlockState(), BlockBehaviour.Properties.of(Material.DIRT).strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final Block LOAM_SLAB = register("loam_slab", new SlabBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final Block COARSE_DIRT_SLAB = register("coarse_dirt_slab", new VariantSlabBlock(BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT)));
    public static final Block DIRT_SLAB = register("dirt_slab", new VariantSlabBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final Block GRASS_SLAB = register("grass_slab", new SnowyVariantSlabBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final Block WINE_BOTTLE = register("wine_bottle", new EmptyWineBottleBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).instabreak().noOcclusion()));
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
    public static final Block CHERRY_WINE = registerWine("cherry_wine", new WineBottleBlock(getWineSettings(), 3), MobEffects.REGENERATION);
    public static final Block MAGNETIC_WINE = registerBigWine("magnetic_wine", new WineBottleBlock(getWineSettings(), 1), VineryEffects.MAGNET);
    public static final Block NOIR_WINE = registerWine("noir_wine", new WineBottleBlock(getWineSettings(), 3), MobEffects.HEAL, 1);
    //jungle - absorption and heal
    public static final Block KING_DANIS_WINE = registerBigWine("king_danis_wine", new WineBottleBlock(getWineSettings(), 1), VineryEffects.IMPROVED_INSTANT_HEALTH);
    public static final Block MELLOHI_WINE = registerBigWine("mellohi_wine", new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_FIRE_RESISTANCE);
    public static final Block STAL_WINE = registerWine("stal_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_REGENERATION);
    public static final Block STRAD_WINE = registerBigWine("strad_wine", new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_ABSORBTION);
    //taiga wine - util, strength
    public static final Block SOLARIS_WINE = registerWine("solaris_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_STRENGTH);
    public static final Block BOLVAR_WINE = registerWine("bolvar_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_HASTE);
    //savanna wine - util, speed / water breathing
    public static final Block AEGIS_WINE = registerBigWine("aegis_wine", new WineBottleBlock(getWineSettings(), 3), MobEffects.NIGHT_VISION);
    public static final Block CLARK_WINE = registerWine("clark_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_JUMP_BOOST);
    public static final Block CHENET_WINE = registerBigWine("chenet_wine", new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_SPEED);
    public static final Block KELP_CIDER = registerWine("kelp_cider", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_WATER_BREATHING);
    //apple wine - heal
    public static final Block APPLE_WINE = registerBigWine("apple_wine", new WineBottleBlock(getWineSettings(), 3), VineryEffects.IMPROVED_INSTANT_HEALTH, 1);
    public static final Block APPLE_CIDER = registerBigWine("apple_cider", new WineBottleBlock(getWineSettings(), 2), VineryEffects.IMPROVED_REGENERATION);
    //rare wine
    public static final Block JELLIE_WINE = registerBigWine("jellie_wine", new WineBottleBlock(getWineSettings(), 1), VineryEffects.JELLIE);


    public static final Block CHERRY_JAR = register("cherry_jar", new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS)));
    public static final Block CHERRY_JAM = register("cherry_jam", new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS)));
    public static final Block APPLE_JAM = register("apple_jam", new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS)));
    public static final Block SWEETBERRY_JAM = register("sweetberry_jam", new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS)));
    public static final Block GRAPE_JAM = register("grape_jam", new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion().sound(SoundType.GLASS)));
    public static final Block PALE_STEM_BLOCK = register("grapevine_stem", new PaleStemBlock(getGrapevineSettings()));
    public static final Block STORAGE_POT = register("storage_pot", new StoragePotBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.DYE_USE, SoundEvents.DYE_USE));
    public static final Block WINE_BOX = register("wine_box", new WineBox(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).noOcclusion()));
    public static final Block BIG_TABLE = register("big_table", new BigTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 2.0F)));
    public static final Block SHELF = register("shelf", new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final Block FLOWER_BOX = register("flower_box", new FlowerBoxBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final Block FLOWER_POT = register("flower_pot", new FlowerPotBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final Block BASKET = register("basket", new BasketBlock(BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion(), 1));
    public static final Block COOKING_POT = register("cooking_pot", new CookingPotBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()));
    public static final Block STACKABLE_LOG = register("stackable_log", new StackableLogBlock(getLogBlockSettings().noOcclusion().lightLevel(state -> state.getValue(StackableLogBlock.FIRED) ? 13 : 0)));
    public static final Item FAUCET = register("faucet", new FaucetItem(getSettings()));
    public static final Item STRAW_HAT = register("straw_hat", new StrawHatItem(getSettings().rarity(Rarity.COMMON)));
    public static final Item VINEMAKER_APRON = register("vinemaker_apron", new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.EPIC)));
    public static final Item VINEMAKER_LEGGINGS = register("vinemaker_leggings", new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, ArmorItem.Type.LEGGINGS, getSettings().rarity(Rarity.RARE)));
    public static final Item VINEMAKER_BOOTS = register("vinemaker_boots", new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, ArmorItem.Type.BOOTS, getSettings().rarity(Rarity.EPIC)));
    public static final Item GLOVES = register("gloves", new GlovesItem(getSettings().rarity(Rarity.RARE)));
    public static final Item DOUGH = register("dough", new CherryItem(getSettings()));
    public static final Item CHOCOLATE_BREAD = register("chocolate_bread", new ChocolateBreadItem(getSettings().food(Foods.BREAD)));
    public static final Item TOAST = register("toast", new ToastItem(getSettings().food(Foods.BEETROOT_SOUP)));
    public static final Item DONUT = register("donut", new DoughnutItem(getSettings().food(Foods.CARROT)));
    public static final Item MILK_BREAD = register("milk_bread", new MilkBreadItem(getSettings().food(Foods.COOKIE)));
    public static final Block CRUSTY_BREAD = register("crusty_bread", new BreadBlock(BlockBehaviour.Properties.copy(Blocks.CAKE).noOcclusion()));
    public static final Item BREAD_SLICE = register("bread_slice", new Item(getSettings().food(Foods.BAKED_POTATO)));
    public static final Item APPLE_CUPCAKE = register("apple_cupcake", new CupcakeItem(getSettings().food(Foods.GOLDEN_CARROT)));
    public static final Item APPLE_PIE_SLICE = register("apple_pie_slice", new Item(getSettings().food(Foods.GOLDEN_CARROT)));
    public static final Block APPLE_PIE = register("apple_pie", new PieBlock((BlockBehaviour.Properties.copy(Blocks.CAKE)), ObjectRegistry.APPLE_PIE_SLICE));
    public static final Item APPLE_MASH = register("apple_mash", new CherryItem(getSettings().food(Foods.APPLE)));
    public static final Block TOMATO_CROP = register("tomato_crop", new TomatoCropBlock(getBushSettings()), false);
    public static final Item TOMATO_SEEDS = register("tomato_seeds", new GrapeBushSeedItem(TOMATO_CROP, getSettings(), GrapevineType.TOMATO));
    public static final Item APPLESAUCE = register("applesauce", new AppleSauceItem(getSettings().food(Foods.BAKED_POTATO)));
    public static final Item MULE_SPAWN_EGG = register("mule_spawn_egg", new SpawnEggItem(VineryEntites.MULE, 0x8b7867, 0x5a4e43, getSettings()));
    public static final Item WANDERING_WINEMAKER_SPAWN_EGG = register("wandering_winemaker_spawn_egg", new SpawnEggItem(VineryEntites.WANDERING_WINEMAKER, 0xb78272, 0x3c4a73, getSettings()));

    public static final Item TOMATO = register("tomato", new JuiceItem(getSettings().food(Foods.APPLE)));




    private static <T extends Item> RegistrySupplier<T> registerI(String path, Supplier<T> item) {
        final ResourceLocation id = new VineryIdentifier(path);
        return ITEMS.register(id, item);
    }

    private static <T extends Block> RegistrySupplier<T> registerB(String path, Supplier<T> block) {
        final ResourceLocation id = new VineryIdentifier(path);
        return BLOCKS.register(id, block);
    }

    /*

    private static <T extends Block> T registerWine(String path, T block, MobEffect effect) {
        return registerWine(path, block, effect, 45 * 20);
    }

    private static <T extends Block> T registerWine(String path, T block, MobEffect effect, int duration) {
        return register(path, block, true, DrinkBlockSmallItem::new, settings -> settings.food(wineFoodComponent(effect, duration)));
    }

    private static <T extends Block> T registerBigWine(String path, T block, MobEffect effect) {
        return registerBigWine(path, block, effect, 45 * 20);
    }

    private static <T extends Block> T registerBigWine(String path, T block, MobEffect effect, int duration) {
        return register(path, block, true, DrinkBlockBigItem::new, settings -> settings.food(wineFoodComponent(effect, duration)));
    }


    private static FoodProperties wineFoodComponent(MobEffect effect, int duration) {
        List<Pair<MobEffectInstance, Float>> statusEffects = new ArrayList<>();
        statusEffects.add(new Pair<>(new MobEffectInstance(effect, duration), 1.0f));
        return new VineryFoodComponent(statusEffects);
    }

    public static void init() {
        /*
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

     */



    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }


    private static BlockBehaviour.Properties getGrassSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).noOcclusion();
    }

    private static BlockBehaviour.Properties getGrapevineSettings() {
        return BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).randomTicks().sound(SoundType.WOOD).noOcclusion();
    }

    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties getSlabSettings() {
        return getLogBlockSettings().explosionResistance(3.0F);
    }

    private static BlockBehaviour.Properties getWineSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }


    private static ButtonBlock createWoodenButtonBlock() {
        return new ButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5f).sound(SoundType.WOOD), BlockSetType.CHERRY, 30,true);
    }

}
