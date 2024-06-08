package satisfyu.vinery.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfyu.vinery.entity.TraderMuleEntity;
import satisfyu.vinery.registry.EntityRegistry;

import java.util.Optional;

@Mixin(WanderingTraderSpawner.class)
public abstract class WanderingTraderManagerMixin implements CustomSpawner {
	@Shadow @Nullable protected abstract BlockPos findSpawnPositionNear(LevelReader world, BlockPos pos, int range);

	@Shadow protected abstract boolean hasEnoughSpace(BlockGetter world, BlockPos pos);

	@Shadow @Final private ServerLevelData serverLevelData;

	@Inject(method = "spawn", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/world/entity/EntityType;spawn(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/MobSpawnType;)Lnet/minecraft/world/entity/Entity;"), cancellable = true)
	private void trySpawn(ServerLevel world, CallbackInfoReturnable<Boolean> cir) {
		if (world.random.nextBoolean()) {
			ServerPlayer playerEntity = world.getRandomPlayer();
			if (playerEntity != null) {
				BlockPos blockPos = playerEntity.blockPosition();
				PoiManager pointOfInterestStorage = world.getPoiManager();
				Optional<BlockPos> optional = pointOfInterestStorage.find(type -> type.is(PoiTypes.MEETING), pos -> true, blockPos, 48, PoiManager.Occupancy.ANY);
				BlockPos blockPos2 = optional.orElse(blockPos);
				BlockPos blockPos3 = this.findSpawnPositionNear(world, blockPos2, 48);
				if (blockPos3 != null && this.hasEnoughSpace(world, blockPos3)) {
					if (!world.getBiome(blockPos3).is(Biomes.THE_VOID)) {
						WanderingTrader wanderingTraderEntity = EntityRegistry.WANDERING_WINEMAKER.get().spawn(world, blockPos3, MobSpawnType.EVENT);
						if (wanderingTraderEntity != null) {
							for (int j = 0; j < 2; ++j) {
								BlockPos blockPos4 = this.findSpawnPositionNear(world, wanderingTraderEntity.blockPosition(), 4);
								if (blockPos4 != null) {
									TraderMuleEntity traderLlamaEntity = EntityRegistry.MULE.get().spawn(world, blockPos4, MobSpawnType.EVENT);
									if (traderLlamaEntity != null) {
										traderLlamaEntity.setLeashedTo(wanderingTraderEntity, true);
									}
								}
							}
							this.serverLevelData.setWanderingTraderId(wanderingTraderEntity.getUUID());
							wanderingTraderEntity.setDespawnDelay(48000);
							wanderingTraderEntity.setWanderTarget(blockPos2);
							wanderingTraderEntity.restrictTo(blockPos2, 16);
							cir.setReturnValue(true);
						}
					}
				}
			}
		}
	}
}
