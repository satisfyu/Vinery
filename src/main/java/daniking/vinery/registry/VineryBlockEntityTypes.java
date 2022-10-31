package daniking.vinery.registry;

import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.entity.CookingPotEntity;
import daniking.vinery.block.entity.FermentationBarrelBlockEntity;
import daniking.vinery.block.entity.WoodFiredOvenBlockEntity;
import daniking.vinery.block.entity.WineRackBlockEntity;
import daniking.vinery.block.entity.chair.ChairEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class VineryBlockEntityTypes {

    private static final Map<Identifier, BlockEntityType<?>> BLOCK_ENTITY_TYPES = new HashMap<>();

    public static final BlockEntityType<WoodFiredOvenBlockEntity> WOOD_FIRED_OVEN_BLOCK_ENTITY = create("wood_fired_oven", FabricBlockEntityTypeBuilder.create(WoodFiredOvenBlockEntity::new, ObjectRegistry.WOOD_FIRED_OVEN).build());
    public static final BlockEntityType<CookingPotEntity> COOKING_POT_BLOCK_ENTITY = create("cooking_pot", FabricBlockEntityTypeBuilder.create(CookingPotEntity::new, ObjectRegistry.COOKING_POT).build());
    public static final BlockEntityType<FermentationBarrelBlockEntity> FERMENTATION_BARREL_ENTITY = create("fermentation_barrel", FabricBlockEntityTypeBuilder.create(FermentationBarrelBlockEntity::new, ObjectRegistry.FERMENTATION_BARREL).build());
    public static final BlockEntityType<WineRackBlockEntity> WINE_RACK_ENTITY = create("wine_rack", FabricBlockEntityTypeBuilder.create(WineRackBlockEntity::new, ObjectRegistry.WINE_RACK_3, ObjectRegistry.WINE_RACK_5).build());

    public static final EntityType<ChairEntity> CHAIR = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("chair"),
            FabricEntityTypeBuilder.<ChairEntity>create(SpawnGroup.MISC, ChairEntity::new).dimensions(EntityDimensions.fixed(0.001F, 0.001F)).build()
    );

    private static <T extends BlockEntityType<?>> T create(final String path, final T type) {
        BLOCK_ENTITY_TYPES.put(new VineryIdentifier(path), type);
        return type;
    }

    public static void init() {
        for (Map.Entry<Identifier, BlockEntityType<?>> entry : BLOCK_ENTITY_TYPES.entrySet()) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, entry.getKey(), entry.getValue());
        }
    }
}
