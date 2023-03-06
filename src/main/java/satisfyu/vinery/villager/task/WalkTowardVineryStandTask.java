package satisfyu.vinery.villager.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.poi.PointOfInterestStorage;
import satisfyu.vinery.villager.memory.ModMemoryModuleType;

import java.util.Optional;

public class WalkTowardVineryStandTask<E extends VillagerEntity> extends Task<E> {
    final float speed;

    public WalkTowardVineryStandTask(Brain<VillagerEntity> brain, float speed) {
        super(ImmutableMap.of(ModMemoryModuleType.SHOP, MemoryModuleState.VALUE_PRESENT), 1200);
        this.speed = speed;
        System.out.println("WalkTowardVineryStandTask");
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        System.out.println("shouldRun");
        return villagerEntity.getBrain().getFirstPossibleNonCoreActivity().map((activity) -> activity == Activity.IDLE || activity == Activity.WORK || activity == Activity.PLAY).orElse(true);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        System.out.println("shouldKeepRunning");
        return villagerEntity.getBrain().hasMemoryModule(ModMemoryModuleType.SHOP);
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        System.out.println("keepRunning");
        LookTargetUtil.walkTowards(villagerEntity, villagerEntity.getBrain().getOptionalMemory(ModMemoryModuleType.SHOP).get().getPos(), this.speed, 1);
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        System.out.println("finishRunning");
        Optional<GlobalPos> optional = villagerEntity.getBrain().getOptionalMemory(ModMemoryModuleType.SHOP);
        optional.ifPresent((pos) -> {
            BlockPos blockPos = pos.getPos();
            ServerWorld serverWorld2 = serverWorld.getServer().getWorld(pos.getDimension());
            if (serverWorld2 != null) {
                PointOfInterestStorage pointOfInterestStorage = serverWorld2.getPointOfInterestStorage();
                if (pointOfInterestStorage.test(blockPos, (registryEntry) -> {
                    return true;
                })) {
                    pointOfInterestStorage.releaseTicket(blockPos);
                }

                DebugInfoSender.sendPointOfInterest(serverWorld, blockPos);
            }
        });
        System.out.println("FORGET");
        villagerEntity.getBrain().forget(ModMemoryModuleType.SHOP);
    }

    @Override
    protected void run(ServerWorld world, E entity, long time) {
        super.run(world, entity, time);
        System.out.println("EYYYYY");
    }
}
