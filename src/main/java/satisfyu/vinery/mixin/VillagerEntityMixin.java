package satisfyu.vinery.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.villager.memory.ModMemoryModuleType;
import satisfyu.vinery.villager.poi.ModPointOfInterestTypes;
import satisfyu.vinery.villager.task.ModTaskListProvider;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {
    @Shadow
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            ModMemoryModuleType.SHOP, ModMemoryModuleType.LAST_SHOPED,
            //Minecraft
            MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE,
            MemoryModuleType.MEETING_POINT, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.WALK_TARGET,
             MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.BREED_TARGET, MemoryModuleType.PATH, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_BED,
            MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE,
            MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WOKEN, MemoryModuleType.LAST_WORKED_AT_POI);

    @Shadow
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<VillagerEntity, RegistryEntry<PointOfInterestType>>> POINTS_OF_INTEREST = ImmutableMap.of(
            MemoryModuleType.HOME, (villager, registryEntry) -> registryEntry.matchesKey(PointOfInterestTypes.HOME),
            MemoryModuleType.JOB_SITE, (villager, registryEntry) -> villager.getVillagerData().getProfession().heldWorkstation().test(registryEntry),
            MemoryModuleType.POTENTIAL_JOB_SITE, (villager, registryEntry) -> VillagerProfession.IS_ACQUIRABLE_JOB_SITE.test(registryEntry),
            MemoryModuleType.MEETING_POINT, (villager, registryEntry) -> registryEntry.matchesKey(PointOfInterestTypes.MEETING),
            ModMemoryModuleType.SHOP, (villager, registryEntry) -> registryEntry.matchesKey(ModPointOfInterestTypes.SHOP_KEY));


    @Shadow public abstract void releaseTicketFor(MemoryModuleType<GlobalPos> memoryModuleType);

    @Inject(method = "initBrain", at = @At("TAIL"))
    private void injectMethod(Brain<VillagerEntity> brain, CallbackInfo ci) {
        brain.setTaskList(Activity.CORE, ModTaskListProvider.createVineryStandTasks(brain, 0.4f));
    }

    @Inject(method = "releaseAllTickets", at = @At("TAIL"))
    private void releaseShopTicket(CallbackInfo ci) {
            this.releaseTicketFor(ModMemoryModuleType.SHOP);
    }
}
