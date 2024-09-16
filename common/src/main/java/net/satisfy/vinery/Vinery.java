package net.satisfy.vinery;

import de.cristelknight.doapi.DoApiEP;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.hooks.item.tool.ShovelItemHooks;
import dev.architectury.registry.fuel.FuelRegistry;
import net.minecraft.world.level.block.Blocks;
import net.satisfy.vinery.config.VineryConfig;
import net.satisfy.vinery.event.EntityDamageEvent;
import net.satisfy.vinery.event.ParticleSpawnEvent;
import net.satisfy.vinery.registry.*;
import net.satisfy.vinery.util.VineryIdentifier;
import net.satisfy.vinery.world.VineryFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vinery {
    public static final String MOD_ID = "vinery";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
        DataFixerRegistry.init();
        VineryConfig config = loadConfig();
        VineryConfig validatedConfig = config.validate();
        validatedConfig.setInstance(validatedConfig);

        ObjectRegistry.init();
        ObjectRegistry.initItemsWithConfig();

        TabRegistry.init();
        BoatAndSignRegistry.init();
        ArmorMaterialRegistry.init();
        BlockEntityTypeRegistry.init();
        MobEffectRegistry.init();
        ScreenhandlerTypeRegistry.init();
        RecipeTypesRegistry.init();
        EntityRegistry.init();
        VineryFeatures.init();
        SoundEventRegistry.init();

        ParticleSpawnEvent particleSpawnEvent = new ParticleSpawnEvent();
        PlayerEvent.ATTACK_ENTITY.register(particleSpawnEvent);
        EntityDamageEvent entityDamageEvent = new EntityDamageEvent();
        EntityEvent.LIVING_HURT.register(entityDamageEvent);
        DoApiEP.registerBuiltInPack(Vinery.MOD_ID, VineryIdentifier.of("bushy_leaves"), false);
    }

    public static void commonSetup() {
        FlammableBlockRegistry.init();
        GrapeTypeRegistry.addGrapeAttributes();

        FuelRegistry.register(300, ObjectRegistry.DARK_CHERRY_FENCE.get(), ObjectRegistry.DARK_CHERRY_FENCE_GATE.get(), ObjectRegistry.STACKABLE_LOG.get(), ObjectRegistry.FERMENTATION_BARREL.get());

        AxeItemHooks.addStrippable(ObjectRegistry.DARK_CHERRY_LOG.get(), ObjectRegistry.STRIPPED_DARK_CHERRY_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.DARK_CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_DARK_CHERRY_WOOD.get());
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_LOG.get(), Blocks.STRIPPED_OAK_LOG);
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_WOOD.get(), Blocks.STRIPPED_OAK_WOOD);
        
        ShovelItemHooks.addFlattenable(ObjectRegistry.GRASS_SLAB.get(), Blocks.DIRT_PATH.defaultBlockState());
    }

    private static VineryConfig loadConfig() {
        VineryConfig config = VineryConfig.DEFAULT.getConfig();
        return config.validate();
    }
}
