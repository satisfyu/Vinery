package satisfyu.vinery.registry;


import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.api.item.TerraformBoatItemHelper;

import java.util.function.Supplier;

public class VineryBoatTypes {



    public static final ResourceLocation CHERRY_BOAT_ID = new ResourceLocation(Vinery.MODID, "cherry_boat");
    public static final ResourceLocation CHERRY_CHEST_BOAT_ID = new ResourceLocation(Vinery.MODID, "cherry_chest_boat");

    private static final ResourceKey<TerraformBoatType> CHERRY_BOAT_KEY = TerraformBoatTypeRegistry.createKey(CHERRY_BOAT_ID);

    public static final RegistrySupplier<Item> CHERRY_BOAT = TerraformBoatItemHelper.registerBoatItem(CHERRY_BOAT_ID, CHERRY_BOAT_KEY, false);
    public static final RegistrySupplier<Item> CHERRY_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(CHERRY_CHEST_BOAT_ID, CHERRY_BOAT_KEY, true);

    public static Supplier<TerraformBoatType> CHERRY;

    public static void init() {

        CHERRY = () -> new TerraformBoatType.Builder().item(CHERRY_BOAT.get()).chestItem(CHERRY_CHEST_BOAT.get()).planks(ObjectRegistry.CHERRY_PLANKS.get().asItem()).build();
        Vinery.LOGGER.error("Registering CHERRY boat type");
        TerraformBoatTypeRegistry.INSTANCE.register(CHERRY_BOAT_ID, CHERRY);

        TerraformBoatItemHelper.registerBoatDispenserBehavior(CHERRY_BOAT.get(), CHERRY_BOAT_KEY, false);
        TerraformBoatItemHelper.registerBoatDispenserBehavior(CHERRY_CHEST_BOAT.get(), CHERRY_BOAT_KEY, true);
    }
    public static void initItems(){

    }
}
