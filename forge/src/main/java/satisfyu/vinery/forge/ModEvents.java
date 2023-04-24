package satisfyu.vinery.forge;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.forge.registry.VineryForgeVillagers;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.VineryVillagers;

import java.util.List;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Vinery.MODID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event){
            if(event.getType().equals(VineryForgeVillagers.WINEMAKER.get())){
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

                List<VillagerTrades.ItemListing> level1 = trades.get(1);
                level1.add(new VineryVillagers.BuyForOneEmeraldFactory(ObjectRegistry.RED_GRAPE.get(), 5, 4, 5));
                level1.add(new VineryVillagers.BuyForOneEmeraldFactory(ObjectRegistry.WHITE_GRAPE.get(), 5, 4, 5));
                level1.add(new VineryVillagers.SellItemFactory(ObjectRegistry.RED_GRAPE_SEEDS.get(), 2, 1, 5));
                level1.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WHITE_GRAPE_SEEDS.get(), 2, 1, 5));

                List<VillagerTrades.ItemListing> level2 = trades.get(2);
                level2.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WINE_BOTTLE.get(), 1, 2, 7));

                List<VillagerTrades.ItemListing> level3 = trades.get(3);
                level3.add(new VineryVillagers.SellItemFactory(ObjectRegistry.COOKING_POT.get(), 3, 1, 10));
                level3.add(new VineryVillagers.SellItemFactory(ObjectRegistry.FLOWER_BOX.get(), 3, 1, 10));
                level3.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WHITE_GRAPE_CRATE.get(), 7, 1, 10));
                level3.add(new VineryVillagers.SellItemFactory(ObjectRegistry.RED_GRAPE_CRATE.get(), 7, 1, 10));

                List<VillagerTrades.ItemListing> level4 = trades.get(4);
                level4.add(new VineryVillagers.SellItemFactory(ObjectRegistry.BASKET.get(), 4, 1, 10));
                level4.add(new VineryVillagers.SellItemFactory(ObjectRegistry.FLOWER_POT.get(), 5, 1, 10));
                level4.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WINDOW.get(), 12, 1, 10));
                level4.add(new VineryVillagers.SellItemFactory(ObjectRegistry.CHERRY_BEAM.get(), 6, 1, 10));

                List<VillagerTrades.ItemListing> level5 = trades.get(5);
                level5.add(new VineryVillagers.SellItemFactory(ObjectRegistry.WINE_BOX.get(), 10, 1, 10));
                level5.add(new VineryVillagers.SellItemFactory(ObjectRegistry.KING_DANIS_WINE.get(), 4, 1, 10));
                level5.add(new VineryVillagers.SellItemFactory(ObjectRegistry.GLOVES.get(), 12, 1, 15));
            }
        }
    }
}
