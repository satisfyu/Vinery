package satisfyu.vinery.util.boat.api.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.impl.item.TerraformBoatDispenserBehavior;
import satisfyu.vinery.util.boat.impl.item.TerraformBoatItem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class TerraformBoatItemHelper {
	private static final Map<RegistrySupplier<? extends ItemLike>, TerraformBoatDispenserBehavior> DISPENSER_BEHAVIOURS = new HashMap<>();

	public static RegistrySupplier<Item> registerBoatItem(DeferredRegister<Item> register, String name, Supplier<TerraformBoatType> boatSupplier, boolean chest, CreativeModeTab group) {
		return registerBoatItem(register, name, boatSupplier, chest, new Item.Properties().stacksTo(1).tab(group));
	}

	public static RegistrySupplier<Item> registerBoatItem(DeferredRegister<Item> register, String name, Supplier<TerraformBoatType> boatSupplier, boolean chest, Item.Properties settings) {
		RegistrySupplier<Item> item = register.register(name,
				() -> new TerraformBoatItem(boatSupplier, settings));
		registerBoatDispenserBehavior(item, boatSupplier);
		return item;
	}

	public static <T extends ItemLike> void registerBoatDispenserBehavior(RegistrySupplier<T> item, Supplier<TerraformBoatType> boatSupplier) {
		DISPENSER_BEHAVIOURS.put(item, new TerraformBoatDispenserBehavior(boatSupplier));
	}
}
