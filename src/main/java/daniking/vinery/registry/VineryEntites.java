package daniking.vinery.registry;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.entity.TraderMuleEntity;
import daniking.vinery.entity.WanderingWinemakerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.MuleEntity;
import net.minecraft.util.registry.Registry;

public class VineryEntites {
	
	public static final EntityType<TraderMuleEntity> MULE = Registry.register(Registry.ENTITY_TYPE,
			new VineryIdentifier("mule"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TraderMuleEntity::new).dimensions(new EntityDimensions(0.9f, 1.87f, true)).trackRangeChunks(10).build()
	                                                                         );
	
	public static final EntityType<WanderingWinemakerEntity> WANDERING_WINEMAKER = Registry.register(Registry.ENTITY_TYPE,
			new VineryIdentifier("wandering_winemaker"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WanderingWinemakerEntity::new)
			                       .dimensions(new EntityDimensions(0.6f, 1.95f, true))
			                       .trackRangeChunks(10)
			                       .build()
	                                                                                                );
	
	public static void init() {
		FabricDefaultAttributeRegistry.register(MULE, LlamaEntity.createLlamaAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f));
		FabricDefaultAttributeRegistry.register(WANDERING_WINEMAKER, WanderingWinemakerEntity.createMobAttributes());
	}
	
}