package satisfyu.vinery.registry;


import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.api.item.TerraformBoatItemHelper;

import java.util.function.Supplier;

public class VineryBoatTypes {

    private static final ResourceLocation CHERRY_BOAT_ID = new ResourceLocation(Vinery.MODID, "cherry_boat");
    private static final ResourceLocation CHERRY_CHEST_BOAT_ID = new ResourceLocation(Vinery.MODID, "cherry_chest_boat");

    private static final ResourceKey<TerraformBoatType> CHERRY_BOAT_KEY = TerraformBoatTypeRegistry.createKey(CHERRY_BOAT_ID);

    public static Supplier<TerraformBoatType> CHERRY;

    public static void init() {
        RegistrySupplier<Item> cherryBoat = TerraformBoatItemHelper.registerBoatItem(new VineryIdentifier("cherry_boat"), CHERRY_BOAT_KEY, false);
        RegistrySupplier<Item> cherryChestBoat = TerraformBoatItemHelper.registerBoatItem(new VineryIdentifier("cherry_chest_boat"), CHERRY_BOAT_KEY, true);
        CHERRY = () -> new TerraformBoatType.Builder().item(cherryBoat.get()).chestItem(cherryChestBoat.get()).planks(ObjectRegistry.CHERRY_PLANKS.get().asItem()).build();
        TerraformBoatTypeRegistry.INSTANCE.register(new VineryIdentifier("cherry"), CHERRY);
    }
}
