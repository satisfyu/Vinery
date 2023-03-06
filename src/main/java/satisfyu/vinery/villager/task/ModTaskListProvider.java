package satisfyu.vinery.villager.task;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestTypes;
import satisfyu.vinery.villager.memory.ModMemoryModuleType;
import satisfyu.vinery.villager.poi.ModPointOfInterestTypes;

import java.util.Optional;

public class ModTaskListProvider extends VillagerTaskListProvider {
    private static final float JOB_WALKING_SPEED = 0.4F;

    public ModTaskListProvider() {
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createVineryStandTasks(Brain<VillagerEntity> brain, float speed) {
        System.out.println("createVineryStandTasks");
        return ImmutableList.of(
                Pair.of(0, new VillagerWalkTowardsTask(ModMemoryModuleType.SHOP, speed, 4, 150, 1200)),
                Pair.of(0, new ForgetCompletedPointOfInterestTask((registryEntry) -> registryEntry.matchesKey(ModPointOfInterestTypes.SHOP_KEY), ModMemoryModuleType.SHOP)),
                Pair.of(10, new FindPointOfInterestTask((registryEntry) -> registryEntry.matchesKey(ModPointOfInterestTypes.SHOP_KEY), ModMemoryModuleType.SHOP, false, Optional.of((byte)14))));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> testTask(VillagerProfession profession, float speed) {
        return ImmutableList.of(
                Pair.of(0, new StayAboveWaterTask(0.8F)),
                Pair.of(0, new OpenDoorsTask()),
                Pair.of(0, new LookAroundTask(45, 90)),
                Pair.of(0, new PanicTask()),
                Pair.of(0, new WakeUpTask()),
                Pair.of(0, new HideWhenBellRingsTask()),
                Pair.of(0, new StartRaidTask()),
                Pair.of(0, new ForgetCompletedPointOfInterestTask(profession.heldWorkstation(), MemoryModuleType.JOB_SITE)),
                Pair.of(0, new ForgetCompletedPointOfInterestTask(profession.acquirableWorkstation(), MemoryModuleType.POTENTIAL_JOB_SITE)),
                Pair.of(1, new WanderAroundTask()), Pair.of(2, new WorkStationCompetitionTask(profession)),
                Pair.of(3, new FollowCustomerTask(speed)),
                new Pair[]{
                        Pair.of(5, new WalkToNearestVisibleWantedItemTask(speed, false, 4)),
                        Pair.of(6, new FindPointOfInterestTask(profession.acquirableWorkstation(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())),
                        Pair.of(7, new WalkTowardJobSiteTask(speed)),
                        Pair.of(8, new TakeJobSiteTask(speed)),
                        Pair.of(10, new FindPointOfInterestTask((registryEntry) -> registryEntry.matchesKey(PointOfInterestTypes.HOME), MemoryModuleType.HOME, false, Optional.of((byte)14))),
                        Pair.of(10, new FindPointOfInterestTask((registryEntry) -> registryEntry.matchesKey(PointOfInterestTypes.MEETING), MemoryModuleType.MEETING_POINT, true, Optional.of((byte)14))),
                        Pair.of(10, new GoToWorkTask()),
                        Pair.of(10, new LoseJobOnSiteLossTask())
                });
    }

    public static void init() {
        System.out.println("Init ModTaskListProvider"); //TODO
    }
}
