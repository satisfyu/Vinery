package satisfyu.vinery.util.boat.impl;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.registry.VineryEntites;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatEntity;

public final class TerraformBoatInitializer {
	private static final ResourceLocation BOAT_ID = new VineryIdentifier("boat");

	public static final RegistrySupplier<EntityType<TerraformBoatEntity>> BOAT = VineryEntites.create("boat",
			() -> EntityType.Builder.<TerraformBoatEntity> of(TerraformBoatEntity::new, MobCategory.MISC)
					.sized(1.375f, 0.5625f).build(BOAT_ID.toString()));

	public static void init() {
		TerraformBoatTrackedData.register();
	}
}
