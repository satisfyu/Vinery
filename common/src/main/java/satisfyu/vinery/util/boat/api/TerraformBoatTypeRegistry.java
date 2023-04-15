package satisfyu.vinery.util.boat.api;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import satisfyu.vinery.Vinery;

/**
 * @see TerraformBoatTypeRegistry#INSTANCE
 */
public class TerraformBoatTypeRegistry {
	private static final ResourceLocation REGISTRY_ID = new ResourceLocation("terraform", "boat");


	public static Registrar<TerraformBoatType> INSTANCE = RegistrarManager.get(Vinery.MODID).get(ResourceKey.createRegistryKey(REGISTRY_ID));

	public static ResourceKey<TerraformBoatType> createKey(ResourceLocation id) {
		return ResourceKey.create(INSTANCE.key(), id);

	}
}
