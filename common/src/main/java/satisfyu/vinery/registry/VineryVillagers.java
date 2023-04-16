package satisfyu.vinery.registry;

import satisfyu.vinery.VineryIdentifier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class VineryVillagers {
    private static final VineryIdentifier WINEMAKER_POI_IDENTIFIER = new VineryIdentifier("winemaker_poi");
    public static final PoiType WINEMAKER_POI = PointOfInterestHelper.register(WINEMAKER_POI_IDENTIFIER, 1, 12, ObjectRegistry.WINE_PRESS);
    public static final VillagerProfession WINEMAKER = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, new ResourceLocation("vinery", "winemaker"), VillagerProfessionBuilder.create().id(new ResourceLocation("vinery", "winemaker")).workstation(ResourceKey.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE.key(), WINEMAKER_POI_IDENTIFIER)).build());

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

    static class BuyForOneEmeraldFactory implements VillagerTrades.ItemListing {
        private final Item buy;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyForOneEmeraldFactory(ItemLike item, int price, int maxUses, int experience) {
            this.buy = item.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            ItemStack itemStack = new ItemStack(this.buy, this.price);
            return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    static class SellItemFactory implements VillagerTrades.ItemListing {
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
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            return new MerchantOffer(
                    new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier
            );
        }
    }
}