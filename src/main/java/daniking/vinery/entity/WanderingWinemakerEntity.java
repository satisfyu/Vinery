package daniking.vinery.entity;

import daniking.vinery.registry.ObjectRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradeOffers.SellItemFactory;
import net.minecraft.world.World;

import java.util.Map;

public class WanderingWinemakerEntity extends WanderingTraderEntity {
	public static final Int2ObjectMap<TradeOffers.Factory[]> TRADES = new Int2ObjectOpenHashMap<>(Map.of(1, new TradeOffers.Factory[] {
			new SellItemFactory(ObjectRegistry.RED_GRAPE_SEEDS, 1, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.WHITE_GRAPE_SEEDS, 1, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.CHERRY_SAPLING, 3, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.OLD_CHERRY_SAPLING, 5, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.RED_GRAPE, 2, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.RED_GRAPEJUICE_WINE_BOTTLE, 2, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.WHITE_GRAPEJUICE_WINE_BOTTLE, 2, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.COARSE_DIRT_SLAB, 1, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.RED_GRASS_FLOWER, 3, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.WHITE_GRASS_FLOWER, 3, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.PINK_GRASS_FLOWER, 3, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.GRASS_SLAB, 1, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.CHERRY_PLANKS, 2, 1, 8, 1),
			new SellItemFactory(ObjectRegistry.CHERRY_WINE, 1, 1, 8, 1)
	}));
	
	public WanderingWinemakerEntity(EntityType<? extends WanderingWinemakerEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	protected void fillRecipes() {
		if (this.offers == null) {
			this.offers = new TradeOfferList();
		}
		this.fillRecipesFromPool(this.offers, TRADES.get(1), 8);
	}
	
}