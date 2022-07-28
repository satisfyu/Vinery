package daniking.vinery.registry;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.StrippableLogBlock;
import daniking.vinery.GrapeBushSeedItem;
import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.*;
import daniking.vinery.block.BannerBlock;
import daniking.vinery.util.GrapevineType;
import daniking.vinery.util.WineType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectRegistry {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
    // Red Grapes
    public static final Block RED_GRAPE_BUSH = register("red_grape_bush",
            new GrapeBush(getBushSettings(), GrapevineType.RED), false);
    public static final Item RED_GRAPE = register("red_grape",
            new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.RED));
    public static final Item RED_GRAPE_SEEDS = register("red_grape_seeds",
            new GrapeBushSeedItem(RED_GRAPE_BUSH, getSettings(), GrapevineType.RED));

    // White Grapes
    public static final Block WHITE_GRAPE_BUSH = register("white_grape_bush",
            new GrapeBush(getBushSettings(), GrapevineType.WHITE), false);
    public static final Item WHITE_GRAPE = register("white_grape",
            new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.WHITE));
    public static final Item WHITE_GRAPE_SEEDS = register("white_grape_seeds",
            new GrapeBushSeedItem(WHITE_GRAPE_BUSH, getSettings(), GrapevineType.WHITE));

    // Vines
    // public static final Block EMPTY_RED_VINE = register("empty_red_vine", new
    // RedVineBlock(FabricBlockSettings.copyOf(Blocks.VINE),
    // RedVineBlock.Variant.Empty));
    // public static final Block RED_VINE = register("red_vine", new
    // RedVineBlock(FabricBlockSettings.copyOf(Blocks.VINE),
    // RedVineBlock.Variant.A));
    // public static final Block RED_VINE_VARIANT_B = register("red_vine_variant_b",
    // new RedVineBlock(FabricBlockSettings.copyOf(Blocks.VINE),
    // RedVineBlock.Variant.B), false);
    // public static final Block RED_VINE_VARIANT_C = register("red_vine_variant_c",
    // new RedVineBlock(FabricBlockSettings.copyOf(Blocks.VINE),
    // RedVineBlock.Variant.C), false);
    // Rocks
    public static final Block ROCKS = register("rocks", new RockBlock(getRockSettings()));
    public static final Block ROCKS_VARIANT_B = register("rocks_variant_b", new RockBlock(getRockSettings()), false);
    public static final Block ROCKS_VARIANT_C = register("rocks_variant_c", new RockBlock(getRockSettings()), false);

    // Grass Flowers
    public static final Block RED_GRASS_FLOWER = register("red_grass_flower",
            new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.RED));
    public static final Block RED_GRASS_FLOWER_VARIANT_B = register("red_grass_flower_variant_b",
            new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.RED), false);
    public static final Block PINK_GRASS_FLOWER = register("pink_grass_flower",
            new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.PINK));
    public static final Block PINK_GRASS_FLOWER_VARIANT_B = register("pink_grass_flower_variant_b",
            new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.PINK), false);
    public static final Block WHITE_GRASS_FLOWER = register("white_grass_flower",
            new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.WHITE));

    public static final Block GRAPEVINE_STEM = register("grapevine_stem",
            new GrapevineStemBlock(getGrapevineSettings()));

    public static final Block GRAPEVINE_LEAVES = register("grapevine_leaves",
            new GrapevineLeaves(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));

    public static final Block GRAPEVINE_POT = register("grapevine_pot",
            new GrapevinePotBlock(FabricBlockSettings.copyOf(Blocks.BARREL)));
    public static final Block RED_GRAPEJUICE_WINE_BOTTLE = register("red_grapejuice_wine_bottle",
            new RedGrapejuiceWineBottle(getWineSettings()));
    public static final Block WHITE_GRAPEJUICE_WINE_BOTTLE = register("white_grapejuice_wine_bottle",
            new WhiteGrapejuiceWineBottle(getWineSettings()));

    public static final Block STOVE = register("stove", new StoveBlock(
            FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(state -> state.get(StoveBlock.LIT) ? 13 : 0)));
    public static final Item CRUSTY_BREAD = register("crusty_bread", new Item(getSettings()));

    // Cherry
    public static final Block CHERRY_PLANKS = register("cherry_planks",
            new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final Block STRIPPED_CHERRY_LOG = registerLog("stripped_cherry_log");
    public static final Block CHERRY_LOG = register("cherry_log",
            new StrippableLogBlock(() -> STRIPPED_CHERRY_LOG, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_OLD_CHERRY_LOG = registerLog("stripped_old_cherry_log");
    public static final Block OLD_CHERRY_LOG = register("old_cherry_log",
            new StrippableLogBlock(() -> STRIPPED_OLD_CHERRY_LOG, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_CHERRY_WOOD = registerLog("stripped_cherry_wood");
    public static final Block CHERRY_WOOD = register("cherry_wood",
            new StrippableLogBlock(() -> STRIPPED_CHERRY_WOOD, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block STRIPPED_OLD_CHERRY_WOOD = registerLog("stripped_old_cherry_wood");
    public static final Block OLD_CHERRY_WOOD = register("old_cherry_wood",
            new StrippableLogBlock(() -> STRIPPED_OLD_CHERRY_WOOD, MapColor.OAK_TAN, getLogBlockSettings()));
    public static final Block CHERRY_SLAB = register("cherry_slab", new SlabBlock(getSlabSettings()));
    public static final Block CHERRY_STAIRS = register("cherry_stairs",
            new StairsBlock(CHERRY_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(CHERRY_PLANKS)));
    public static final Block CHERRY_FENCE = register("cherry_fence",
            new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
    public static final Block CHERRY_FENCE_GATE = register("cherry_fence_gate",
            new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
    public static final Block CHERRY_BUTTON = register("cherry_button",
            new WoodenButtonBlock(AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)));
    public static final Block CHERRY_PRESSURE_PLATE = register("cherry_pressure_plate", new PressurePlateBlock(
            PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)));
    public static final Block CHERRY_DOOR = register("cherry_door",
            new DoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_DOOR)));
    public static final Block CHERRY_TRAPDOOR = register("cherry_trapdoor",
            new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)));
    private static final Identifier CHERRY_SIGN_TEXTURE = new VineryIdentifier("entity/sign/cherry");
    public static final TerraformSignBlock CHERRY_SIGN = register("cherry_sign",
            new TerraformSignBlock(CHERRY_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)), false);
    public static final Block CHERRY_WALL_SIGN = register("cherry_wall_sign",
            new TerraformWallSignBlock(CHERRY_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)), false);
    public static final Item CHERRY_SIGN_ITEM = register("cherry_sign",
            new SignItem(getSettings().maxCount(16), CHERRY_SIGN, CHERRY_WALL_SIGN));
    public static final Block CHERRY_LEAVES = register("cherry_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).nonOpaque()));
    public static final Block CHERRY_LEAVES_VARIANT = register("cherry_leaves_variant",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).nonOpaque()));
    public static final Block PINK_CHERRY_LEAVES = register("pink_cherry_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static final Block PINK_CHERRY_LEAVES_VARIANT = register("pink_cherry_leaves_variant",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static final Block STACKABLE_LOG = register("stackable_log",
            new StackableLogBlock(getLogBlockSettings().nonOpaque()));
    public static final Item CHERRY = register("cherry", new Item(getSettings().food(FoodComponents.COOKIE)));
    public static final Block COOKING_POT = register("cooking_pot",
            new CookingPotBlock(FabricBlockSettings.of(Material.STONE).breakInstantly().nonOpaque()));
    public static final Block CHERRY_JAR = register("cherry_jar",
            new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque()));
    public static final Block CHERRY_JAM = register("cherry_jam",
            new StackableBlock(FabricBlockSettings.of(Material.GLASS).breakInstantly().nonOpaque()));

    public static final Item VINEMAKER_APRON = register("vinemaker_apron", new ArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.CHEST, getSettings()));
    public static final Item VINEMAKER_GLOVES = register("vinemaker_gloves", new ArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.CHEST, getSettings()));
    public static final Item VINEMAKER_LEGGINGS = register("vinemaker_leggings", new ArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.LEGS, getSettings()));
    public static final Item VINEMAKER_BOOTS = register("vinemaker_boots", new ArmorItem(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.FEET, getSettings()));
    public static final Block FERMENTATION_BARREL = register("fermentation_barrel",
            new FermentationBarrelBlock(AbstractBlock.Settings.copy(Blocks.BARREL).nonOpaque()));

    public static final Block WINE_BOTTLE = register("wine_bottle",
            new EmptyWineBottleBlock(AbstractBlock.Settings.copy(Blocks.GLASS).breakInstantly().nonOpaque()));

    public static final Block CHENET_WINE = register("chenet_wine", new WineBottleBlock(getWineSettings(), WineType.RED));
    public static final Block KING_DANIS_WINE = register("king_danis_wine", new WineBottleBlock(getWineSettings(), WineType.RED));
    public static final Block NOIR_WINE = register("noir_wine", new WineBottleBlock(getWineSettings(), WineType.RED));
    public static final Block CLARK_WINE = register("clark_wine", new WineBottleBlock(getWineSettings(), WineType.WHITE));
    public static final Block MELLOHI_WINE = register("mellohi_wine", new WineBottleBlock(getWineSettings(), WineType.WHITE));
    public static final Block BANNER = register("banner", new BannerBlock(FabricBlockSettings.of(Material.WOOD).breakInstantly().nonOpaque()));
    public static final Block BIG_TABLE = register("big_table", new BigTableBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 2.0F)));
    
    private static PillarBlock registerLog(String path) {
        return register(path, new PillarBlock(getLogBlockSettings()));
    }

    private static <T extends Block> T register(String path, T block) {
        return register(path, block, true);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem) {
        final Identifier id = new VineryIdentifier(path);
        BLOCKS.put(id, block);
        if (registerItem) {
            ITEMS.put(id, new BlockItem(block, getSettings()));
        }
        return block;
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

    private static Item.Settings getSettings() {
        return new Item.Settings().group(Vinery.CREATIVE_TAB);
    }

    private static Block.Settings getVineSettings() {
        return FabricBlockSettings.copyOf(Blocks.VINE);
    }

    private static Block.Settings getBushSettings() {
        return FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH);
    }

    private static AbstractBlock.Settings getRockSettings() {
        return FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.0F, 3.0F)
                .nonOpaque();
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
