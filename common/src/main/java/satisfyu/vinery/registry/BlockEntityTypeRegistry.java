package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.entity.*;

import java.util.function.Supplier;

public class BlockEntityTypeRegistry {

    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Vinery.MOD_ID, Registries.BLOCK_ENTITY_TYPE).getRegistrar();

    public static final RegistrySupplier<BlockEntityType<ApplePressBlockEntity>> APPLE_PRESS_BLOCK_ENTITY = create("apple_press", () -> BlockEntityType.Builder.of(ApplePressBlockEntity::new, ObjectRegistry.APPLE_PRESS.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BARREL_ENTITY = create("fermentation_barrel", () -> BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new, ObjectRegistry.FERMENTATION_BARREL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> WINE_RACK_STORAGE_ENTITY = create("wine_rack_storage", () -> BlockEntityType.Builder.of(StorageBlockEntity::new, ObjectRegistry.CHERRY_DRAWER.get(), ObjectRegistry.CHERRY_CABINET.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FlowerBoxBlockEntity>> FLOWER_BOX_ENTITY = create("flower_box", () -> BlockEntityType.Builder.of(FlowerBoxBlockEntity::new, ObjectRegistry.FLOWER_BOX.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FlowerPotBlockEntity>> FLOWER_POT_ENTITY = create("flower_pot", () -> BlockEntityType.Builder.of(FlowerPotBlockEntity::new, ObjectRegistry.FLOWER_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BasketBlockEntity>> BASKET_ENTITY = create("basket", () -> BlockEntityType.Builder.of(BasketBlockEntity::new, ObjectRegistry.BASKET.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new VineryIdentifier(path), type);
    }

    public static void init() {
        
    }
}
