package satisfyu.vinery;

import de.cristelknight.doapi.DoApiEP;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.hooks.item.tool.ShovelItemHooks;
import dev.architectury.registry.fuel.FuelRegistry;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.event.ParticleSpawnEvent;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.util.VineryIdentifier;
import satisfyu.vinery.world.VineryFeatures;

public class Vinery {
    public static final String MOD_ID = "vinery";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    public static void init() {
        DataFixerRegistry.init();
        VineryConfig.DEFAULT.getConfig();
        TabRegistry.init();
        ObjectRegistry.init();
        BoatAndSignRegistry.init();
        BlockEntityTypeRegistry.init();
        MobEffectRegistry.init();
        ScreenhandlerTypeRegistry.init();
        RecipeTypesRegistry.init();
        EntityRegistry.init();
        VineryFeatures.init();
        SoundEventRegistry.init();
        ParticleSpawnEvent particleSpawnEvent = new ParticleSpawnEvent();
        PlayerEvent.ATTACK_ENTITY.register(particleSpawnEvent);
        DoApiEP.registerBuiltInPack(Vinery.MOD_ID, new VineryIdentifier("bushy_leaves"), false);
    }

    public static void commonSetup(){
        FlammableBlockRegistry.init();
        GrapeTypeRegistry.addGrapeAttributes();

        FuelRegistry.register(300, ObjectRegistry.DARK_CHERRY_FENCE.get(), ObjectRegistry.DARK_CHERRY_FENCE_GATE.get(), ObjectRegistry.STACKABLE_LOG.get(), ObjectRegistry.FERMENTATION_BARREL.get());

        AxeItemHooks.addStrippable(ObjectRegistry.DARK_CHERRY_LOG.get(), ObjectRegistry.STRIPPED_DARK_CHERRY_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.DARK_CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_DARK_CHERRY_WOOD.get());
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_LOG.get(), Blocks.STRIPPED_OAK_LOG);
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_WOOD.get(), Blocks.STRIPPED_OAK_WOOD);
        ShovelItemHooks.addFlattenable(ObjectRegistry.GRASS_SLAB.get(), Blocks.DIRT_PATH.defaultBlockState());
    }
}

