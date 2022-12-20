package daniking.vinery.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class VineryBoatTypes {

    public static TerraformBoatType CHERRY;

    public static void init() {
        Item cherryBoat = TerraformBoatItemHelper.registerBoatItem(new VineryIdentifier("cherry_boat"), () -> CHERRY, false);
        Item cherryChestBoat = TerraformBoatItemHelper.registerBoatItem(new VineryIdentifier("cherry_chest_boat"), () -> CHERRY, true);
        CHERRY = new TerraformBoatType.Builder().item(cherryBoat).chestItem(cherryChestBoat).planks(ObjectRegistry.CHERRY_PLANKS.asItem()).build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new VineryIdentifier("cherry"), CHERRY);
    }
}
