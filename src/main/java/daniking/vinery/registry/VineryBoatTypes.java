package daniking.vinery.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class VineryBoatTypes {

    public static TerraformBoatType cherry;

    public static void init() {
        Item cherryBoat = TerraformBoatItemHelper.registerBoatItem(new VineryIdentifier("cherry_boat"), () -> cherry, Vinery.CREATIVE_TAB);
        cherry = new TerraformBoatType.Builder().item(cherryBoat).build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new VineryIdentifier("cherry"), cherry);
    }
}
