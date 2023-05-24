package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.FlowerPotBlock;
import satisfyu.vinery.block.*;
import satisfyu.vinery.block.grape.GrapeBush;
import satisfyu.vinery.block.grape.GrapeVineBlock;
import satisfyu.vinery.block.grape.SavannaGrapeBush;
import satisfyu.vinery.block.grape.TaigaGrapeBush;
import satisfyu.vinery.block.stem.LatticeStemBlock;
import satisfyu.vinery.block.stem.PaleStemBlock;
import satisfyu.vinery.block.storage.*;
import satisfyu.vinery.util.GeneralUtil;
import satisfyu.vinery.util.GrapevineType;
import satisfyu.vinery.util.generators.ConfiguredFeatureSaplingGenerator;
import satisfyu.vinery.util.sign.block.TerraformSignBlock;
import satisfyu.vinery.util.sign.block.TerraformWallSignBlock;
import satisfyu.vinery.world.VineryConfiguredFeatures;

import java.util.Random;
import java.util.function.Supplier;

import static satisfyu.vinery.registry.ItemRegistry.APPLE_PIE_SLICE;

public class BlockRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Vinery.MODID, Registry.BLOCK_REGISTRY);

	public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

	//Grapes
	public static final RegistrySupplier<Block> RED_GRAPE_BUSH = registerB("red_grape_bush",
			() -> new GrapeBush(getBushSettings(), GrapevineType.RED));

	public static final RegistrySupplier<Block> WHITE_GRAPE_BUSH = registerB("white_grape_bush",
			() -> new GrapeBush(getBushSettings(), GrapevineType.WHITE));

	public static final RegistrySupplier<Block> SAVANNA_RED_GRAPE_BUSH = registerB("savanna_grape_bush_red",
			() -> new SavannaGrapeBush(getBushSettings(), GrapevineType.SAVANNA_RED));

	public static final RegistrySupplier<Block> SAVANNA_WHITE_GRAPE_BUSH = registerB("savanna_grape_bush_white",
			() -> new SavannaGrapeBush(getBushSettings(), GrapevineType.SAVANNA_WHITE));

	public static final RegistrySupplier<Block> TAIGA_RED_GRAPE_BUSH = registerB("taiga_grape_bush_red",
			() -> new TaigaGrapeBush(getBushSettings(), GrapevineType.TAIGA_RED));

	public static final RegistrySupplier<Block> TAIGA_WHITE_GRAPE_BUSH = registerB("taiga_grape_bush_white",
			() -> new TaigaGrapeBush(getBushSettings(), GrapevineType.TAIGA_WHITE));

	public static final RegistrySupplier<Block> JUNGLE_RED_GRAPE_BUSH = registerB("jungle_grape_bush_red",
			() -> new GrapeVineBlock(getBushSettings(), GrapevineType.JUNGLE_RED));

	public static final RegistrySupplier<Block> JUNGLE_WHITE_GRAPE_BUSH = registerB("jungle_grape_bush_white",
			() -> new GrapeVineBlock(getBushSettings(), GrapevineType.JUNGLE_WHITE));

	//Saplings
	public static final RegistrySupplier<Block> CHERRY_SAPLING = registerB("cherry_sapling",
			() -> new SaplingBlock(new ConfiguredFeatureSaplingGenerator() {
				@Override
				protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getTreeConfiguredFeature(Random random, boolean bees) {
					if (random.nextBoolean()) { return VineryConfiguredFeatures.CHERRY_KEY; }
					return VineryConfiguredFeatures.CHERRY_VARIANT_KEY;
				}
			}, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak()
					.sound(SoundType.GRASS)));

	public static final RegistrySupplier<Block> OLD_CHERRY_SAPLING = registerB("old_cherry_sapling",
			() -> new SaplingBlock(new ConfiguredFeatureSaplingGenerator() {
				@Override
				protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getTreeConfiguredFeature(Random random, boolean bees) {
					if (random.nextBoolean()) {
						if (bees) { return VineryConfiguredFeatures.OLD_CHERRY_BEE_KEY; }
						return VineryConfiguredFeatures.OLD_CHERRY_KEY;
					}
					else {
						if (bees) { return VineryConfiguredFeatures.OLD_CHERRY_VARIANT_WITH_BEE_KEY; }
						return VineryConfiguredFeatures.OLD_CHERRY_VARIANT_KEY;
					}
				}
			}, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak()
					.sound(SoundType.GRASS)));

	public static final RegistrySupplier<Block> GRAPEVINE_LEAVES = registerB("grapevine_leaves",
			() -> new GrapevineLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));

	public static final RegistrySupplier<Block> CHERRY_LEAVES = registerB("cherry_leaves",
			() -> new CherryLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));

	public static final RegistrySupplier<Block> WHITE_GRAPE_CRATE = registerB("white_grape_crate",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

	public static final RegistrySupplier<Block> RED_GRAPE_CRATE = registerB("red_grape_crate",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

	public static final RegistrySupplier<Block> CHERRY_CRATE = registerB("cherry_crate",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

	public static final RegistrySupplier<Block> GRAPEVINE_LATTICE = registerB("grapevine_lattice",
			() -> new LatticeStemBlock(getGrapevineSettings()));

	public static final RegistrySupplier<Block> GRAPEVINE_POT = registerB("grapevine_pot",
			() -> new GrapevinePotBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));

	public static final RegistrySupplier<Block> FERMENTATION_BARREL = registerB("fermentation_barrel",
			() -> new FermentationBarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));

	public static final RegistrySupplier<Block> WINE_PRESS = registerB("wine_press",
			() -> new WinePressBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F)
					.sound(SoundType.WOOD).noOcclusion()));

	public static final RegistrySupplier<Block> CHAIR = registerB("chair",
			() -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f)
					.sound(SoundType.WOOD)));

	public static final RegistrySupplier<Block> TABLE = registerB("table",
			() -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

	public static final RegistrySupplier<Block> WOOD_FIRED_OVEN = registerB("wood_fired_oven",
			() -> new WoodFiredOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)
					.lightLevel(state -> state.getValue(WoodFiredOvenBlock.LIT) ? 13 : 0)));

	public static final RegistrySupplier<Block> STOVE = registerB("stove",
			() -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(block -> 12)));

	public static final RegistrySupplier<Block> KITCHEN_SINK = registerB("kitchen_sink",
			() -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));

	public static final RegistrySupplier<Block> WINE_RACK_1 = registerB("wine_rack_1",
			() -> new NineBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F)
					.sound(SoundType.WOOD).noOcclusion()));

	public static final RegistrySupplier<Block> WINE_RACK_2 = registerB("wine_rack_2",
			() -> new FourBottleStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F)
					.sound(SoundType.WOOD).noOcclusion()));

	public static final RegistrySupplier<Block> WINE_RACK_3 = registerB("wine_rack_3",
			() -> new WineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F)
					.sound(SoundType.WOOD), VinerySoundEvents.WINE_RACK_3_OPEN.getOrNull(),
					VinerySoundEvents.WINE_RACK_3_CLOSE.getOrNull()));

	public static final RegistrySupplier<Block> WINE_RACK_5 = registerB("wine_rack_5",
			() -> new WineRackStorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F)
					.sound(SoundType.WOOD), VinerySoundEvents.WINE_RACK_5_OPEN.getOrNull(),
					VinerySoundEvents.WINE_RACK_5_CLOSE.getOrNull()));

	public static final RegistrySupplier<Block> BARREL = registerB("barrel",
			() -> new BarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));

	public static final RegistrySupplier<Block> STRIPPED_CHERRY_LOG = registerB("stripped_cherry_log",
			GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> CHERRY_LOG = registerB("cherry_log", GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> STRIPPED_CHERRY_WOOD = registerB("stripped_cherry_wood",
			GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> CHERRY_WOOD = registerB("cherry_wood", GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> STRIPPED_OLD_CHERRY_LOG = registerB("stripped_old_cherry_log",
			GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> OLD_CHERRY_LOG = registerB("old_cherry_log", GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> STRIPPED_OLD_CHERRY_WOOD = registerB("stripped_old_cherry_wood",
			GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> OLD_CHERRY_WOOD = registerB("old_cherry_wood", GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> CHERRY_BEAM = registerB("cherry_beam", GeneralUtil::logBlock);

	public static final RegistrySupplier<Block> CHERRY_PLANKS = registerB("cherry_planks",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

	public static final RegistrySupplier<Block> CHERRY_FLOORBOARD = registerB("cherry_floorboard",
			() -> new Block(BlockBehaviour.Properties.copy(CHERRY_PLANKS.getOrNull())));

	public static final RegistrySupplier<Block> CHERRY_STAIRS = registerB("cherry_stairs",
			() -> new StairBlock(CHERRY_PLANKS.getOrNull().defaultBlockState(),
					BlockBehaviour.Properties.copy(CHERRY_PLANKS.getOrNull())));

	public static final RegistrySupplier<Block> CHERRY_SLAB = registerB("cherry_slab",
			() -> new SlabBlock(getSlabSettings()));

	public static final RegistrySupplier<Block> CHERRY_FENCE = registerB("cherry_fence",
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
	//Wines

	public static final RegistrySupplier<Block> CHERRY_FENCE_GATE = registerB("cherry_fence_gate",
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));

	public static final RegistrySupplier<Block> CHERRY_BUTTON = registerB("cherry_button", BlockRegistry::woodenButton);

	public static final RegistrySupplier<Block> CHERRY_PRESSURE_PLATE = registerB("cherry_pressure_plate",
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
					BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));

	public static final RegistrySupplier<Block> CHERRY_DOOR = registerB("cherry_door",
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)));

	public static final RegistrySupplier<Block> CHERRY_TRAPDOOR = registerB("cherry_trapdoor",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)));

	public static final RegistrySupplier<Block> WINDOW = registerB("window",
			() -> new WindowBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE)));

	public static final RegistrySupplier<Block> LOAM = registerB("loam", () -> new Block(BlockBehaviour.Properties.of(
			Material.DIRT).strength(2.0F, 3.0F).sound(SoundType.SAND)));

	public static final RegistrySupplier<Block> LOAM_STAIRS = registerB("loam_stairs",
			() -> new StairBlock(LOAM.getOrNull().defaultBlockState(), BlockBehaviour.Properties.of(Material.DIRT)
					.strength(2.0F, 3.0F).sound(SoundType.SAND)));

	public static final RegistrySupplier<Block> LOAM_SLAB = registerB("loam_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(2.0F, 3.0F)
					.sound(SoundType.SAND)));

	public static final RegistrySupplier<Block> COARSE_DIRT_SLAB = registerB("coarse_dirt_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT)));

	public static final RegistrySupplier<Block> DIRT_SLAB = registerB("dirt_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)));

	public static final RegistrySupplier<Block> GRASS_SLAB = registerB("grass_slab",
			() -> new SnowyVariantSlabBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));

	public static final RegistrySupplier<Block> STAL_WINE = registerB("stal_wine",
			() -> new WineBottleBlock(getWineSettings(), 3));

	public static final RegistrySupplier<Block> KELP_CIDER = registerB("kelp_cider",
			() -> new WineBottleBlock(getWineSettings(), 3));

		public static final RegistrySupplier<Block> STRAD_WINE = registerB("strad_wine",
			() -> new WineBottleBlock(getWineSettings(), 2));

	public static final RegistrySupplier<Block> MAGNETIC_WINE = registerB("magnetic_wine",
			() -> new WineBottleBlock(getWineSettings(), 1));

	public static final RegistrySupplier<Block> CHORUS_WINE = registerB("chorus_wine",
			() -> new WineBottleBlock(getWineSettings(), 1));

		public static final RegistrySupplier<Block> AEGIS_WINE = registerB("aegis_wine",
			() -> new WineBottleBlock(getWineSettings(), 3));

	public static final RegistrySupplier<Block> SOLARIS_WINE = registerB("solaris_wine",
			() -> new WineBottleBlock(getWineSettings(), 3));

		public static final RegistrySupplier<Block> NOIR_WINE = registerB("noir_wine",
			() -> new WineBottleBlock(getWineSettings(), 3));

	public static final RegistrySupplier<Block> BOLVAR_WINE = registerB("bolvar_wine",
			() -> new WineBottleBlock(getWineSettings(), 3));
		public static final RegistrySupplier<Block> CHERRY_WINE = registerB("cherry_wine",
			() -> new WineBottleBlock(getWineSettings(), 3));

	public static final RegistrySupplier<Block> CLARK_WINE = registerB("clark_wine",
			() -> new WineBottleBlock(getWineSettings(), 3));
		//Big Wines
	public static final RegistrySupplier<Block> JELLIE_WINE = registerB("jellie_wine",
			() -> new WineBottleBlock(getWineSettings(), 1));

	public static final RegistrySupplier<Block> APPLE_CIDER = registerB("apple_cider",
			() -> new WineBottleBlock(getWineSettings(), 2));

		public static final RegistrySupplier<Block> APPLE_WINE = registerB("apple_wine",
			() -> new WineBottleBlock(getWineSettings(), 3));

	public static final RegistrySupplier<Block> CHENET_WINE = registerB("chenet_wine",
			() -> new WineBottleBlock(getWineSettings(), 2));

	public static final RegistrySupplier<Block> KING_DANIS_WINE = registerB("king_danis_wine",
			() -> new WineBottleBlock(getWineSettings(), 1));

		public static final RegistrySupplier<Block> MELLOHI_WINE = registerB("mellohi_wine",
			() -> new WineBottleBlock(getWineSettings(), 2));

	public static final RegistrySupplier<Block> GRAPEVINE_STEM = registerB("grapevine_stem",
			() -> new PaleStemBlock(getGrapevineSettings()));

		public static final RegistrySupplier<Block> STORAGE_POT = registerB("storage_pot",
			() -> new StoragePotBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F)
					.sound(SoundType.WOOD), SoundEvents.DYE_USE, SoundEvents.DYE_USE));

	//Flower Box/Pot
	public static final RegistrySupplier<Block> FLOWER_BOX = registerB("flower_box",
			() -> new FlowerBoxBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

		public static final RegistrySupplier<Block> FLOWER_POT = registerB("flower_pot",
			() -> new FlowerPotBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

	public static final RegistrySupplier<Block> CHERRY_JAR = registerB("cherry_jar",
			() -> new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion()));

		public static final RegistrySupplier<Block> CHERRY_JAM = registerB("cherry_jam",
			() -> new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion()));

	public static final RegistrySupplier<Block> SWEETBERRY_JAM = registerB("sweetberry_jam",
			() -> new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion()
					.sound(SoundType.GLASS)));


	public static final RegistrySupplier<Block> GRAPE_JAM = registerB("grape_jam",
			() -> new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion()
					.sound(SoundType.GLASS)));

		public static final RegistrySupplier<Block> WINE_BOX = registerB("wine_box",
			() -> new WineBox(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).noOcclusion()));

	public static final RegistrySupplier<Block> BIG_TABLE = registerB("big_table",
			() -> new BigTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 2.0F)));

		public static final RegistrySupplier<Block> SHELF = registerB("shelf",
			() -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)
					.noOcclusion()));

	public static final RegistrySupplier<Block> APPLE_CRATE = registerB("apple_crate",
			() -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));


	public static final RegistrySupplier<Block> BASKET = registerB("basket",
			() -> new BasketBlock(BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion(), 1));

		public static final RegistrySupplier<Block> COOKING_POT = registerB("cooking_pot",
			() -> new CookingPotBlock(BlockBehaviour.Properties.of(Material.STONE).instabreak().noOcclusion()));

	public static final RegistrySupplier<Block> STACKABLE_LOG = registerB("stackable_log", () -> new StackableLogBlock(
			getLogBlockSettings().noOcclusion().lightLevel(state -> state.getValue(StackableLogBlock.FIRED) ? 13 : 0)));

	public static final RegistrySupplier<Block> APPLE_JAM = registerB("apple_jam",
			() -> new StackableBlock(BlockBehaviour.Properties.of(Material.GLASS).instabreak().noOcclusion()));
	public static final RegistrySupplier<Block> APPLE_PIE = registerB("apple_pie", () -> new PieBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), APPLE_PIE_SLICE));
	public static final RegistrySupplier<Block> CRUSTY_BREAD = registerB("crusty_bread",
			() -> new BreadBlock(BlockBehaviour.Properties.copy(Blocks.CAKE).noOcclusion()));
		//Signs
	private static final ResourceLocation CHERRY_SIGN_TEXTURE = new ResourceLocation("entity/signs/cherry");

	public static final RegistrySupplier<TerraformSignBlock> CHERRY_SIGN = registerB("cherry_sign",
			() -> new TerraformSignBlock(CHERRY_SIGN_TEXTURE, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN)));
	public static final RegistrySupplier<Block> CHERRY_WALL_SIGN = registerB("cherry_wall_sign",
			() -> new TerraformWallSignBlock(CHERRY_SIGN_TEXTURE,
					BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN)));

	private static <T extends Block> RegistrySupplier<T> registerB(String path, Supplier<T> block) {
		final ResourceLocation id = new VineryIdentifier(path);
		return BLOCK_REGISTRAR.register(id, block);
	}

	private static BlockBehaviour.Properties getBushSettings() {
		return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
	}

	private static BlockBehaviour.Properties getGrapevineSettings() {
		return BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).randomTicks().sound(SoundType.WOOD)
				.noOcclusion();
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

	private static ButtonBlock woodenButton() {
		return new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON));
	}

	public static void init() {
	}
}
