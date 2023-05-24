package satisfyu.vinery.forge.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.registry.BlockRegistry;

import java.lang.reflect.InvocationTargetException;

public class VineryForgeVillagers {
	public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES,
			Vinery.MODID);

	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(
			ForgeRegistries.PROFESSIONS, Vinery.MODID);

	public static final RegistryObject<PoiType> WINEMAKER_POI = POI_TYPES.register("winemaker_poi",
			() -> new PoiType("winemaker_poi",
					ImmutableSet.copyOf(BlockRegistry.WINE_PRESS.get().getStateDefinition().getPossibleStates()), 1,
					1));

	public static final RegistryObject<VillagerProfession> WINEMAKER = VILLAGER_PROFESSIONS.register("winemaker",
			() -> new VillagerProfession("winemaker", WINEMAKER_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.VILLAGER_WORK_FARMER));

	public static void registerPOIs() {
		try {
			ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null,
					WINEMAKER_POI.get());
		} catch (InvocationTargetException | IllegalAccessException exception) {
			exception.printStackTrace();
		}
	}

	public static void register(IEventBus eventBus) {
		POI_TYPES.register(eventBus);
		VILLAGER_PROFESSIONS.register(eventBus);
	}
}
