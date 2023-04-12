package satisfyu.vinery.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import net.minecraft.item.Item;

public class VineryBoatTypes {

    private static final Identifier CHERRY_BOAT_ID = new Identifier(Vinery.MODID, "cherry_boat");
    private static final Identifier CHERRY_CHEST_BOAT_ID = new Identifier(Vinery.MODID, "cherry_chest_boat");

    private static final RegistryKey<TerraformBoatType> CHERRY_BOAT_KEY = TerraformBoatTypeRegistry.createKey(CHERRY_BOAT_ID);

    public static TerraformBoatType CHERRY;

    public static void init() {
        Item cherryBoat = TerraformBoatItemHelper.registerBoatItem(new VineryIdentifier("cherry_boat"), CHERRY_BOAT_KEY, false);
        Item cherryChestBoat = TerraformBoatItemHelper.registerBoatItem(new VineryIdentifier("cherry_chest_boat"), CHERRY_BOAT_KEY, true);
        CHERRY = new TerraformBoatType.Builder().item(cherryBoat).chestItem(cherryChestBoat).planks(ObjectRegistry.CHERRY_PLANKS.asItem()).build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new VineryIdentifier("cherry"), CHERRY);
    }
}
