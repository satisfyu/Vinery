package satisfyu.vinery.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;

public class VineryBoatTypes {

    public static TerraformBoatType cherry;

    public static void init() {
        Item cherryBoat = TerraformBoatItemHelper.registerBoatItem(new VineryIdentifier("cherry_boat"), () -> cherry, Vinery.CREATIVE_TAB);
        cherry = new TerraformBoatType.Builder().item(cherryBoat).build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new VineryIdentifier("cherry"), cherry);
    }
}