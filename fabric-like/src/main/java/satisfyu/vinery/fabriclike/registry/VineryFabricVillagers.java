package satisfyu.vinery.fabriclike.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.registry.BlockRegistry;
import satisfyu.vinery.registry.ItemRegistry;
import satisfyu.vinery.util.VineryVillagerUtil;

public class VineryFabricVillagers {
	private static final VineryIdentifier WINEMAKER_POI_IDENTIFIER = new VineryIdentifier("winemaker_poi");

	public static final PoiType WINEMAKER_POI = PointOfInterestHelper.register(WINEMAKER_POI_IDENTIFIER, 1, 12,
			BlockRegistry.WINE_PRESS.get());

	public static final VillagerProfession WINEMAKER = Registry.register(Registry.VILLAGER_PROFESSION,
			new ResourceLocation("vinery", "winemaker"), VillagerProfessionBuilder.create().id(
					new ResourceLocation("vinery", "winemaker")).workstation(WINEMAKER_POI).build());

	public static void init() {
		TradeOfferHelper.registerVillagerOffers(WINEMAKER, 1, factories -> {
			factories.add(new VineryVillagerUtil.BuyForOneEmeraldFactory(ItemRegistry.RED_GRAPE.get(), 15, 4, 5));
			factories.add(new VineryVillagerUtil.BuyForOneEmeraldFactory(ItemRegistry.WHITE_GRAPE.get(), 15, 4, 5));
			factories.add(new VineryVillagerUtil.SellItemFactory(ItemRegistry.RED_GRAPE_SEEDS.get(), 2, 1, 5));
			factories.add(new VineryVillagerUtil.SellItemFactory(ItemRegistry.WHITE_GRAPE_SEEDS.get(), 2, 1, 5));
		});
		TradeOfferHelper.registerVillagerOffers(WINEMAKER, 2, factories -> {
			factories.add(new VineryVillagerUtil.SellItemFactory(ItemRegistry.WINE_BOTTLE.get(), 1, 2, 7));
		});
		TradeOfferHelper.registerVillagerOffers(WINEMAKER, 3, factories -> {
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.COOKING_POT.get(), 3, 1, 10));
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.FLOWER_BOX.get(), 3, 1, 10));
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.WHITE_GRAPE_CRATE.get(), 7, 1, 10));
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.RED_GRAPE_CRATE.get(), 7, 1, 10));
		});
		TradeOfferHelper.registerVillagerOffers(WINEMAKER, 4, factories -> {
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.BASKET.get(), 4, 1, 10));
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.FLOWER_POT.get(), 5, 1, 10));
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.WINDOW.get(), 12, 1, 10));
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.CHERRY_BEAM.get(), 6, 1, 10));
		});
		TradeOfferHelper.registerVillagerOffers(WINEMAKER, 5, factories -> {
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.WINE_BOX.get(), 10, 1, 10));
			factories.add(new VineryVillagerUtil.SellItemFactory(BlockRegistry.KING_DANIS_WINE.get(), 4, 1, 10));
			factories.add(new VineryVillagerUtil.SellItemFactory(ItemRegistry.GLOVES.get(), 12, 1, 15));
		});
	}
}
