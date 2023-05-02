package satisfyu.vinery.util.boat.api.item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.impl.item.TerraformBoatDispenserBehavior;
import satisfyu.vinery.util.boat.impl.item.TerraformBoatItem;

public final class TerraformBoatItemHelper {
	private static final CreativeModeTab DEFAULT_ITEM_GROUP = CreativeModeTab.TAB_TRANSPORTATION;

	private static Map<RegistrySupplier<? extends ItemLike>, TerraformBoatDispenserBehavior> DISPENSER_BEHAVIOURS = new HashMap<>();

	private TerraformBoatItemHelper() {
		return;
	}

	public static RegistrySupplier<Item> registerBoatItem(DeferredRegister<Item> register, String name, Supplier<TerraformBoatType> boatSupplier, boolean chest) {
		return registerBoatItem(register, name, boatSupplier, chest, DEFAULT_ITEM_GROUP);
	}

	public static RegistrySupplier<Item> registerBoatItem(DeferredRegister<Item> register, String name, Supplier<TerraformBoatType> boatSupplier, boolean chest, CreativeModeTab group) {
		return registerBoatItem(register, name, boatSupplier, chest, new Item.Properties().stacksTo(1).tab(group));
	}


	public static RegistrySupplier<Item> registerBoatItem(DeferredRegister<Item> register, String name, Supplier<TerraformBoatType> boatSupplier, boolean chest, Item.Properties settings) {
		RegistrySupplier<Item> item = register.register(name, () -> new TerraformBoatItem(boatSupplier, chest, settings));

		registerBoatDispenserBehavior(item, boatSupplier, chest);
		return item;
	}

	public static <T extends ItemLike> void registerBoatDispenserBehavior(RegistrySupplier<T> item, Supplier<TerraformBoatType> boatSupplier, boolean chest) {
		DISPENSER_BEHAVIOURS.put(item, new TerraformBoatDispenserBehavior(boatSupplier, chest));
	}

	public static void registerDispenserBehaviours(){
		DISPENSER_BEHAVIOURS.forEach((item, dispenserBehavior) -> DispenserBlock.registerBehavior(item.get(), dispenserBehavior));
	}
}
