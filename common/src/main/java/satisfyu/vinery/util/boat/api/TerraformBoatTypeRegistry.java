package satisfyu.vinery.util.boat.api;

import dev.architectury.registry.registries.Registrar;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;

/**
 * @see TerraformBoatTypeRegistry#INSTANCE
 */
public class TerraformBoatTypeRegistry {
	public static final ResourceLocation REGISTRY_ID = new VineryIdentifier("boat");

	public static Registrar<TerraformBoatType> INSTANCE = Vinery.REGISTRIES.builder(REGISTRY_ID, new TerraformBoatType[0]).build();;

	public static ResourceKey<TerraformBoatType> createKey(ResourceLocation id) {
		return ResourceKey.create(INSTANCE.key(), id);
	}

}
