package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryExpectPlatform;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.entity.*;

import java.util.function.Supplier;

public class VineryBlockEntityTypes {
	private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Vinery.MODID,
			Registry.BLOCK_ENTITY_TYPE_REGISTRY).getRegistrar();

	private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
		return BLOCK_ENTITY_TYPES.register(new VineryIdentifier(path), type);
	}

	public static final RegistrySupplier<BlockEntityType<WoodFiredOvenBlockEntity>> WOOD_FIRED_OVEN_BLOCK_ENTITY = create(
			"wood_fired_oven",
			() -> BlockEntityType.Builder.of(WoodFiredOvenBlockEntity::new, BlockRegistry.WOOD_FIRED_OVEN.get())
					.build(null));

	public static final RegistrySupplier<BlockEntityType<CookingPotEntity>> COOKING_POT_BLOCK_ENTITY = create(
			"cooking_pot",
			() -> BlockEntityType.Builder.of(CookingPotEntity::new, BlockRegistry.COOKING_POT.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<WinePressBlockEntity>> WINE_PRESS_BLOCK_ENTITY = create(
			"wine_press",
			() -> BlockEntityType.Builder.of(WinePressBlockEntity::new, BlockRegistry.WINE_PRESS.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BARREL_ENTITY = create(
			"fermentation_barrel", () -> BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new,
					BlockRegistry.FERMENTATION_BARREL.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<WineRackBlockEntity>> WINE_RACK_ENTITY = create("wine_rack",
			() -> BlockEntityType.Builder.of(WineRackBlockEntity::new, BlockRegistry.WINE_RACK_3.get(),
					BlockRegistry.WINE_RACK_5.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<WineRackStorageBlockEntity>> WINE_RACK_STORAGE_ENTITY = create(
			"wine_rack_storage",
			() -> BlockEntityType.Builder.of(WineRackStorageBlockEntity::new, BlockRegistry.WINE_RACK_3.get(),
					BlockRegistry.WINE_RACK_5.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<FlowerBoxBlockEntity>> FLOWER_BOX_ENTITY = create("flower_box",
			() -> BlockEntityType.Builder.of(FlowerBoxBlockEntity::new, BlockRegistry.FLOWER_BOX.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<FlowerPotBlockEntity>> FLOWER_POT_ENTITY = create("flower_pot",
			() -> BlockEntityType.Builder.of(FlowerPotBlockEntity::new, BlockRegistry.FLOWER_POT.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<WineBottleBlockEntity>> WINE_BOTTLE_ENTITY = create(
			"wine_bottle", () -> BlockEntityType.Builder.of(WineBottleBlockEntity::new, BlockRegistry.NOIR_WINE.get(),
					BlockRegistry.CLARK_WINE.get(), BlockRegistry.BOLVAR_WINE.get(), BlockRegistry.STAL_WINE.get(),
					BlockRegistry.CHERRY_WINE.get(), BlockRegistry.KELP_CIDER.get(),
					BlockRegistry.SOLARIS_WINE.get(), BlockRegistry.APPLE_WINE.get(),
					BlockRegistry.APPLE_CIDER.get(), BlockRegistry.STRAD_WINE.get(), BlockRegistry.CHENET_WINE.get(),
					BlockRegistry.MELLOHI_WINE.get(), BlockRegistry.KING_DANIS_WINE.get(),
					BlockRegistry.MAGNETIC_WINE.get(), BlockRegistry.CHORUS_WINE.get(),
					BlockRegistry.JELLIE_WINE.get(), BlockRegistry.AEGIS_WINE.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> STORAGE_ENTITY = create("storage",
			() -> BlockEntityType.Builder.of(StorageBlockEntity::new, VineryExpectPlatform.getBlocksForStorage())
					.build(null));

	public static void init() {
	}
}
