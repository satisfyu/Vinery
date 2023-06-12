package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.entity.*;

import java.util.function.Supplier;

public class VineryBlockEntityTypes {

    


    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Vinery.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY).getRegistrar();

    public static final RegistrySupplier<BlockEntityType<SmokerBlockEntity>> SMOKER_BLOCK_ENTITY = create("smoker", () -> BlockEntityType.Builder.of(SmokerBlockEntity::new, ObjectRegistry.STOVE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WinePressBlockEntity>> WINE_PRESS_BLOCK_ENTITY = create("wine_press", () -> BlockEntityType.Builder.of(WinePressBlockEntity::new, ObjectRegistry.WINE_PRESS.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BARREL_ENTITY = create("fermentation_barrel", () -> BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new, ObjectRegistry.FERMENTATION_BARREL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WineRackBlockEntity>> WINE_RACK_ENTITY = create("wine_rack", () -> BlockEntityType.Builder.of(WineRackBlockEntity::new, ObjectRegistry.WINE_RACK_3.get(), ObjectRegistry.WINE_RACK_5.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<WineRackStorageBlockEntity>> WINE_RACK_STORAGE_ENTITY = create("wine_rack_storage", () -> BlockEntityType.Builder.of(WineRackStorageBlockEntity::new, ObjectRegistry.WINE_RACK_3.get(), ObjectRegistry.WINE_RACK_5.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<FlowerBoxBlockEntity>> FLOWER_BOX_ENTITY = create("flower_box", () -> BlockEntityType.Builder.of(FlowerBoxBlockEntity::new, ObjectRegistry.FLOWER_BOX.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FlowerPotBlockEntity>> FLOWER_POT_ENTITY = create("flower_pot", () -> BlockEntityType.Builder.of(FlowerPotBlockEntity::new, ObjectRegistry.FLOWER_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WineBottleBlockEntity>> WINE_BOTTLE_ENTITY = create("wine_bottle", () -> BlockEntityType.Builder.of(WineBottleBlockEntity::new,
            ObjectRegistry.NOIR_WINE.get(), ObjectRegistry.CLARK_WINE.get(), ObjectRegistry.BOLVAR_WINE.get(), ObjectRegistry.STAL_WINE.get(), ObjectRegistry.CHERRY_WINE.get(), ObjectRegistry.KELP_CIDER.get(),
            ObjectRegistry.SOLARIS_WINE.get(), ObjectRegistry.APPLE_WINE.get(), ObjectRegistry.APPLE_CIDER.get(), ObjectRegistry.STRAD_WINE.get(), ObjectRegistry.CHENET_WINE.get(), ObjectRegistry.MELLOHI_WINE.get(),
            ObjectRegistry.KING_DANIS_WINE.get(), ObjectRegistry.MAGNETIC_WINE.get(), ObjectRegistry.CHORUS_WINE.get(), ObjectRegistry.JELLIE_WINE.get(), ObjectRegistry.AEGIS_WINE.get(), ObjectRegistry.RED_WINE.get(),
            ObjectRegistry.PRAETORIAN_WINE.get()).build(null));





    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new VineryIdentifier(path), type);
    }

    public static void init() {
        
    }
}
