package satisfyu.vinery;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.fuel.FuelRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.util.boat.impl.TerraformBoatInitializer;
import satisfyu.vinery.util.boat.impl.TerraformBoatTrackedData;
import satisfyu.vinery.world.VineryFeatures;

public class Vinery {
	public static final String MODID = "vinery";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static final CreativeModeTab VINERY_TAB = CreativeTabRegistry.create(new VineryIdentifier("vinery_tab"),
			() -> new ItemStack(ItemRegistry.RED_GRAPE.get()));

	public static void commonForge() {
		VineryCompostableItems.init();
		VineryFlammableBlocks.init();
		FuelRegistry.register(300, BlockRegistry.CHERRY_FENCE.get(), BlockRegistry.CHERRY_FENCE_GATE.get(),
				BlockRegistry.STACKABLE_LOG.get(), BlockRegistry.FERMENTATION_BARREL.get());
		AxeItemHooks.addStrippable(BlockRegistry.CHERRY_LOG.get(), BlockRegistry.STRIPPED_CHERRY_LOG.get());
		AxeItemHooks.addStrippable(BlockRegistry.CHERRY_WOOD.get(), BlockRegistry.STRIPPED_CHERRY_WOOD.get());
		AxeItemHooks.addStrippable(BlockRegistry.OLD_CHERRY_LOG.get(), BlockRegistry.STRIPPED_OLD_CHERRY_LOG.get());
		AxeItemHooks.addStrippable(BlockRegistry.OLD_CHERRY_WOOD.get(), BlockRegistry.STRIPPED_OLD_CHERRY_WOOD.get());
	}

	public static void init() {
		commonForge();
		initForge();
	}

	public static void initForge() {
		BlockRegistry.init();
		ItemRegistry.init();
		VineryEffects.init();
		VineryEntites.init();
		VineryRecipeTypes.init();
		VineryBlockEntityTypes.init();
		VineryScreenHandlerTypes.init();
		VineryBoatTypes.init();
		VineryStorageTypes.init();
		VineryFeatures.init();
		VinerySoundEvents.init();
		TerraformBoatInitializer.init();
		TerraformBoatTrackedData.register();
	}
}
