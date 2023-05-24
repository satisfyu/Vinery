package satisfyu.vinery.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SignItem;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.GrapeItem;
import satisfyu.vinery.item.*;
import satisfyu.vinery.util.GrapevineType;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static satisfyu.vinery.registry.BlockRegistry.*;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Vinery.MODID, Registry.ITEM_REGISTRY);

	public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();

	public static final RegistrySupplier<Item> CHERRY = registerI("cherry",
			() -> new CherryItem(getSettings().food(Foods.COOKIE)));

	public static final RegistrySupplier<Item> ROTTEN_CHERRY = registerI("rotten_cherry",
			() -> new RottenCherryItem(getSettings().food(Foods.POISONOUS_POTATO)));

	public static final RegistrySupplier<Item> WINE_BOTTLE = registerI("wine_bottle", () -> new Item(getSettings()));

	public static final RegistrySupplier<Item> APPLE_JUICE = registerI("apple_juice",
			() -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> RED_GRAPEJUICE_WINE_BOTTLE = registerI("red_grapejuice_wine_bottle",
			() -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> WHITE_GRAPEJUICE_WINE_BOTTLE = registerI("white_grapejuice_wine_bottle",
			() -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> SAVANNA_RED_GRAPEJUICE_BOTTLE = registerI(
			"savanna_red_grapejuice_bottle", () -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> SAVANNA_WHITE_GRAPEJUICE_BOTTLE = registerI(
			"savanna_white_grapejuice_bottle", () -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> TAIGA_RED_GRAPEJUICE_BOTTLE = registerI("taiga_red_grapejuice_bottle",
			() -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> TAIGA_WHITE_GRAPEJUICE_BOTTLE = registerI(
			"taiga_white_grapejuice_bottle", () -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> JUNGLE_RED_GRAPEJUICE_BOTTLE = registerI("jungle_red_grapejuice_bottle",
			() -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> JUNGLE_WHITE_GRAPEJUICE_BOTTLE = registerI(
			"jungle_white_grapejuice_bottle", () -> new JuiceItem(getSettings()));

	public static final RegistrySupplier<Item> FAUCET = registerI("faucet", () -> new FaucetItem(getSettings()));

	public static final RegistrySupplier<Item> STRAW_HAT = registerI("straw_hat",
			() -> new StrawHatItem(getSettings().rarity(Rarity.RARE)));

	public static final RegistrySupplier<Item> VINEMAKER_APRON = registerI("vinemaker_apron",
			() -> new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.CHEST,
					getSettings().rarity(Rarity.EPIC)));

	public static final RegistrySupplier<Item> VINEMAKER_LEGGINGS = registerI("vinemaker_leggings",
			() -> new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.LEGS,
					getSettings().rarity(Rarity.RARE)));

	public static final RegistrySupplier<Item> VINEMAKER_BOOTS = registerI("vinemaker_boots",
			() -> new WinemakerDefaultArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.FEET,
					getSettings().rarity(Rarity.RARE)));

	public static final RegistrySupplier<Item> GLOVES = registerI("gloves",
			() -> new GlovesItem(getSettings().rarity(Rarity.RARE)));

	public static final RegistrySupplier<Item> APPLE_MASH = registerI("apple_mash",
			() -> new CherryItem(getSettings().food(Foods.APPLE)));

	public static final RegistrySupplier<Item> APPLESAUCE = registerI("applesauce",
			() -> new AppleSauceItem(getSettings().food(Foods.COOKED_RABBIT)));

	public static final RegistrySupplier<Item> APPLE_PIE_SLICE = registerI("apple_pie_slice",
			() -> new Item(getSettings().food(Foods.COOKED_BEEF)));

	public static final RegistrySupplier<Item> APPLE_CUPCAKE = registerI("apple_cupcake",
			() -> new CupcakeItem(getSettings().food(Foods.GOLDEN_CARROT)));

	public static final RegistrySupplier<Item> DOUGH = registerI("dough", () -> new CherryItem(getSettings()));

	public static final RegistrySupplier<Item> CHOCOLATE_BREAD = registerI("chocolate_bread",
			() -> new ChocolateBreadItem(getSettings().food(Foods.BREAD)));

	public static final RegistrySupplier<Item> TOAST = registerI("toast",
			() -> new ToastItem(getSettings().food(Foods.BEETROOT_SOUP)));

	public static final RegistrySupplier<Item> DONUT = registerI("donut",
			() -> new DoughnutItem(getSettings().food(Foods.CARROT)));

	public static final RegistrySupplier<Item> MILK_BREAD = registerI("milk_bread",
			() -> new MilkBreadItem(getSettings().food(Foods.COOKIE)));

	public static final RegistrySupplier<Item> BREAD_SLICE = registerI("bread_slice",
			() -> new Item(getSettings().food(Foods.BAKED_POTATO)));

	public static final RegistrySupplier<Item> MULE_SPAWN_EGG = registerI("mule_spawn_egg",
			() -> new ArchitecturySpawnEggItem(VineryEntites.MULE, 0x8b7867, 0x5a4e43, getSettings()));

	public static final RegistrySupplier<Item> WANDERING_WINEMAKER_SPAWN_EGG = registerI(
			"wandering_winemaker_spawn_egg",
			() -> new ArchitecturySpawnEggItem(VineryEntites.WANDERING_WINEMAKER, 0xb78272, 0x3c4a73, getSettings()));

	//Grapes
	public static final RegistrySupplier<Item> RED_GRAPE_SEEDS = registerI("red_grape_seeds",
			() -> new GrapeBushSeedItem(RED_GRAPE_BUSH.getOrNull(), getSettings(), GrapevineType.RED));

	public static final RegistrySupplier<Item> RED_GRAPE = registerI("red_grape",
			() -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.RED, RED_GRAPE_SEEDS.getOrNull()));


	public static final RegistrySupplier<Item> WHITE_GRAPE_SEEDS = registerI("white_grape_seeds",
			() -> new GrapeBushSeedItem(WHITE_GRAPE_BUSH.getOrNull(), getSettings(), GrapevineType.WHITE));

	public static final RegistrySupplier<Item> WHITE_GRAPE = registerI("white_grape",
			() -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.WHITE, WHITE_GRAPE_SEEDS.getOrNull()));


	public static final RegistrySupplier<Item> SAVANNA_RED_GRAPE_SEEDS = registerI("savanna_grape_seeds_red",
			() -> new GrapeBushSeedItem(SAVANNA_RED_GRAPE_BUSH.getOrNull(), getSettings(), GrapevineType.SAVANNA_RED));

	public static final RegistrySupplier<Item> SAVANNA_RED_GRAPE = registerI("savanna_grapes_red",
			() -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.SAVANNA_RED,
					ItemRegistry.SAVANNA_RED_GRAPE_SEEDS.getOrNull()));


	public static final RegistrySupplier<Item> SAVANNA_WHITE_GRAPE_SEEDS = registerI("savanna_grape_seeds_white",
			() -> new GrapeBushSeedItem(SAVANNA_WHITE_GRAPE_BUSH.getOrNull(), getSettings(), GrapevineType.SAVANNA_WHITE));

	public static final RegistrySupplier<Item> SAVANNA_WHITE_GRAPE = registerI("savanna_grapes_white",
			() -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.SAVANNA_WHITE,
					ItemRegistry.SAVANNA_WHITE_GRAPE_SEEDS.getOrNull()));

	public static final RegistrySupplier<Item> TAIGA_RED_GRAPE_SEEDS = registerI("taiga_grape_seeds_red",
			() -> new GrapeBushSeedItem(TAIGA_RED_GRAPE_BUSH.getOrNull(), getSettings(), GrapevineType.TAIGA_RED));

	public static final RegistrySupplier<Item> TAIGA_RED_GRAPE = registerI("taiga_grapes_red",
			() -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.TAIGA_RED,
					ItemRegistry.TAIGA_RED_GRAPE_SEEDS.getOrNull()));


	public static final RegistrySupplier<Item> TAIGA_WHITE_GRAPE_SEEDS = registerI("taiga_grape_seeds_white",
			() -> new GrapeBushSeedItem(TAIGA_WHITE_GRAPE_BUSH.getOrNull(), getSettings(), GrapevineType.TAIGA_WHITE));

	public static final RegistrySupplier<Item> TAIGA_WHITE_GRAPE = registerI("taiga_grapes_white",
			() -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), GrapevineType.TAIGA_WHITE,
					ItemRegistry.TAIGA_WHITE_GRAPE_SEEDS.getOrNull()));

	public static final RegistrySupplier<Item> JUNGLE_RED_GRAPE_SEEDS = registerI("jungle_grape_seeds_red",
			() -> new GrapeBushSeedItem(JUNGLE_RED_GRAPE_BUSH.getOrNull(), getSettings(), GrapevineType.JUNGLE_RED));

	public static final RegistrySupplier<Item> JUNGLE_RED_GRAPE = registerI("jungle_grapes_red",
			() -> new GrapeItem(getSettings().food(Foods.BAKED_POTATO), GrapevineType.JUNGLE_RED,
					ItemRegistry.JUNGLE_RED_GRAPE_SEEDS.getOrNull()));

	public static final RegistrySupplier<Item> JUNGLE_WHITE_GRAPE_SEEDS = registerI("jungle_grape_seeds_white",
			() -> new GrapeBushSeedItem(JUNGLE_WHITE_GRAPE_BUSH.getOrNull(), getSettings(), GrapevineType.JUNGLE_WHITE));

	public static final RegistrySupplier<Item> JUNGLE_WHITE_GRAPE = registerI("jungle_grapes_white",
			() -> new GrapeItem(getSettings().food(Foods.BAKED_POTATO), GrapevineType.JUNGLE_WHITE,
					ItemRegistry.JUNGLE_WHITE_GRAPE_SEEDS.getOrNull()));

	//Saplings

	public static final RegistrySupplier<Item> CHERRY_SAPLING_ITEM = registerI("cherry_sapling",
			() -> new BlockItem(CHERRY_SAPLING.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> OLD_CHERRY_SAPLING_ITEM = registerI("old_cherry_sapling",
			() -> new BlockItem(OLD_CHERRY_SAPLING.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> GRAPEVINE_LEAVES_ITEM = registerI("grapevine_leaves",
			() -> new BlockItem(GRAPEVINE_LEAVES.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_LEAVES_ITEM = registerI("cherry_leaves",
			() -> new BlockItem(CHERRY_LEAVES.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> WHITE_GRAPE_CRATE_ITEM = registerI("white_grape_crate",
			() -> new BlockItem(WHITE_GRAPE_CRATE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> RED_GRAPE_CRATE_ITEM = registerI("red_grape_crate",
			() -> new BlockItem(RED_GRAPE_CRATE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_CRATE_ITEM = registerI("cherry_crate",
			() -> new BlockItem(CHERRY_CRATE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> GRAPEVINE_LATTICE_ITEM = registerI("grapevine_lattice",
			() -> new BlockItem(GRAPEVINE_LATTICE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> GRAPEVINE_POT_ITEM = registerI("grapevine_pot",
			() -> new BlockItem(GRAPEVINE_POT.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> FERMENTATION_BARREL_ITEM = registerI("fermentation_barrel",
			() -> new BlockItem(FERMENTATION_BARREL.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> WINE_PRESS_ITEM = registerI("wine_press",
			() -> new BlockItem(WINE_PRESS.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHAIR_ITEM = registerI("chair",
			() -> new BlockItem(CHAIR.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> TABLE_ITEM = registerI("table",
			() -> new BlockItem(TABLE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> WOOD_FIRED_OVEN_ITEM = registerI("wood_fired_oven",
			() -> new BlockItem(WOOD_FIRED_OVEN.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> STOVE_ITEM = registerI("stove",
			() -> new BlockItem(STOVE.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> KITCHEN_SINK_ITEM = registerI("kitchen_sink",
			() -> new BlockItem(KITCHEN_SINK.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> WINE_RACK_1_ITEM = registerI("wine_rack_1",
			() -> new BlockItem(WINE_RACK_1.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> WINE_RACK_2_ITEM = registerI("wine_rack_2",
			() -> new BlockItem(WINE_RACK_2.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> WINE_RACK_3_ITEM = registerI("wine_rack_3",
			() -> new BlockItem(WINE_RACK_3.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> WINE_RACK_5_ITEM = registerI("wine_rack_5",
			() -> new BlockItem(WINE_RACK_5.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> BARREL_ITEM = registerI("barrel",
			() -> new BlockItem(BARREL.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> STRIPPED_CHERRY_LOG_ITEM = registerI("stripped_cherry_log",
			() -> new BlockItem(STRIPPED_CHERRY_LOG.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> CHERRY_LOG_ITEM = registerI("cherry_log",
			() -> new BlockItem(CHERRY_LOG.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> STRIPPED_CHERRY_WOOD_ITEM = registerI("stripped_cherry_wood",
			() -> new BlockItem(STRIPPED_CHERRY_WOOD.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> CHERRY_WOOD_ITEM = registerI("cherry_wood",
			() -> new BlockItem(CHERRY_WOOD.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> STRIPPED_OLD_CHERRY_LOG_ITEM = registerI("stripped_old_cherry_log",
			() -> new BlockItem(STRIPPED_OLD_CHERRY_LOG.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> OLD_CHERRY_LOG_ITEM = registerI("old_cherry_log",
			() -> new BlockItem(OLD_CHERRY_LOG.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> STRIPPED_OLD_CHERRY_WOOD_ITEM = registerI("stripped_old_cherry_wood",
			() -> new BlockItem(STRIPPED_OLD_CHERRY_WOOD.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> OLD_CHERRY_WOOD_ITEM = registerI("old_cherry_wood",
			() -> new BlockItem(OLD_CHERRY_WOOD.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> CHERRY_BEAM_ITEM = registerI("cherry_beam",
			() -> new BlockItem(CHERRY_BEAM.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> CHERRY_PLANK_ITEM = registerI("cherry_planks",
			() -> new BlockItem(CHERRY_PLANKS.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_FLOORBOARD_ITEM = registerI("cherry_floorboard",
			() -> new BlockItem(CHERRY_FLOORBOARD.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> CHERRY_STAIRS_ITEM = registerI("cherry_stairs",
			() -> new BlockItem(CHERRY_STAIRS.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> CHERRY_SLAB_ITEM = registerI("cherry_slab",
			() -> new BlockItem(CHERRY_SLAB.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> CHERRY_FENCE_ITEM = registerI("cherry_fence",
			() -> new BlockItem(CHERRY_FENCE.getOrNull(), getSettings()));
	//Wines

	public static final RegistrySupplier<Item> CHERRY_FENCE_GATE_ITEM = registerI("cherry_fence_gate",
			() -> new BlockItem(CHERRY_FENCE_GATE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_BUTTON_ITEM = registerI("cherry_button",
			() -> new BlockItem(CHERRY_BUTTON.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_PRESSURE_PLATE_ITEM = registerI("cherry_pressure_plate",
			() -> new BlockItem(CHERRY_PRESSURE_PLATE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_DOOR_ITEM = registerI("cherry_door",
			() -> new BlockItem(CHERRY_DOOR.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_TRAPDOOR_ITEM = registerI("cherry_trapdoor",
			() -> new BlockItem(CHERRY_TRAPDOOR.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> WINDOW_ITEM = registerI("window",
			() -> new BlockItem(WINDOW.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> LOAM_ITEM = registerI("loam",
			() -> new BlockItem(LOAM.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> LOAM_STAIRS_ITEM = registerI("loam_stairs",
			() -> new BlockItem(LOAM_STAIRS.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> LOAM_SLAB_ITEM = registerI("loam_slab",
			() -> new BlockItem(LOAM_SLAB.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> COARSE_DIRT_SLAB_ITEM = registerI("coarse_dirt_slab",
			() -> new BlockItem(COARSE_DIRT_SLAB.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> DIRT_SLAB_ITEM = registerI("dirt_slab",
			() -> new BlockItem(DIRT_SLAB.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> GRASS_SLAB_ITEM = registerI("grass_slab",
			() -> new BlockItem(GRASS_SLAB.getOrNull(), getSettings()));


	public static final RegistrySupplier<Item> STAL_WINE_ITEM = registerI("stal_wine",
			() -> new DrinkBlockSmallItem(STAL_WINE.getOrNull(),
					getWineItemSettings(VineryEffects.IMPROVED_REGENERATION.getOrNull())));


	public static final RegistrySupplier<Item> KELP_CIDER_ITEM = registerI("kelp_cider",
			() -> new DrinkBlockSmallItem(KELP_CIDER.getOrNull(),
					getWineItemSettings(VineryEffects.IMPROVED_WATER_BREATHING.getOrNull())));


	public static final RegistrySupplier<Item> STRAD_WINE_ITEM = registerI("strad_wine",
			() -> new DrinkBlockItem(STRAD_WINE.getOrNull(), getWineItemSettings(VineryEffects.IMPROVED_ABSORBTION.getOrNull())));

	public static final RegistrySupplier<Item> MAGNETIC_WINE_ITEM = registerI("magnetic_wine",
			() -> new DrinkBlockItem(MAGNETIC_WINE.getOrNull(), getWineItemSettings(VineryEffects.MAGNET.getOrNull())));

	public static final RegistrySupplier<Item> CHORUS_WINE_ITEM = registerI("chorus_wine",
			() -> new DrinkBlockItem(CHORUS_WINE.getOrNull(), getWineItemSettings(VineryEffects.TELEPORT.getOrNull(), 1)));

	public static final RegistrySupplier<Item> AEGIS_WINE_ITEM = registerI("aegis_wine",
			() -> new DrinkBlockItem(AEGIS_WINE.getOrNull(), getWineItemSettings(MobEffects.NIGHT_VISION)));

	public static final RegistrySupplier<Item> SOLARIS_WINE_ITEM = registerI("solaris_wine",
			() -> new DrinkBlockSmallItem(SOLARIS_WINE.getOrNull(),
					getWineItemSettings(VineryEffects.IMPROVED_STRENGTH.getOrNull())));


	public static final RegistrySupplier<Item> NOIR_WINE_ITEM = registerI("noir_wine",
			() -> new DrinkBlockSmallItem(NOIR_WINE.getOrNull(), getWineItemSettings(MobEffects.HEAL)));

	public static final RegistrySupplier<Item> BOLVAR_WINE_ITEM = registerI("bolvar_wine",
			() -> new DrinkBlockSmallItem(BOLVAR_WINE.getOrNull(), getWineItemSettings(VineryEffects.IMPROVED_HASTE.getOrNull())));



	public static final RegistrySupplier<Item> CHERRY_WINE_ITEM = registerI("cherry_wine",
			() -> new DrinkBlockSmallItem(CHERRY_WINE.getOrNull(), getWineItemSettings(MobEffects.REGENERATION)));



	public static final RegistrySupplier<Item> CLARK_WINE_ITEM = registerI("clark_wine",
			() -> new DrinkBlockSmallItem(CLARK_WINE.getOrNull(),
					getWineItemSettings(VineryEffects.IMPROVED_JUMP_BOOST.getOrNull())));


	public static final RegistrySupplier<Item> JELLIE_WINE_ITEM = registerI("jellie_wine",
			() -> new DrinkBlockBigItem(JELLIE_WINE.getOrNull(), getWineItemSettings(VineryEffects.JELLIE.getOrNull())));



	public static final RegistrySupplier<Item> APPLE_CIDER_ITEM = registerI("apple_cider",
			() -> new DrinkBlockBigItem(APPLE_CIDER.getOrNull(),
					getWineItemSettings(VineryEffects.IMPROVED_REGENERATION.getOrNull())));



	public static final RegistrySupplier<Item> APPLE_WINE_ITEM = registerI("apple_wine",
			() -> new DrinkBlockBigItem(APPLE_WINE.getOrNull(),
					getWineItemSettings(VineryEffects.IMPROVED_INSTANT_HEALTH.getOrNull())));


	public static final RegistrySupplier<Item> CHENET_WINE_ITEM = registerI("chenet_wine",
			() -> new DrinkBlockBigItem(CHENET_WINE.getOrNull(), getWineItemSettings(VineryEffects.IMPROVED_SPEED.getOrNull())));

	public static final RegistrySupplier<Item> KING_DANIS_WINE_ITEM = registerI("king_danis_wine",
			() -> new DrinkBlockBigItem(KING_DANIS_WINE.getOrNull(),
					getWineItemSettings(VineryEffects.IMPROVED_INSTANT_HEALTH.getOrNull())));

	public static final RegistrySupplier<Item> MELLOHI_WINE_ITEM = registerI("mellohi_wine",
			() -> new DrinkBlockBigItem(MELLOHI_WINE.getOrNull(),
					getWineItemSettings(VineryEffects.IMPROVED_FIRE_RESISTANCE.getOrNull())));
	public static final RegistrySupplier<Item> GRAPEVINE_STEM_ITEM = registerI("grapevine_stem",
			() -> new BlockItem(GRAPEVINE_STEM.getOrNull(), getSettings()));
	public static final RegistrySupplier<Item> STORAGE_POT_ITEM = registerI("storage_pot",
			() -> new BlockItem(STORAGE_POT.getOrNull(), getSettings()));

	//Flower Box/Pot

	public static final RegistrySupplier<Item> FLOWER_BOX_ITEM = registerI("flower_box",
			() -> new BlockItem(FLOWER_BOX.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> FLOWER_POT_ITEM = registerI("flower_pot",
			() -> new BlockItem(FLOWER_POT.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_JAR_ITEM = registerI("cherry_jar",
			() -> new BlockItem(CHERRY_JAR.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CHERRY_JAM_ITEM = registerI("cherry_jam",
			() -> new BlockItem(CHERRY_JAM.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> SWEETBERRY_JAM_ITEM = registerI("sweetberry_jam",
			() -> new BlockItem(SWEETBERRY_JAM.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> GRAPE_JAM_ITEM = registerI("grape_jam",
			() -> new BlockItem(GRAPE_JAM.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> WINE_BOX_ITEM = registerI("wine_box",
			() -> new BlockItem(WINE_BOX.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> BIG_TABLE_ITEM = registerI("big_table",
			() -> new BlockItem(BIG_TABLE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> SHELF_ITEM = registerI("shelf",
			() -> new BlockItem(SHELF.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> APPLE_CRATE_ITEM = registerI("apple_crate",
			() -> new BlockItem(APPLE_CRATE.getOrNull(), getSettings()));
	public static final RegistrySupplier<Item> BASKET_ITEM = registerI("basket",
			() -> new BlockItem(BASKET.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> COOKING_POT_ITEM = registerI("cooking_pot",
			() -> new BlockItem(COOKING_POT.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> STACKABLE_LOG_ITEM = registerI("stackable_log",
			() -> new BlockItem(STACKABLE_LOG.getOrNull(), getSettings()));
	public static final RegistrySupplier<Item> APPLE_JAM_ITEM = registerI("apple_jam",
			() -> new BlockItem(APPLE_JAM.getOrNull(), getSettings()));
	public static final RegistrySupplier<Item> APPLE_PIE_ITEM = registerI("apple_pie",
			() -> new BlockItem(APPLE_PIE.getOrNull(), getSettings()));

	public static final RegistrySupplier<Item> CRUSTY_BREAD_ITEM = registerI("crusty_bread",
			() -> new BlockItem(CRUSTY_BREAD.getOrNull(), getSettings()));

	//Signs
	private static final ResourceLocation CHERRY_SIGN_TEXTURE = new ResourceLocation("entity/signs/cherry");

	public static final RegistrySupplier<Item> CHERRY_SIGN_ITEM = registerI("cherry_sign",
			() -> new SignItem(getSettings().stacksTo(16), CHERRY_SIGN.getOrNull(), CHERRY_WALL_SIGN.getOrNull()));

	private static <T extends Item> RegistrySupplier<T> registerI(String path, Supplier<T> item) {
		final ResourceLocation id = new VineryIdentifier(path);
		return ITEM_REGISTRAR.register(id, item);
	}

	private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
		Item.Properties settings = new Item.Properties().tab(Vinery.VINERY_TAB);
		consumer.accept(settings);
		return settings;
	}

	private static Item.Properties getSettings() {
		return getSettings(settings -> {
		});
	}

	private static Item.Properties getWineItemSettings(MobEffect effect) {
		return getSettings().food(wineFoodComponent(effect, 45 * 20));
	}

	private static Item.Properties getWineItemSettings(MobEffect effect, int duration) {
		return getSettings().food(wineFoodComponent(effect, duration));
	}

	private static FoodProperties wineFoodComponent(MobEffect effect, int duration) {
		FoodProperties.Builder component = new FoodProperties.Builder().nutrition(1);
        if (effect != null) { component.effect(new MobEffectInstance(effect, duration), 1.0f); }
		return component.build();
	}

	public static void init() {
	}
}
