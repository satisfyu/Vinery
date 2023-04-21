package satisfyu.vinery.util.boat.api;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import satisfyu.vinery.VineryIdentifier;

public class TerraformBoatTypeRegistry {
	public static final ResourceLocation REGISTRY_ID = new VineryIdentifier("boat");

	public static <T> ResourceKey<Registry<T>> createRegistryKey(ResourceLocation key) {
		return ResourceKey.createRegistryKey(key);
	}

}
