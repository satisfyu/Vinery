package satisfyu.vinery.registry;


import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.api.item.TerraformBoatItemHelper;

public class VineryBoatTypes {

    public static TerraformBoatType CHERRY;

    public static void init() {
        RegistrySupplier<Item> cherryBoat = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "cherry_boat", () -> CHERRY, false, Vinery.VINERY_TAB);
        RegistrySupplier<Item> cherryChestBoat = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "cherry_chest_boat", () -> CHERRY, true, Vinery.VINERY_TAB);

        CHERRY = new TerraformBoatType.Builder().item(cherryBoat).chestItem(cherryChestBoat).build();
        TerraformBoatTypeRegistry.register(new VineryIdentifier("cherry"), CHERRY);
    }
}
