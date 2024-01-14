package satisfyu.vinery.forge;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.forge.registry.VineryForgeVillagers;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.MobEffectRegistry;
import satisfyu.vinery.util.VillagerUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModEvents {

    @Mod.EventBusSubscriber(MOD_ID = Vinery.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event) {
            if (event.getType().equals(VineryForgeVillagers.WINEMAKER.get())) {
                Map<Integer, List<VillagerTrades.ItemListing>> trades = new HashMap<>(event.getTrades());

                List<VillagerTrades.ItemListing> level1 = trades.get(1);
                level1.add(new VillagerUtil.BuyForOneEmeraldFactory(ObjectRegistry.RED_GRAPE.get(), 5, 4, 5));
                level1.add(new VillagerUtil.BuyForOneEmeraldFactory(ObjectRegistry.WHITE_GRAPE.get(), 5, 4, 5));
                level1.add(new VillagerUtil.SellItemFactory(ObjectRegistry.RED_GRAPE_SEEDS.get(), 2, 1, 5));
                level1.add(new VillagerUtil.SellItemFactory(ObjectRegistry.WHITE_GRAPE_SEEDS.get(), 2, 1, 5));

                List<VillagerTrades.ItemListing> level2 = trades.get(2);
                level2.add(new VillagerUtil.SellItemFactory(ObjectRegistry.WINE_BOTTLE.get(), 1, 2, 7));

                List<VillagerTrades.ItemListing> level3 = trades.get(3);
                level3.add(new VillagerUtil.SellItemFactory(ObjectRegistry.FLOWER_BOX.get(), 3, 1, 10));
                level3.add(new VillagerUtil.SellItemFactory(ObjectRegistry.WHITE_GRAPE_CRATE.get(), 7, 1, 10));
                level3.add(new VillagerUtil.SellItemFactory(ObjectRegistry.RED_GRAPE_CRATE.get(), 7, 1, 10));

                List<VillagerTrades.ItemListing> level4 = trades.get(4);
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.BASKET.get(), 4, 1, 10));
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.FLOWER_POT.get(), 5, 1, 10));
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.WINDOW.get(), 12, 1, 10));
                level4.add(new VillagerUtil.SellItemFactory(ObjectRegistry.CHERRY_BEAM.get(), 6, 1, 10));

                List<VillagerTrades.ItemListing> level5 = trades.get(5);
                level5.add(new VillagerUtil.SellItemFactory(ObjectRegistry.WINE_BOX.get(), 10, 1, 10));
                level5.add(new VillagerUtil.SellItemFactory(ObjectRegistry.KING_DANIS_WINE.get(), 4, 1, 10));
                level5.add(new VillagerUtil.SellItemFactory(ObjectRegistry.CALENDAR.get(), 2, 1, 15));

                event.getTrades().clear();
                event.getTrades().putAll(trades);
            }
        }

        @SubscribeEvent
        public static void experience(PlayerXpEvent.PickupXp event) {
            Player p = event.getEntity();
            if (p.hasEffect(MobEffectRegistry.EXPERIENCE_EFFECT.get())) {
                int amplifier = p.getEffect(MobEffectRegistry.EXPERIENCE_EFFECT.get()).amplifier;
                ExperienceOrb e = event.getOrb();
                int i = e.value;
                e.value = (int) (i + (i * (1 + amplifier) * 0.5));
            }
        }
    }
}
