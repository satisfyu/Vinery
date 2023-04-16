package satisfyu.vinery.fabriclike;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.npc.VillagerTrades;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VineryBoatTypes;

import java.util.Map;

public class VineryFabricLike {
    public static void init() {
        Vinery.init();
        VineryBoatTypes.init();

        FabricLoader.getInstance().getModContainer(Vinery.MODID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("bushy_leaves"), container, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("apple_leaves"), container, ResourcePackActivationType.NORMAL);
        });



    }
    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> TRADES = new Int2ObjectOpenHashMap<>(Map.of(1, new VillagerTrades.ItemListing[] {
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.RED_GRAPE_SEEDS.get(), 1, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.WHITE_GRAPE_SEEDS.get(), 1, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.TAIGA_RED_GRAPE_SEEDS, 1, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS, 1, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS, 1, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS, 1, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS, 1, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.JUNGLE_WHITE_GRAPE, 1, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.CHERRY_SAPLING.get(), 3, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.OLD_CHERRY_SAPLING.get(), 5, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.RED_GRAPE.get(), 2, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.RED_GRAPEJUICE_WINE_BOTTLE.get(), 4, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.WHITE_GRAPEJUICE_WINE_BOTTLE.get(), 4, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.SAVANNA_RED_GRAPEJUICE_BOTTLE, 4, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.TAIGA_WHITE_GRAPEJUICE_BOTTLE, 4, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.JUNGLE_RED_GRAPEJUICE_BOTTLE, 4, 1, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.COARSE_DIRT_SLAB.get(), 1, 3, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.GRASS_SLAB.get(), 1, 3, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.CHERRY_PLANKS.get(), 3, 4, 8, 1),
            new VillagerTrades.ItemsForEmeralds(ObjectRegistry.CHERRY_WINE.get(), 1, 1, 8, 1)
    }));
}
