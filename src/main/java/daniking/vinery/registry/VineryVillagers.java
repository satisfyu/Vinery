package daniking.vinery.registry;

import daniking.vinery.VineryIdentifier;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.advancement.criterion.VillagerTradeCriterion;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;

public class VineryVillagers {
    private static final VineryIdentifier WINEMAKER_POI_IDENTIFIER = new VineryIdentifier("winemaker_poi");
    public static final PointOfInterestType WINEMAKER_POI = PointOfInterestHelper.register(WINEMAKER_POI_IDENTIFIER, 1, 12, ObjectRegistry.WINE_PRESS);
    public static final VillagerProfession WINEMAKER = Registry.register(Registry.VILLAGER_PROFESSION, new Identifier("vinery", "winemaker"), VillagerProfessionBuilder.create().id(new Identifier("vinery", "winemaker")).workstation(RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, WINEMAKER_POI_IDENTIFIER)).build());

    public static void init() {
        TradeOfferHelper.registerVillagerOffers(WINEMAKER, 1, factories -> {
            factories.add(new BuyForOneEmeraldFactory(ObjectRegistry.RED_GRAPE, 15, 4, 5));
            factories.add(new BuyForOneEmeraldFactory(ObjectRegistry.WHITE_GRAPE, 15, 4, 5));
            factories.add(new SellItemFactory(ObjectRegistry.RED_GRAPE_SEEDS, 2, 1, 5));
            factories.add(new SellItemFactory(ObjectRegistry.WHITE_GRAPE_SEEDS, 2, 1, 5));
        });
        TradeOfferHelper.registerVillagerOffers(WINEMAKER, 2, factories -> {
            factories.add(new SellItemFactory(ObjectRegistry.WINE_BOTTLE, 1, 2, 7));
        });
        TradeOfferHelper.registerVillagerOffers(WINEMAKER, 3, factories -> {
            factories.add(new SellItemFactory(ObjectRegistry.COOKING_POT, 3, 1, 10));
            factories.add(new SellItemFactory(ObjectRegistry.FLOWER_BOX, 3, 1, 10));
            factories.add(new SellItemFactory(ObjectRegistry.WHITE_GRAPE_CRATE, 7, 1, 10));
            factories.add(new SellItemFactory(ObjectRegistry.RED_GRAPE_CRATE, 7, 1, 10));

        });
        TradeOfferHelper.registerVillagerOffers(WINEMAKER, 4, factories -> {
            factories.add(new SellItemFactory(ObjectRegistry.BASKET, 4, 1, 10));
            factories.add(new SellItemFactory(ObjectRegistry.FLOWER_POT, 5, 1, 10));
            factories.add(new SellItemFactory(ObjectRegistry.WINDOW, 12, 1, 10));
            factories.add(new SellItemFactory(ObjectRegistry.CHERRY_BEAM, 6, 1, 10));


        });
        TradeOfferHelper.registerVillagerOffers(WINEMAKER, 5, factories -> {
            factories.add(new SellItemFactory(ObjectRegistry.WINE_BOX, 10, 1, 10));
            factories.add(new SellItemFactory(ObjectRegistry.KING_DANIS_WINE, 4, 1, 10));
            factories.add(new SellItemFactory(ObjectRegistry.GLOVES, 12, 1, 15));


        });

    }

    static class BuyForOneEmeraldFactory implements TradeOffers.Factory {
        private final Item buy;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyForOneEmeraldFactory(ItemConvertible item, int price, int maxUses, int experience) {
            this.buy = item.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        @Override
        public TradeOffer create(Entity entity, Random random) {
            ItemStack itemStack = new ItemStack(this.buy, this.price);
            return new TradeOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    static class SellItemFactory implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemFactory(Block block, int price, int count, int maxUses, int experience) {
            this(new ItemStack(block), price, count, maxUses, experience);
        }

        public SellItemFactory(Block item, int price, int count, int experience) {
            this(new ItemStack(item), price, count, 12, experience);
        }

        public SellItemFactory(Item item, int price, int count, int experience) {
            this(new ItemStack(item), price, count, 12, experience);
        }

        public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
            this(new ItemStack(item), price, count, maxUses, experience);
        }

        public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience) {
            this(stack, price, count, maxUses, experience, 0.05F);
        }

        public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(
                    new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier
            );
        }
    }
}