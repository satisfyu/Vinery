package satisfyu.vinery;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.util.boat.impl.TerraformBoatInitializer;
import satisfyu.vinery.util.boat.impl.client.TerraformBoatClientInitializer;
import satisfyu.vinery.world.VineryBiomeModification;
import satisfyu.vinery.world.VineryFeatures;

public class Vinery {

    public static final String MODID = "vinery";
    public static final RegistrarManager REGISTRIES = RegistrarManager.get(MODID);
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final CreativeTabRegistry.TabSupplier EXAMPLE_TAB = CreativeTabRegistry.create(new ResourceLocation(MODID, "example_tab"), () ->
            new ItemStack(Items.SADDLE));

    
    public static void init() {
        TerraformBoatInitializer.init();

        VineryEffects.init();
        ObjectRegistry.init();
        VineryBlockEntityTypes.init();
        VineryStorageTypes.init();
        VineryScreenHandlerTypes.init();
        VineryRecipeTypes.init();
        VineryEntites.init();
        VineryBoatTypes.init();
        VineryFeatures.init();
        VineryBiomeModification.init();
        VinerySoundEvents.init();
        VineryVillagers.init();
        VineryCompostableItems.init();
    }
}
