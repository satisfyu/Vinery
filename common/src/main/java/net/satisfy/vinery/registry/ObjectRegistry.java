package net.satisfy.vinery.registry;

import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.common.block.*;
import de.cristelknight.doapi.common.block.storage.FlowerPotBigBlock;
import de.cristelknight.doapi.common.registry.DoApiSoundEventRegistry;
import de.cristelknight.doapi.common.util.GeneralUtil;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.block.*;
import net.satisfy.vinery.block.CherryLeavesBlock;
import net.satisfy.vinery.block.grape.GrapeBush;
import net.satisfy.vinery.block.grape.GrapeVineBlock;
import net.satisfy.vinery.block.grape.SavannaGrapeBush;
import net.satisfy.vinery.block.grape.TaigaGrapeBush;
import net.satisfy.vinery.block.stem.LatticeBlock;
import net.satisfy.vinery.block.stem.PaleStemBlock;
import net.satisfy.vinery.block.storage.*;
import net.satisfy.vinery.config.VineryConfig;
import net.satisfy.vinery.item.*;
import net.satisfy.vinery.util.VineryIdentifier;
import net.satisfy.vinery.world.VineryConfiguredFeatures;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    VineryConfig config = VineryConfig.getConfigInstance();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Vinery.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Vinery.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();
    public static final RegistrySupplier<Block> RED_GRAPE_BUSH = registerWithoutItem("red_grape_bush", () -> new GrapeBush(getBushSettings(), GrapeTypeRegistry.RED));
    public static final RegistrySupplier<Item> RED_GRAPE_SEEDS = registerItem("red_grape_seeds", () -> new GrapeBushSeedItem(RED_GRAPE_BUSH.get(), getSettings(), GrapeTypeRegistry.RED));
    public static final RegistrySupplier<Item> RED_GRAPE = registerItem("red_grape", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypeRegistry.RED, RED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> WHITE_GRAPE_BUSH = registerWithoutItem("white_grape_bush", () -> new GrapeBush(getBushSettings(), GrapeTypeRegistry.WHITE));
    public static final RegistrySupplier<Item> WHITE_GRAPE_SEEDS = registerItem("white_grape_seeds", () -> new GrapeBushSeedItem(WHITE_GRAPE_BUSH.get(), getSettings(), GrapeTypeRegistry.WHITE));
    public static final RegistrySupplier<Item> WHITE_GRAPE = registerItem("white_grape", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypeRegistry.WHITE, WHITE_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> SAVANNA_RED_GRAPE_BUSH = registerWithoutItem("savanna_grape_bush_red", () -> new SavannaGrapeBush(getBushSettings(), GrapeTypeRegistry.SAVANNA_RED));
    public static final RegistrySupplier<Item> SAVANNA_RED_GRAPE_SEEDS = registerItem("savanna_grape_seeds_red", () -> new GrapeBushSeedItem(SAVANNA_RED_GRAPE_BUSH.get(), getSettings(), GrapeTypeRegistry.SAVANNA_RED));
    public static final RegistrySupplier<Item> SAVANNA_RED_GRAPE = registerItem("savanna_grapes_red", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypeRegistry.SAVANNA_RED, ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> SAVANNA_WHITE_GRAPE_BUSH = registerWithoutItem("savanna_grape_bush_white", () -> new SavannaGrapeBush(getBushSettings(), GrapeTypeRegistry.SAVANNA_WHITE));
    public static final RegistrySupplier<Item> SAVANNA_WHITE_GRAPE_SEEDS = registerItem("savanna_grape_seeds_white", () -> new GrapeBushSeedItem(SAVANNA_WHITE_GRAPE_BUSH.get(), getSettings(), GrapeTypeRegistry.SAVANNA_WHITE));
    public static final RegistrySupplier<Item> SAVANNA_WHITE_GRAPE = registerItem("savanna_grapes_white", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypeRegistry.SAVANNA_WHITE, ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> TAIGA_RED_GRAPE_BUSH = registerWithoutItem("taiga_grape_bush_red", () -> new TaigaGrapeBush(getBushSettings(), GrapeTypeRegistry.TAIGA_RED));
    public static final RegistrySupplier<Item> TAIGA_RED_GRAPE_SEEDS = registerItem("taiga_grape_seeds_red", () -> new GrapeBushSeedItem(TAIGA_RED_GRAPE_BUSH.get(), getSettings(), GrapeTypeRegistry.TAIGA_RED));
    public static final RegistrySupplier<Item> TAIGA_RED_GRAPE = registerItem("taiga_grapes_red", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypeRegistry.TAIGA_RED, ObjectRegistry.TAIGA_RED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> TAIGA_WHITE_GRAPE_BUSH = registerWithoutItem("taiga_grape_bush_white", () -> new TaigaGrapeBush(getBushSettings(), GrapeTypeRegistry.TAIGA_WHITE));
    public static final RegistrySupplier<Item> TAIGA_WHITE_GRAPE_SEEDS = registerItem("taiga_grape_seeds_white", () -> new GrapeBushSeedItem(TAIGA_WHITE_GRAPE_BUSH.get(), getSettings(), GrapeTypeRegistry.TAIGA_WHITE));
    public static final RegistrySupplier<Item> TAIGA_WHITE_GRAPE = registerItem("taiga_grapes_white", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapeTypeRegistry.TAIGA_WHITE, ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> JUNGLE_RED_GRAPE_BUSH = registerWithoutItem("jungle_grape_bush_red", () -> new GrapeVineBlock(getBushSettings(), GrapeTypeRegistry.JUNGLE_RED));
    public static final RegistrySupplier<Item> JUNGLE_RED_GRAPE_SEEDS = registerItem("jungle_grape_seeds_red", () -> new GrapeBushSeedItem(JUNGLE_RED_GRAPE_BUSH.get(), getSettings(), GrapeTypeRegistry.JUNGLE_RED));
    public static final RegistrySupplier<Item> JUNGLE_RED_GRAPE = registerItem("jungle_grapes_red", () -> new GrapeItem(getSettings().food(Foods.BAKED_POTATO), GrapeTypeRegistry.JUNGLE_RED, ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> JUNGLE_WHITE_GRAPE_BUSH = registerWithoutItem("jungle_grape_bush_white", () -> new GrapeVineBlock(getBushSettings(), GrapeTypeRegistry.JUNGLE_WHITE));
    public static final RegistrySupplier<Item> JUNGLE_WHITE_GRAPE_SEEDS = registerItem("jungle_grape_seeds_white", () -> new GrapeBushSeedItem(JUNGLE_WHITE_GRAPE_BUSH.get(), getSettings(), GrapeTypeRegistry.JUNGLE_WHITE));
    public static final RegistrySupplier<Item> JUNGLE_WHITE_GRAPE = registerItem("jungle_grapes_white", () -> new GrapeItem(getSettings().food(Foods.BAKED_POTATO), GrapeTypeRegistry.JUNGLE_WHITE, ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> DARK_CHERRY_SAPLING = registerWithItem("dark_cherry_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
            if (random.nextBoolean()) return VineryConfiguredFeatures.DARK_CHERRY_KEY;
            return VineryConfiguredFeatures.DARK_CHERRY_VARIANT_KEY;
        }

    }, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> APPLE_TREE_SAPLING = registerWithItem("apple_tree_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
            if (random.nextBoolean()) return VineryConfiguredFeatures.APPLE_KEY;
            return VineryConfiguredFeatures.APPLE_VARIANT_KEY;
        }
    }, BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Item> CHERRY = registerItem("cherry", () -> new Item(getSettings().food(Foods.COOKIE)));
    public static final RegistrySupplier<Item> ROTTEN_CHERRY = registerItem("rotten_cherry", () -> new RottenCherryItem(getSettings().food(Foods.POISONOUS_POTATO)));
    public static final RegistrySupplier<Block> GRAPEVINE_LEAVES = registerWithItem("grapevine_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> DARK_CHERRY_LEAVES = registerWithItem("dark_cherry_leaves", () -> new CherryLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> APPLE_LEAVES = registerWithItem("apple_leaves", () -> new AppleLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> WHITE_GRAPE_BAG = registerWithItem("white_grape_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> RED_GRAPE_BAG = registerWithItem("red_grape_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> CHERRY_BAG = registerWithItem("cherry_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> APPLE_BAG = registerWithItem("apple_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> GRAPEVINE_POT = registerWithItem("grapevine_pot", () -> new GrapevinePotBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistrySupplier<Block> FERMENTATION_BARREL = registerWithItem("fermentation_barrel", () -> new FermentationBarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistrySupplier<Block> APPLE_PRESS = registerWithItem("apple_press", () -> new ApplePressBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_CHERRY_CHAIR = registerWithItem("dark_cherry_chair", () -> new ChairBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DARK_CHERRY_TABLE = registerWithItem("dark_cherry_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> DARK_CHERRY_CABINET = registerWithItem("dark_cherry_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), DoApiSoundEventRegistry.CABINET_OPEN, DoApiSoundEventRegistry.CABINET_CLOSE));
    public static final RegistrySupplier<Block> DARK_CHERRY_DRAWER = registerWithItem("dark_cherry_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), DoApiSoundEventRegistry.DRAWER_OPEN, DoApiSoundEventRegistry.DRAWER_CLOSE));
    public static final RegistrySupplier<Block> DARK_CHERRY_WINE_RACK_BIG = registerWithItem("dark_cherry_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_CHERRY_WINE_RACK_SMALL = registerWithItem("dark_cherry_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_CHERRY_WINE_RACK_MID = registerWithItem("dark_cherry_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_CHERRY_BARREL = registerWithItem("dark_cherry_barrel", () -> new BarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistrySupplier<Block> APPLE_LOG = registerWithItem("apple_log", GeneralUtil::logBlock);
    public static final RegistrySupplier<Block> APPLE_WOOD = registerWithItem("apple_wood", GeneralUtil::logBlock);
    public static final RegistrySupplier<Block> STRIPPED_DARK_CHERRY_LOG = registerWithItem("stripped_dark_cherry_log", GeneralUtil::logBlock);
    public static final RegistrySupplier<Block> DARK_CHERRY_LOG = registerWithItem("dark_cherry_log", GeneralUtil::logBlock);
    public static final RegistrySupplier<Block> STRIPPED_DARK_CHERRY_WOOD = registerWithItem("stripped_dark_cherry_wood", GeneralUtil::logBlock);
    public static final RegistrySupplier<Block> DARK_CHERRY_WOOD = registerWithItem("dark_cherry_wood", GeneralUtil::logBlock);
    public static final RegistrySupplier<Block> DARK_CHERRY_BEAM = registerWithItem("dark_cherry_beam", GeneralUtil::logBlock);
    public static final RegistrySupplier<Block> DARK_CHERRY_PLANKS = registerWithItem("dark_cherry_planks", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BIT).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistrySupplier<Block> DARK_CHERRY_FLOORBOARD = registerWithItem("dark_cherry_floorboard", () -> new Block(BlockBehaviour.Properties.copy(DARK_CHERRY_PLANKS.get())));
    public static final RegistrySupplier<Block> DARK_CHERRY_STAIRS = registerWithItem("dark_cherry_stairs", () -> new StairBlock(DARK_CHERRY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(DARK_CHERRY_PLANKS.get())));
    public static final RegistrySupplier<Block> DARK_CHERRY_SLAB = registerWithItem("dark_cherry_slab", () -> new SlabBlock(getSlabSettings()));
    public static final RegistrySupplier<Block> DARK_CHERRY_FENCE = registerWithItem("dark_cherry_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistrySupplier<Block> DARK_CHERRY_FENCE_GATE = registerWithItem("dark_cherry_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE), WoodType.CHERRY));
    public static final RegistrySupplier<Block> DARK_CHERRY_BUTTON = registerWithItem("dark_cherry_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> DARK_CHERRY_PRESSURE_PLATE = registerWithItem("dark_cherry_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.CHERRY));
    public static final RegistrySupplier<Block> DARK_CHERRY_DOOR = registerWithItem("dark_cherry_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.CHERRY));
    public static final RegistrySupplier<Block> DARK_CHERRY_TRAPDOOR = registerWithItem("dark_cherry_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.CHERRY));
    public static final RegistrySupplier<Block> WINDOW = registerWithItem("window", () -> new WindowBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE)));
    public static final RegistrySupplier<Block> LOAM = registerWithItem("loam", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final RegistrySupplier<Block> LOAM_STAIRS = registerWithItem("loam_stairs", () -> new StairBlock(LOAM.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final RegistrySupplier<Block> LOAM_SLAB = registerWithItem("loam_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.MUD)));
    public static final RegistrySupplier<Block> COARSE_DIRT_SLAB = registerWithItem("coarse_dirt_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT)));
    public static final RegistrySupplier<Block> DIRT_SLAB = registerWithItem("dirt_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistrySupplier<Block> GRASS_SLAB = registerWithItem("grass_slab", () -> new SpreadableGrassSlabBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistrySupplier<Item> WINE_BOTTLE = registerItem("wine_bottle", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> APPLE_JUICE = registerItem("apple_juice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Item> RED_GRAPEJUICE = registerItem("red_grapejuice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Item> WHITE_GRAPEJUICE = registerItem("white_grapejuice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Item> RED_SAVANNA_GRAPEJUICE = registerItem("red_savanna_grapejuice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Item> WHITE_SAVANNA_GRAPEJUICE = registerItem("white_savanna_grapejuice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Item> RED_TAIGA_GRAPEJUICE = registerItem("red_taiga_grapejuice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Item> WHITE_TAIGA_GRAPEJUICE = registerItem("white_taiga_grapejuice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Item> RED_JUNGLE_GRAPEJUICE = registerItem("red_jungle_grapejuice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Item> WHITE_JUNGLE_GRAPEJUICE = registerItem("white_jungle_grapejuice", () -> new GrapejuiceBottleItem(getSettings().craftRemainder(ObjectRegistry.WINE_BOTTLE.get().asItem())));
    public static final RegistrySupplier<Block> CHORUS_WINE = registerWithoutItem("chorus_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Item> CHORUS_WINE_ITEM = registerItem("chorus_wine", () -> new DrinkBlockBigItem(CHORUS_WINE.get(), getWineItemSettings(MobEffectRegistry.TELEPORT.get(), 1, 0)));
    public static final RegistrySupplier<Block> CHERRY_WINE = registerWithoutItem("cherry_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> MAGNETIC_WINE = registerWithoutItem("magnetic_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Block> JO_SPECIAL_MIXTURE = registerWithoutItem("jo_special_mixture", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Block> CRISTEL_WINE = registerWithoutItem("cristel_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Block> GLOWING_WINE = registerWithoutItem("glowing_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> CREEPERS_CRUSH = registerWithoutItem("creepers_crush", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item> CREEPERS_CRUSH_ITEM = registerItem("creepers_crush", () -> new DrinkBlockBigItem(CREEPERS_CRUSH.get(), getWineItemSettings(MobEffectRegistry.CREEPER_EFFECT.get(), 1, 2)));
    public static final RegistrySupplier<Block> MEAD = registerWithoutItem("mead", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item> MEAD_ITEM = registerItem("mead", () -> new DrinkBlockBigItem(MEAD.get(), getWineItemSettings(MobEffects.SATURATION, 240, 0)));
    public static final RegistrySupplier<Block> RED_WINE = registerWithoutItem("red_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> JELLIE_WINE = registerWithoutItem("jellie_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Block> STAL_WINE = registerWithoutItem("stal_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> NOIR_WINE = registerWithoutItem("noir_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item> NOIR_WINE_ITEM = registerItem("noir_wine", () -> new DrinkBlockSmallItem(NOIR_WINE.get(), getWineItemSettings(MobEffects.HEAL, 1, 2)));
    public static final RegistrySupplier<Block> BOLVAR_WINE = registerWithoutItem("bolvar_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> SOLARIS_WINE = registerWithoutItem("solaris_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> EISWEIN = registerWithoutItem("eiswein", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Block> CHENET_WINE = registerWithoutItem("chenet_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Block> KELP_CIDER = registerWithoutItem("kelp_cider", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> AEGIS_WINE = registerWithoutItem("aegis_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Block> CLARK_WINE = registerWithoutItem("clark_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> MELLOHI_WINE = registerWithoutItem("mellohi_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Block> STRAD_WINE = registerWithoutItem("strad_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Block> APPLE_CIDER = registerWithoutItem("apple_cider", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Block> APPLE_WINE = registerWithoutItem("apple_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> KNULP_WINE = registerWithoutItem("knulp_wine", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Block> LILITU_WINE = registerWithoutItem("lilitu_wine", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Block> BOTTLE_MOJANG_NOIR = registerWithoutItem("bottle_mojang_noir", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> VILLAGERS_FRIGHT = registerWithoutItem("villagers_fright", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item> APPLE_MASH = registerItem("apple_mash", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> GRAPEVINE_STEM = registerWithItem("grapevine_stem", () -> new PaleStemBlock(getGrapevineSettings()));
    public static final RegistrySupplier<Block> STORAGE_POT = registerWithItem("storage_pot", () -> new StoragePotBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), DoApiSoundEventRegistry.CABINET_OPEN, DoApiSoundEventRegistry.CABINET_CLOSE));
    public static final RegistrySupplier<Block> FLOWER_BOX = registerWithItem("flower_box", () -> new FlowerBoxBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> FLOWER_POT_BIG = registerWithItem("flower_pot_big", () -> new FlowerPotBigBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> WINE_BOX = registerWithItem("wine_box", () -> new WineBoxBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_CHERRY_BIG_TABLE = registerWithItem("dark_cherry_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of().strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> DARK_CHERRY_SHELF = registerWithItem("dark_cherry_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BASKET = registerWithoutItem("basket", () -> new BasketBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BIT).strength(1.5F).sound(SoundType.CANDLE).ignitedByLava().noOcclusion()));
    public static final RegistrySupplier<Item> BASKET_ITEM = registerItem("basket", () -> new BasketItem(BASKET.get(), getSettings()));
    public static final RegistrySupplier<Block> STACKABLE_LOG = registerWithItem("stackable_log", () -> new StackableLogBlock(getLogBlockSettings().noOcclusion().lightLevel(state -> state.getValue(StackableLogBlock.FIRED) ? 13 : 0)));
    public static final RegistrySupplier<Item> STRAW_HAT = registerItem("straw_hat", () -> new WinemakerHatItem(ArmorMaterialRegistry.WINEMAKER_ARMOR, ArmorItem.Type.HELMET, getSettings().rarity(Rarity.EPIC), new VineryIdentifier("textures/models/armor/straw_hat.png")));
    public static final RegistrySupplier<Item> WINEMAKER_APRON = registerItem("winemaker_apron", () -> new WinemakerChestItem(ArmorMaterialRegistry.WINEMAKER_ARMOR, getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> WINEMAKER_LEGGINGS = registerItem("winemaker_leggings", () -> new WinemakerLegsItem(ArmorMaterialRegistry.WINEMAKER_ARMOR, getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> WINEMAKER_BOOTS = registerItem("winemaker_boots", () -> new WinemakerBootsItem(ArmorMaterialRegistry.WINEMAKER_ARMOR, getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Block> CALENDAR = registerWithItem("calendar", () -> new CalendarBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Item> MULE_SPAWN_EGG = registerItem("mule_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.MULE, -1, -1, getSettings()));
    public static final RegistrySupplier<Item> WANDERING_WINEMAKER_SPAWN_EGG = registerItem("wandering_winemaker_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.WANDERING_WINEMAKER, -1, -1, getSettings()));
    public static final RegistrySupplier<Block> POTTED_APPLE_TREE_SAPLING = registerWithoutItem("potted_apple_tree_sapling", () -> new FlowerPotBlock(ObjectRegistry.APPLE_TREE_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));
    public static final RegistrySupplier<Block> POTTED_DARK_CHERRY_TREE_SAPLING = registerWithoutItem("potted_dark_cherry_tree_sapling", () -> new FlowerPotBlock(ObjectRegistry.DARK_CHERRY_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));
    public static final RegistrySupplier<Block> OAK_WINE_RACK_BIG = registerWithItem("oak_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_WINE_RACK_SMALL = registerWithItem("oak_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_WINE_RACK_MID = registerWithItem("oak_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_WINE_RACK_BIG = registerWithItem("birch_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_WINE_RACK_SMALL = registerWithItem("birch_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_WINE_RACK_MID = registerWithItem("birch_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_WINE_RACK_BIG = registerWithItem("spruce_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_WINE_RACK_SMALL = registerWithItem("spruce_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_WINE_RACK_MID = registerWithItem("spruce_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_WINE_RACK_BIG = registerWithItem("dark_oak_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_WINE_RACK_SMALL = registerWithItem("dark_oak_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_WINE_RACK_MID = registerWithItem("dark_oak_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_WINE_RACK_BIG = registerWithItem("jungle_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_WINE_RACK_SMALL = registerWithItem("jungle_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_WINE_RACK_MID = registerWithItem("jungle_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_WINE_RACK_BIG = registerWithItem("acacia_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_WINE_RACK_SMALL = registerWithItem("acacia_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_WINE_RACK_MID = registerWithItem("acacia_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_WINE_RACK_BIG = registerWithItem("mangrove_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_WINE_RACK_SMALL = registerWithItem("mangrove_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_WINE_RACK_MID = registerWithItem("mangrove_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_WINE_RACK_BIG = registerWithItem("bamboo_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_WINE_RACK_SMALL = registerWithItem("bamboo_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_WINE_RACK_MID = registerWithItem("bamboo_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_WINE_RACK_BIG = registerWithItem("cherry_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_WINE_RACK_SMALL = registerWithItem("cherry_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_WINE_RACK_MID = registerWithItem("cherry_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Item> VINERY_STANDARD = registerItem("vinery_standard", () -> new StandardItem(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Block> OAK_LATTICE = registerWithItem("oak_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.OAK_PLANKS.getSoundType(Blocks.OAK_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_LATTICE = registerWithItem("spruce_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.SPRUCE_PLANKS.getSoundType(Blocks.SPRUCE_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_LATTICE = registerWithItem("cherry_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.CHERRY_PLANKS.getSoundType(Blocks.CHERRY_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_LATTICE = registerWithItem("birch_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.BIRCH_PLANKS.getSoundType(Blocks.BIRCH_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_LATTICE = registerWithItem("dark_oak_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.DARK_OAK_PLANKS.getSoundType(Blocks.DARK_OAK_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_LATTICE = registerWithItem("acacia_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.ACACIA_PLANKS.getSoundType(Blocks.ACACIA_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_LATTICE = registerWithItem("bamboo_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.BAMBOO_PLANKS.getSoundType(Blocks.BAMBOO_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_LATTICE = registerWithItem("jungle_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.JUNGLE_PLANKS.getSoundType(Blocks.JUNGLE_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_LATTICE = registerWithItem("mangrove_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.MANGROVE_PLANKS.getSoundType(Blocks.MANGROVE_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> LAMROC_WINE = registerWithoutItem("lamroc_wine", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Block> COUNT_ROLEESTER_SHIRAZ_WINE = registerWithoutItem("count_roleester_shiraz", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Block> WHITE_SAVANNA_GRAPE_BAG = registerWithItem("white_savanna_grape_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> RED_SAVANNA_GRAPE_BAG = registerWithItem("red_savanna_grape_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> WHITE_TAIGA_GRAPE_BAG = registerWithItem("white_taiga_grape_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> RED_TAIGA_GRAPE_BAG = registerWithItem("red_taiga_grape_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> WHITE_JUNGLE_GRAPE_BAG = registerWithItem("white_jungle_grape_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistrySupplier<Block> RED_JUNGLE_GRAPE_BAG = registerWithItem("red_jungle_grape_bag", () -> new FacingBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOL)));

    public static void init() {
        Vinery.LOGGER.debug("Registering Mod Block and Items for " + Vinery.MOD_ID);
        ITEMS.register();
        BLOCKS.register();
    }

    public static void initItemsWithConfig() {
        VineryConfig config = VineryConfig.getConfigInstance();
        registerItem("cherry_wine", () -> new DrinkBlockSmallItem(CHERRY_WINE.get(), getWineItemSettings(MobEffects.REGENERATION, config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("magnetic_wine", () -> new DrinkBlockBigItem(MAGNETIC_WINE.get(), getWineItemSettings(MobEffectRegistry.MAGNET.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("jo_special_mixture", () -> new DrinkBlockBigItem(JO_SPECIAL_MIXTURE.get(), getWineItemSettings(MobEffectRegistry.TRIPPY.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("cristel_wine", () -> new DrinkBlockBigItem(CRISTEL_WINE.get(), getWineItemSettings(MobEffectRegistry.EXPERIENCE_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("glowing_wine", () -> new DrinkBlockBigItem(GLOWING_WINE.get(), getWineItemSettings(MobEffects.GLOWING, config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("red_wine", () -> new DrinkBlockSmallItem(RED_WINE.get(), getWineItemSettings(MobEffectRegistry.STAGGER_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("jellie_wine", () -> new DrinkBlockBigItem(JELLIE_WINE.get(), getWineItemSettings(MobEffectRegistry.JELLIE.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("stal_wine", () -> new DrinkBlockSmallItem(STAL_WINE.get(), getWineItemSettings(MobEffectRegistry.HEALTH_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("bolvar_wine", () -> new DrinkBlockSmallItem(BOLVAR_WINE.get(), getWineItemSettings(MobEffectRegistry.LAVA_WALKER.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("solaris_wine", () -> new DrinkBlockSmallItem(SOLARIS_WINE.get(), getWineItemSettings(MobEffects.DIG_SPEED, config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("eiswein", () -> new DrinkBlockBigItem(EISWEIN.get(), getWineItemSettings(MobEffectRegistry.FROSTY_ARMOR_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("chenet_wine", () -> new DrinkBlockBigItem(CHENET_WINE.get(), getWineItemSettings(MobEffectRegistry.CLIMBING_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("kelp_cider", () -> new DrinkBlockSmallItem(KELP_CIDER.get(), getWineItemSettings(MobEffectRegistry.WATER_WALKER.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("aegis_wine", () -> new DrinkBlockBigItem(AEGIS_WINE.get(), getWineItemSettings(MobEffectRegistry.ARMOR_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("clark_wine", () -> new DrinkBlockSmallItem(CLARK_WINE.get(), getWineItemSettings(MobEffectRegistry.IMPROVED_JUMP_BOOST.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("mellohi_wine", () -> new DrinkBlockBigItem(MELLOHI_WINE.get(), getWineItemSettings(MobEffectRegistry.LUCK_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("strad_wine", () -> new DrinkBlockBigItem(STRAD_WINE.get(), getWineItemSettings(MobEffectRegistry.RESISTANCE_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("apple_cider", () -> new DrinkBlockBigItem(APPLE_CIDER.get(), getWineItemSettings(MobEffects.HEALTH_BOOST, config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("apple_wine", () -> new DrinkBlockBigItem(APPLE_WINE.get(), getWineItemSettings(MobEffects.ABSORPTION, config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("knulp_wine", () -> new DrinkBlockBigItem(KNULP_WINE.get(), getWineItemSettings(MobEffects.LEVITATION, config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("lilitu_wine", () -> new DrinkBlockBigItem(LILITU_WINE.get(), getWineItemSettings(MobEffectRegistry.PARTY_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("bottle_mojang_noir", () -> new DrinkBlockSmallItem(BOTTLE_MOJANG_NOIR.get(), getWineItemSettings(MobEffectRegistry.ARMOR_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("villagers_fright", () -> new DrinkBlockSmallItem(VILLAGERS_FRIGHT.get(), getWineItemSettings(MobEffects.BAD_OMEN, config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("count_roleester_shiraz", () -> new DrinkBlockBigItem(COUNT_ROLEESTER_SHIRAZ_WINE.get(), getWineItemSettings(MobEffectRegistry.SHIRAAZ_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
        registerItem("lamroc_wine", () -> new DrinkBlockSmallItem(LAMROC_WINE.get(), getWineItemSettings(MobEffectRegistry.TRADING_EFFECT.get(), config.wineEffectDuration(), config.wineEffectStrength())));
    }

    public static BlockBehaviour.Properties properties(float strength) {
        return properties(strength, strength);
    }

    public static BlockBehaviour.Properties properties(float breakSpeed, float explosionResist) {
        return BlockBehaviour.Properties.of().strength(breakSpeed, explosionResist);
    }


    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static Item.Properties getWineItemSettings(MobEffect effect, int duration, int strength) {
        return new Item.Properties().food(wineFoodComponent(effect, duration, strength));
    }

    private static FoodProperties wineFoodComponent(MobEffect effect, int duration, int strength) {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        if (effect != null) {
            builder.effect(new MobEffectInstance(effect, duration, strength), 1.0f);
        }
        return builder.build();
    }

    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }


    private static BlockBehaviour.Properties getGrassSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).noOcclusion();
    }

    private static BlockBehaviour.Properties getGrapevineSettings() {
        return BlockBehaviour.Properties.of().strength(2.0F).randomTicks().sound(SoundType.WOOD).noOcclusion();
    }

    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties getSlabSettings() {
        return getLogBlockSettings().explosionResistance(3.0F);
    }

    private static BlockBehaviour.Properties getWineSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }

    private static ButtonBlock woodenButton(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }

        return new ButtonBlock(properties, BlockSetType.CHERRY, 30, true);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new VineryIdentifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new VineryIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return Util.registerItem(ITEMS, ITEM_REGISTRAR, new VineryIdentifier(path), itemSupplier);
    }
}
