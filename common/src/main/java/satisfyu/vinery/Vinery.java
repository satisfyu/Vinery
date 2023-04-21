package satisfyu.vinery;

import dev.architectury.event.EventHandler;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.impl.TerraformBoatInitializer;
import satisfyu.vinery.util.boat.impl.client.TerraformBoatClientInitializer;
import satisfyu.vinery.world.VineryBiomeModification;
import satisfyu.vinery.world.VineryFeatures;

import static satisfyu.vinery.registry.VineryBoatTypes.CHERRY;
import static satisfyu.vinery.registry.VineryBoatTypes.CHERRY_BOAT_ID;

public class Vinery {

    public static final String MODID = "vinery";
    public static final RegistrarManager REGISTRIES = RegistrarManager.get(MODID);
    public static final Logger LOGGER = LogManager.getLogger(MODID);



    
    public static void init() {
        TerraformBoatInitializer.init();

        VineryEffects.init();
        ObjectRegistry.init();
        VineryBlockEntityTypes.init();
        VineryStorageTypes.init();
        VineryScreenHandlerTypes.init();
        VineryRecipeTypes.init();
        VineryEntites.init();
        VineryFeatures.init();
        VineryBiomeModification.init();
        VinerySoundEvents.init();

        VineryBoatTypes.init();
    }

    public static void commonSetup(){

        VineryCompostableItems.init();
        //VineryVillagers.init();


        FuelRegistry.register(300, ObjectRegistry.CHERRY_FENCE.get(), ObjectRegistry.CHERRY_FENCE_GATE.get(), ObjectRegistry.STACKABLE_LOG.get(), ObjectRegistry.FERMENTATION_BARREL.get());
        AxeItemHooks.addStrippable(ObjectRegistry.CHERRY_LOG.get(), ObjectRegistry.STRIPPED_CHERRY_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_CHERRY_WOOD.get());
        AxeItemHooks.addStrippable(ObjectRegistry.OLD_CHERRY_LOG.get(), ObjectRegistry.STRIPPED_OLD_CHERRY_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.OLD_CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_OLD_CHERRY_WOOD.get());

/*
        FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
        flammableRegistry.add(CHERRY_PLANKS, 5, 20);
        flammableRegistry.add(STRIPPED_CHERRY_LOG, 5, 5);
        flammableRegistry.add(STRIPPED_OLD_CHERRY_LOG, 5, 5);
        flammableRegistry.add(CHERRY_LOG, 5, 5);
        flammableRegistry.add(OLD_CHERRY_LOG, 5, 5);
        flammableRegistry.add(STRIPPED_CHERRY_WOOD, 5, 5);
        flammableRegistry.add(CHERRY_WOOD, 5, 5);
        flammableRegistry.add(OLD_CHERRY_WOOD, 5, 5);
        flammableRegistry.add(STRIPPED_OLD_CHERRY_WOOD, 5, 5);
        flammableRegistry.add(CHERRY_SLAB, 5, 20);
        flammableRegistry.add(CHERRY_STAIRS, 5, 20);
        flammableRegistry.add(CHERRY_FENCE, 5, 20);
        flammableRegistry.add(CHERRY_FENCE_GATE, 5, 20);
        */
    }
}
