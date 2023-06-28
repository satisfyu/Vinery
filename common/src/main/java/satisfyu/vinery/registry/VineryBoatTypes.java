package satisfyu.vinery.registry;


import de.cristelknight.doapi.DoApiExpectPlatform;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import de.cristelknight.doapi.terraform.boat.item.TerraformBoatItemHelper;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;

public class VineryBoatTypes {

    public static ResourceLocation CHERRY_BOAT_TYPE = new VineryIdentifier("cherry");


    public static void init() {
        RegistrySupplier<Item> cherryBoat = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "cherry_boat", CHERRY_BOAT_TYPE, false);
        RegistrySupplier<Item> cherryChestBoat = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "cherry_chest_boat", CHERRY_BOAT_TYPE, true);

        DoApiExpectPlatform.registerBoatType(CHERRY_BOAT_TYPE, new TerraformBoatType.Builder().item(cherryBoat).chestItem(cherryChestBoat).build());
    }
}
