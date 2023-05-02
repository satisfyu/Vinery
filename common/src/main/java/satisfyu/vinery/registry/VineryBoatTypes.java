package satisfyu.vinery.registry;


import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.api.item.CustomBoatItemHelper;

public class VineryBoatTypes {

    public static final ResourceLocation CHERRY_BOAT_ID = new ResourceLocation(Vinery.MODID, "cherry_boat");
    public static final ResourceLocation CHERRY_CHEST_BOAT_ID = new ResourceLocation(Vinery.MODID, "cherry_chest_boat");

    public static final RegistrySupplier<Item> CHERRY_BOAT = CustomBoatItemHelper.registerBoatItem(CHERRY_BOAT_ID, CHERRY_BOAT_ID, false);
    public static final RegistrySupplier<Item> CHERRY_CHEST_BOAT = CustomBoatItemHelper.registerBoatItem(CHERRY_CHEST_BOAT_ID, CHERRY_BOAT_ID, true);

    public static TerraformBoatType CHERRY;

    public static void initItems(){

    }

    public static void init() {
        CHERRY = new TerraformBoatType.Builder().item(CHERRY_BOAT.get()).chestItem(CHERRY_CHEST_BOAT.get()).planks(ObjectRegistry.CHERRY_PLANKS.get().asItem()).build();
        TerraformBoatTypeRegistry.register(CHERRY_BOAT_ID, CHERRY);

        CustomBoatItemHelper.registerBoatDispenserBehavior(CHERRY_BOAT.get(), CHERRY_BOAT_ID, false);
        CustomBoatItemHelper.registerBoatDispenserBehavior(CHERRY_CHEST_BOAT.get(), CHERRY_BOAT_ID, true);
    }
}
