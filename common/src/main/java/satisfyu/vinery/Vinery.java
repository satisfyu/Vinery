package satisfyu.vinery;

import de.cristelknight.doapi.DoApiExpectPlatform;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.hooks.item.tool.ShovelItemHooks;
import dev.architectury.registry.fuel.FuelRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.world.VineryFeatures;

public class Vinery {
    public static final String MODID = "vinery";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    
    public static void init() {
        VineryConfig.DEFAULT.getConfig();
        VineryTabRegistry.init();
        VineryEffects.init();
        ObjectRegistry.init();
        VineryBlockEntityTypes.init();
        VineryScreenHandlerTypes.init();
        VineryRecipeTypes.init();
        VineryEntites.init();
        VineryFeatures.init();
        VinerySoundEvents.init();


        DoApiExpectPlatform.registerBuiltInPack(Vinery.MODID, new VineryIdentifier("bushy_leaves"), false);
    }

    public static void commonSetup(){
        VineryFlammableBlocks.init();
        ObjectRegistry.registerCompostable();
        GrapeTypes.addGrapeAttributes();

        FuelRegistry.register(300, ObjectRegistry.CHERRY_FENCE.get(), ObjectRegistry.CHERRY_FENCE_GATE.get(), ObjectRegistry.STACKABLE_LOG.get(), ObjectRegistry.FERMENTATION_BARREL.get());

        AxeItemHooks.addStrippable(ObjectRegistry.CHERRY_LOG.get(), ObjectRegistry.STRIPPED_CHERRY_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_CHERRY_WOOD.get());
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_LOG.get(), Blocks.STRIPPED_OAK_LOG);
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_WOOD.get(), Blocks.STRIPPED_OAK_WOOD);
        ShovelItemHooks.addFlattenable(ObjectRegistry.GRASS_SLAB.get(), Blocks.DIRT_PATH.defaultBlockState());
    }

    public static ResourceLocation MODID(String path) {
        return new ResourceLocation(Vinery.MODID, path);
    }
}

