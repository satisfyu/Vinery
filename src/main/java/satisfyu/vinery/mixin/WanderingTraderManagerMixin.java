package satisfyu.vinery.mixin;

import satisfyu.vinery.registry.VineryEntites;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.TraderLlamaEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestTypes;
import net.minecraft.world.spawner.Spawner;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(WanderingTraderManager.class)
public abstract class WanderingTraderManagerMixin implements Spawner {
	@Shadow @Nullable protected abstract BlockPos getNearbySpawnPos(WorldView world, BlockPos pos, int range);
	
	@Shadow protected abstract boolean doesNotSuffocateAt(BlockView world, BlockPos pos);
	
	@Shadow @Final private ServerWorldProperties properties;
	
	@Inject(method = "trySpawn", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/entity/EntityType;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/SpawnReason;)Lnet/minecraft/entity/Entity;"), cancellable = true)
	private void trySpawn(ServerWorld world, CallbackInfoReturnable<Boolean> cir) {
		if (world.random.nextBoolean()) {
			ServerPlayerEntity playerEntity = world.getRandomAlivePlayer();
			BlockPos blockPos = playerEntity.getBlockPos();
			int i = 48;
			PointOfInterestStorage pointOfInterestStorage = world.getPointOfInterestStorage();
			Optional<BlockPos> optional = pointOfInterestStorage.getPosition(type -> type.matchesKey(PointOfInterestTypes.MEETING), pos -> true, blockPos, 48, PointOfInterestStorage.OccupationStatus.ANY);
			BlockPos blockPos2 = optional.orElse(blockPos);
			BlockPos blockPos3 = this.getNearbySpawnPos(world, blockPos2, 48);
			if (blockPos3 != null && this.doesNotSuffocateAt(world, blockPos3)) {
				if (world.getBiome(blockPos3).matchesKey(BiomeKeys.THE_VOID)) {
					return;
				}
				
				WanderingTraderEntity wanderingTraderEntity = VineryEntites.WANDERING_WINEMAKER.spawn(world,  blockPos3, SpawnReason.EVENT);
				if (wanderingTraderEntity != null) {
					for (int j = 0; j < 2; ++j) {
						BlockPos blockPos4 = this.getNearbySpawnPos(world, wanderingTraderEntity.getBlockPos(), 4);
						if (blockPos4 == null) {
							return;
						}
						TraderLlamaEntity traderLlamaEntity = VineryEntites.MULE.spawn(world, blockPos4, SpawnReason.EVENT);
						if (traderLlamaEntity == null) {
							return;
						}
						traderLlamaEntity.attachLeash(wanderingTraderEntity, true);
					}
					this.properties.setWanderingTraderId(wanderingTraderEntity.getUuid());
					wanderingTraderEntity.setDespawnDelay(48000);
					wanderingTraderEntity.setWanderTarget(blockPos2);
					wanderingTraderEntity.setPositionTarget(blockPos2, 16);
					cir.setReturnValue(true);
				}
			}
		}
	}
}