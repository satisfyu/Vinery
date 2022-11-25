package daniking.vinery;

import daniking.vinery.registry.*;
import daniking.vinery.world.VineryConfiguredFeatures;
import daniking.vinery.world.village.AdditionalHouses;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vinery implements ModInitializer {

    public static final String MODID = "vinery";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(new VineryIdentifier("creative_tab"), () -> new ItemStack(ObjectRegistry.RED_GRAPE));
    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.of(Registry.BLOCK_KEY, new VineryIdentifier("allows_cooking_on_pot"));
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.of(Registry.BLOCK_KEY, new VineryIdentifier("can_not_connect"));
    public static final TagKey<Item> CHERRY_LOGS = TagKey.of(Registry.ITEM_KEY, new VineryIdentifier("cherry_logs"));
    public static final TagKey<Block> WINE_RACK = TagKey.of(Registry.BLOCK_KEY, new VineryIdentifier("wine_racks"));

    @Override
    public void onInitialize() {
        VineryEffects.init();
        ObjectRegistry.init();
        VineryBlockEntityTypes.init();
        AdditionalHouses.registerNewVillageStructures();
        VineryScreenHandlerTypes.init();
        VineryRecipeTypes.init();
        LootTableEvents.MODIFY.register((resourceManager, manager, id, supplier, setter) -> {
            final Identifier resourceLocation = new VineryIdentifier("inject/seeds");
            if (Blocks.GRASS.getLootTableId().equals(id) || Blocks.TALL_GRASS.getLootTableId().equals(id) || Blocks.FERN.getLootTableId().equals(id)) {
                supplier.pool(LootPool.builder().with(LootTableEntry.builder(resourceLocation).weight(1)).build());
            }
        });
        VineryBoatTypes.init();
        VineryConfiguredFeatures.init();
        VinerySoundEvents.init();
        VineryVillagers.init();
        VineryEntites.init();
    }
}

