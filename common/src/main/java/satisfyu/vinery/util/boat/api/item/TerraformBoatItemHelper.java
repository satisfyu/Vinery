package satisfyu.vinery.util.boat.api.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.impl.item.TerraformBoatDispenserBehavior;
import satisfyu.vinery.util.boat.impl.item.TerraformBoatItem;

/**
 * This class provides utilities for the {@linkplain TerraformBoatItem item forms} of {@linkplain TerraformBoatType Terraform boats},
 * such as {@linkplain #registerBoatItem(ResourceLocation, ResourceKey, boolean, Item.Properties) registering them and their dispenser behavior}.
 */
public final class TerraformBoatItemHelper {
	private TerraformBoatItemHelper() {
		return;
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item}
	 * and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 * 
	 * <p>To register a boat item and its dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), MAHOGANY_BOAT_KEY, false);
	 * }</pre>
	 * 
	 * <p>This method should be called twice for a given boat type for both boats and chest boats.
	 * 
	 * <p>This method does not define item groups for the item.
	 * 
	 * @see #registerBoatItem(ResourceLocation, ResourceKey, boolean, Item.Properties) Helper that allows specifying a custom item settings
	 * 
	 * @param id the {@linkplain ResourceLocation identifier} to register the item with 
	 * @param boatKey a {@linkplain ResourceKey registry key} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 * @param chest whether the boat contains a chest
	 */
	public static RegistrySupplier<Item> registerBoatItem(ResourceLocation id, ResourceKey<TerraformBoatType> boatKey, boolean chest) {
		return registerBoatItem(id, boatKey, chest, new Item.Properties().stacksTo(1));
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item} and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 * 
	 * <p>To register a boat item and its dispenser behavior:
	 * 
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), MAHOGANY_BOAT_KEY, false, new Item.Settings().maxCount(1));
	 * }</pre>
	 * 
	 * <p>This method should be called twice for a given boat type for both boats and chest boats.
	 * 
	 * <p>This method does not define item groups for the item.
	 * 
	 * @param id the {@linkplain ResourceLocation identifier} to register the item with
	 * @param boatKey a {@linkplain ResourceKey registry key} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 * @param chest whether the boat contains a chest
	 */
	public static RegistrySupplier<Item> registerBoatItem(ResourceLocation id, ResourceKey<TerraformBoatType> boatKey, boolean chest, Item.Properties settings) {
		RegistrySupplier<Item> item = ObjectRegistry.ITEMS.register(id, () -> new TerraformBoatItem(boatKey, chest, settings));
		registerBoatDispenserBehavior(item.get(), boatKey, chest);
		return item;
	}


	public static void registerBoatDispenserBehavior(ItemLike item, ResourceKey<TerraformBoatType> boatKey, boolean chest) {
		DispenserBlock.registerBehavior(item, new TerraformBoatDispenserBehavior(boatKey, chest));
	}
}
