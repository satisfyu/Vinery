package net.satisfy.vinery.forge.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.registry.ObjectRegistry;

import java.lang.reflect.InvocationTargetException;

public class VineryForgeVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Vinery.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Vinery.MOD_ID);

    public static final RegistryObject<PoiType> WINEMAKER_POI = POI_TYPES.register("winemaker_poi", () ->
            new PoiType(ImmutableSet.copyOf(ObjectRegistry.APPLE_PRESS.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final RegistryObject<VillagerProfession> WINEMAKER = VILLAGER_PROFESSIONS.register("winemaker", () ->
            new VillagerProfession("winemaker", x -> x.get() == WINEMAKER_POI.get(), x -> x.get() == WINEMAKER_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_FARMER));


    public static void registerPOIs(){
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, WINEMAKER_POI.get());
        } catch (InvocationTargetException | IllegalAccessException exception){
            exception.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }


}
