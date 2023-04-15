package satisfyu.vinery.registry;

import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.entity.*;
import satisfyu.vinery.block.entity.chair.ChairEntity;
import satisfyu.vinery.util.VineryApi;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.*;

public class VineryBlockEntityTypes {

    private static final Map<ResourceLocation, BlockEntityType<?>> BLOCK_ENTITY_TYPES = new HashMap<>();

    public static final BlockEntityType<WoodFiredOvenBlockEntity> WOOD_FIRED_OVEN_BLOCK_ENTITY = create("wood_fired_oven", FabricBlockEntityTypeBuilder.create(WoodFiredOvenBlockEntity::new, ObjectRegistry.WOOD_FIRED_OVEN).build());
    public static final BlockEntityType<CookingPotEntity> COOKING_POT_BLOCK_ENTITY = create("cooking_pot", FabricBlockEntityTypeBuilder.create(CookingPotEntity::new, ObjectRegistry.COOKING_POT).build());

    public static final BlockEntityType<WinePressBlockEntity> WINE_PRESS_BLOCK_ENTITY = create("wine_press", FabricBlockEntityTypeBuilder.create(WinePressBlockEntity::new, ObjectRegistry.WINE_PRESS).build());

    public static final BlockEntityType<FermentationBarrelBlockEntity> FERMENTATION_BARREL_ENTITY = create("fermentation_barrel", FabricBlockEntityTypeBuilder.create(FermentationBarrelBlockEntity::new, ObjectRegistry.FERMENTATION_BARREL).build());
    public static final BlockEntityType<WineRackBlockEntity> WINE_RACK_ENTITY = create("wine_rack", FabricBlockEntityTypeBuilder.create(WineRackBlockEntity::new, ObjectRegistry.WINE_RACK_3, ObjectRegistry.WINE_RACK_5).build());

    public static final BlockEntityType<WineRackStorageBlockEntity> WINE_RACK_STORAGE_ENTITY = create("wine_rack_storage", FabricBlockEntityTypeBuilder.create(WineRackStorageBlockEntity::new, ObjectRegistry.WINE_RACK_3, ObjectRegistry.WINE_RACK_5).build());

    public static final BlockEntityType<FlowerBoxBlockEntity> FLOWER_BOX_ENTITY = create("flower_box", FabricBlockEntityTypeBuilder.create(FlowerBoxBlockEntity::new, ObjectRegistry.FLOWER_BOX).build());
    public static final BlockEntityType<FlowerPotBlockEntity> FLOWER_POT_ENTITY = create("flower_pot", FabricBlockEntityTypeBuilder.create(FlowerPotBlockEntity::new, ObjectRegistry.FLOWER_POT).build());
    public static final BlockEntityType<WineBottleBlockEntity> WINE_BOTTLE_ENTITY = create("wine_bottle", FabricBlockEntityTypeBuilder.create(WineBottleBlockEntity::new, ObjectRegistry.NOIR_WINE, ObjectRegistry.CLARK_WINE, ObjectRegistry.BOLVAR_WINE, ObjectRegistry.STAL_WINE, ObjectRegistry.CHERRY_WINE, ObjectRegistry.KELP_CIDER, ObjectRegistry.SOLARIS_WINE, ObjectRegistry.APPLE_WINE,
            ObjectRegistry.APPLE_CIDER, ObjectRegistry.STRAD_WINE, ObjectRegistry.CHENET_WINE, ObjectRegistry.MELLOHI_WINE,
            ObjectRegistry.KING_DANIS_WINE, ObjectRegistry.MAGNETIC_WINE, ObjectRegistry.CHORUS_WINE, ObjectRegistry.JELLIE_WINE, ObjectRegistry.AEGIS_WINE).build());


    public static Block[] b(){
        Set<Block> set = new HashSet<>();
        FabricLoader.getInstance().getEntrypointContainers("vinery", VineryApi.class).forEach(entrypoint -> {
            String modId = entrypoint.getProvider().getMetadata().getId();
            try {
                VineryApi api = entrypoint.getEntrypoint();
                api.registerBlocks(set);
            } catch (Throwable e) {
                Vinery.LOGGER.error("Mod {} provides a broken implementation of VineryApi, therefore couldn't register blocks to the Storage Block Entity", modId, e);
            }
        });
        return set.toArray(new Block[0]);
    }

    public static final BlockEntityType<StorageBlockEntity> STORAGE_ENTITY = create("storage", FabricBlockEntityTypeBuilder.create(
            StorageBlockEntity::new, b()).build());



    public static final EntityType<ChairEntity> CHAIR = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(Vinery.MODID, "chair"),
            FabricEntityTypeBuilder.<ChairEntity>create(MobCategory.MISC, ChairEntity::new).dimensions(EntityDimensions.fixed(0.001F, 0.001F)).build()
    );



    private static <T extends BlockEntityType<?>> T create(final String path, final T type) {
        BLOCK_ENTITY_TYPES.put(new VineryIdentifier(path), type);
        return type;
    }

    public static void init() {
        for (Map.Entry<ResourceLocation, BlockEntityType<?>> entry : BLOCK_ENTITY_TYPES.entrySet()) {
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, entry.getKey(), entry.getValue());
        }
    }
}
