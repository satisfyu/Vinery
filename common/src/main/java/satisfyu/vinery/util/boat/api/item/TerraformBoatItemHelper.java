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


public final class TerraformBoatItemHelper {
	private TerraformBoatItemHelper() {
		return;
	}


	public static RegistrySupplier<Item> registerBoatItem(ResourceLocation id, ResourceKey<TerraformBoatType> boatKey, boolean chest) {
		return registerBoatItem(id, boatKey, chest, new Item.Properties().stacksTo(1));
	}
	public static RegistrySupplier<Item> registerBoatItem(ResourceLocation id, ResourceKey<TerraformBoatType> boatKey, boolean chest, Item.Properties settings) {
		RegistrySupplier<Item> item = ObjectRegistry.ITEMS.register(id, () -> new TerraformBoatItem(boatKey, chest, settings));
		registerBoatDispenserBehavior(item.get(), boatKey, chest);
		return item;
	}

	public static void registerBoatDispenserBehavior(ItemLike item, ResourceKey<TerraformBoatType> boatKey, boolean chest) {
		DispenserBlock.registerBehavior(item, new TerraformBoatDispenserBehavior(boatKey, chest));
	}
}
