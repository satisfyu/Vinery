package satisfyu.vinery.registry;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.level.entity.trade.TradeRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.Vinery;
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

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.function.Supplier;

public class VineryVillagers {
    private static final Registrar<PoiType> POI_TYPES = Vinery.REGISTRIES.get(Registries.POINT_OF_INTEREST_TYPE);
    private static final Registrar<VillagerProfession> VILLAGER_PROFESSIONS = Vinery.REGISTRIES.get(Registries.VILLAGER_PROFESSION);





    public static final PoiType WINEMAKER_POI_TYPE = new PoiType(ImmutableSet.copyOf(ObjectRegistry.WINE_PRESS.get().getStateDefinition().getPossibleStates()), 1, 1);

    public static final RegistrySupplier<PoiType> WINEMAKER_POI = POI_TYPES.register(new VineryIdentifier("winemaker_poi"), () ->
            WINEMAKER_POI_TYPE);

    public static final RegistrySupplier<VillagerProfession> WINEMAKER = VILLAGER_PROFESSIONS.register(new VineryIdentifier("winemaker"), () ->
            new VillagerProfession("winemaker", x -> x.value() == WINEMAKER_POI.get(), x -> x.value() == WINEMAKER_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_FARMER));


    public static void init() {

        TradeRegistry.registerVillagerTrade(WINEMAKER.get(), 1,
            new BuyForOneEmeraldFactory(ObjectRegistry.RED_GRAPE.get(), 15, 4, 5),
            new BuyForOneEmeraldFactory(ObjectRegistry.WHITE_GRAPE.get(), 15, 4, 5),
            new SellItemFactory(ObjectRegistry.RED_GRAPE_SEEDS.get(), 2, 1, 5),
            new SellItemFactory(ObjectRegistry.WHITE_GRAPE_SEEDS.get(), 2, 1, 5)
        );
        TradeRegistry.registerVillagerTrade(WINEMAKER.get(), 2,
            new SellItemFactory(ObjectRegistry.WINE_BOTTLE.get(), 1, 2, 7)
        );
        TradeRegistry.registerVillagerTrade(WINEMAKER.get(), 3,
            new SellItemFactory(ObjectRegistry.COOKING_POT.get(), 3, 1, 10),
            new SellItemFactory(ObjectRegistry.FLOWER_BOX.get(), 3, 1, 10),
            new SellItemFactory(ObjectRegistry.WHITE_GRAPE_CRATE.get(), 7, 1, 10),
            new SellItemFactory(ObjectRegistry.RED_GRAPE_CRATE.get(), 7, 1, 10)
        );
        TradeRegistry.registerVillagerTrade(WINEMAKER.get(), 4,
            new SellItemFactory(ObjectRegistry.BASKET.get(), 4, 1, 10),
            new SellItemFactory(ObjectRegistry.FLOWER_POT.get(), 5, 1, 10),
            new SellItemFactory(ObjectRegistry.WINDOW.get(), 12, 1, 10),
            new SellItemFactory(ObjectRegistry.CHERRY_BEAM.get(), 6, 1, 10)
        );
        TradeRegistry.registerVillagerTrade(WINEMAKER.get(), 5,
            new SellItemFactory(ObjectRegistry.WINE_BOX.get(), 10, 1, 10),
            new SellItemFactory(ObjectRegistry.KING_DANIS_WINE.get(), 4, 1, 10),
            new SellItemFactory(ObjectRegistry.GLOVES.get(), 12, 1, 15)
        );
        register(new VineryIdentifier("winemaker_poi"), WINEMAKER_POI_TYPE);
    }

    public static void register(ResourceLocation id, PoiType poiType) {
        var key = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, id);
        poiType.matchingStates().forEach(state -> {
            Holder<PoiType> replaced = PoiTypes.TYPE_BY_STATE.put(state, BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolderOrThrow(key));
            if (replaced != null) {
                throw Util.pauseInIde(new IllegalStateException(String.format("%s is defined in too many tags", state)));
            }
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