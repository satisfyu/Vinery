package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.entity.*;

import java.util.function.Supplier;

public class VineryBlockEntityTypes {

    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Vinery.MODID, Registries.BLOCK_ENTITY_TYPE).getRegistrar();

    public static final RegistrySupplier<BlockEntityType<WinePressBlockEntity>> WINE_PRESS_BLOCK_ENTITY = create("wine_press", () -> BlockEntityType.Builder.of(WinePressBlockEntity::new, ObjectRegistry.WINE_PRESS.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BARREL_ENTITY = create("fermentation_barrel", () -> BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new, ObjectRegistry.FERMENTATION_BARREL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WineRackBlockEntity>> WINE_RACK_ENTITY = create("wine_rack", () -> BlockEntityType.Builder.of(WineRackBlockEntity::new, ObjectRegistry.WINE_RACK_3.get(), ObjectRegistry.WINE_RACK_5.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<WineRackStorageBlockEntity>> WINE_RACK_STORAGE_ENTITY = create("wine_rack_storage", () -> BlockEntityType.Builder.of(WineRackStorageBlockEntity::new, ObjectRegistry.WINE_RACK_3.get(), ObjectRegistry.WINE_RACK_5.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<FlowerBoxBlockEntity>> FLOWER_BOX_ENTITY = create("flower_box", () -> BlockEntityType.Builder.of(FlowerBoxBlockEntity::new, ObjectRegistry.FLOWER_BOX.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FlowerPotBlockEntity>> FLOWER_POT_ENTITY = create("flower_pot", () -> BlockEntityType.Builder.of(FlowerPotBlockEntity::new, ObjectRegistry.FLOWER_POT.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new VineryIdentifier(path), type);
    }

    public static void init() {
        
    }
}
