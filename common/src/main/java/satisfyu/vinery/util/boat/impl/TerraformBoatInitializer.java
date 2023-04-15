package satisfyu.vinery.util.boat.impl;

import dev.architectury.registry.registries.Registrar;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatEntity;
import satisfyu.vinery.util.boat.impl.entity.TerraformChestBoatEntity;

public final class TerraformBoatInitializer {

	// Hack that prevents the following crash during client startup:
	// Caused by: java.lang.NoClassDefFoundError: Could not initialize class com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry
	private static final Registrar<TerraformBoatType> registryInstance = TerraformBoatTypeRegistry.INSTANCE;

	private static final ResourceLocation BOAT_ID = new ResourceLocation("terraform", "boat");
	public static final EntityType<TerraformBoatEntity> BOAT =  EntityType.Builder.<TerraformBoatEntity>of(TerraformBoatEntity::new, MobCategory.MISC)
			.sized(1.375f, 0.5625f)
		.build(BOAT_ID.toString());

	private static final ResourceLocation CHEST_BOAT_ID = new ResourceLocation("terraform", "chest_boat");
	public static final EntityType<TerraformChestBoatEntity> CHEST_BOAT = EntityType.Builder.<TerraformChestBoatEntity>of(TerraformChestBoatEntity::new, MobCategory.MISC)
		.sized(1.375f, 0.5625f)
		.build(CHEST_BOAT_ID.toString());

	public void init() {
		TerraformBoatTrackedData.register();
		Registry.register(BuiltInRegistries.ENTITY_TYPE, BOAT_ID, BOAT);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, CHEST_BOAT_ID, CHEST_BOAT);
	}
}
