package satisfyu.vinery.registry;


import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryExpectPlatform;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.item.TerraformBoatItemHelper;

import java.util.function.Supplier;

public class VineryBoatTypes {



    public static final ResourceLocation CHERRY_BOAT_ID = new ResourceLocation(Vinery.MODID, "cherry_boat");
    public static final ResourceLocation CHERRY_CHEST_BOAT_ID = new ResourceLocation(Vinery.MODID, "cherry_chest_boat");

    public static final RegistrySupplier<Item> CHERRY_BOAT = TerraformBoatItemHelper.registerBoatItem(CHERRY_BOAT_ID, false);
    public static final RegistrySupplier<Item> CHERRY_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(CHERRY_CHEST_BOAT_ID, true);

    public static Supplier<TerraformBoatType> CHERRY = () -> new TerraformBoatType.Builder().item(CHERRY_BOAT_ID).chestItem(CHERRY_CHEST_BOAT_ID).planks(new VineryIdentifier("cherry_planks")).build();

    public static void init() {
        VineryExpectPlatform.register(CHERRY_BOAT_ID, CHERRY);

        if(!Platform.isForge()) dispenser();
    }

    public static void dispenser(){
        final ResourceKey<TerraformBoatType> CHERRY_BOAT_KEY = VineryExpectPlatform.createKey(CHERRY_BOAT_ID);
        TerraformBoatItemHelper.registerBoatDispenserBehavior(CHERRY_BOAT.get(), CHERRY_BOAT_KEY, false);
        TerraformBoatItemHelper.registerBoatDispenserBehavior(CHERRY_CHEST_BOAT.get(), CHERRY_BOAT_KEY, true);
    }
    public static void initItems(){

    }
}
