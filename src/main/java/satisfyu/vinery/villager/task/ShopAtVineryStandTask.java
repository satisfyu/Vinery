package satisfyu.vinery.villager.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestStorage;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.villager.memory.ModMemoryModuleType;

import java.util.Optional;

public class ShopAtVineryStandTask<E extends VillagerEntity> extends Task<E> {

    private final long timeToShop = 24000L;
    private final long distanceToShop = 25;
    private final MemoryModuleType<GlobalPos> destination;
    private final float speed;
    private final int completionRange;
    private final int maxRange;
    private final int maxRunTime;

    public ShopAtVineryStandTask(float speed, int completionRange, int maxRange, int maxRunTime) {
        super(ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
                ModMemoryModuleType.SHOP, MemoryModuleState.VALUE_PRESENT, ModMemoryModuleType.LAST_SHOPED, MemoryModuleState.REGISTERED));
        this.destination = ModMemoryModuleType.SHOP;
        this.speed = speed;
        this.completionRange = completionRange;
        this.maxRange = maxRange;
        this.maxRunTime = maxRunTime;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, E entity) {
        System.out.println("tryShop");
        Brain<?> brain = entity.getBrain();
        GlobalPos globalPos = brain.getOptionalMemory(ModMemoryModuleType.SHOP).get();
        if (world.getRegistryKey() != globalPos.getDimension()) {
            return false;
        }

        Optional<Long> optional = brain.getOptionalMemory(ModMemoryModuleType.LAST_SHOPED);
        if (optional.isPresent()) {
            long l = world.getTime() - optional.get();
            if (l > 0L && l < timeToShop) {
                return false;
            }
        }
        BlockState blockState = world.getBlockState(globalPos.getPos());
        return globalPos.getPos().isWithinDistance(entity.getPos(), distanceToShop) && blockState.getBlock() == ObjectRegistry.BASKET;
    }

    @Override
    protected void run(ServerWorld serverWorld, E villagerEntity, long l) {
        System.out.println("shop");
        Brain<?> brain = villagerEntity.getBrain();
        brain.getOptionalMemory(this.destination).ifPresent((pos) -> {
            if (!this.dimensionMismatches(serverWorld, pos) && !this.shouldGiveUp(serverWorld, villagerEntity)) {
                if (this.exceedsMaxRange(villagerEntity, pos)) {
                    Vec3d vec3d = null;
                    int i = 0;

                    for(; i < 1000 && (vec3d == null || this.exceedsMaxRange(villagerEntity, GlobalPos.create(serverWorld.getRegistryKey(), new BlockPos(vec3d)))); ++i) {
                        vec3d = NoPenaltyTargeting.findTo(villagerEntity, 15, 7, Vec3d.ofBottomCenter(pos.getPos()), 1.5707963705062866);
                    }

                    if (i == 1000) {
                        this.giveUp(villagerEntity, l);
                        return;
                    }
                    brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, this.speed, this.completionRange));
                } else {
                    if (!this.reachedDestination(serverWorld, villagerEntity, pos)) {
                        brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(pos.getPos(), this.speed, this.completionRange));
                    } else {
                        PointOfInterestStorage pointOfInterestStorage = serverWorld.getPointOfInterestStorage();
                        pointOfInterestStorage.releaseTicket(pos.getPos());

                        brain.forget(ModMemoryModuleType.SHOP);
                        brain.remember(ModMemoryModuleType.LAST_SHOPED, serverWorld.getTime());

                        BlockPos blockPos = pos.getPos();
                        ItemScatterer.spawn(serverWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ(), Items.DIAMOND.getDefaultStack());
                    }
                }
            } else {
                this.giveUp(villagerEntity, l);
            }
        });
    }

    private void giveUp(VillagerEntity villager, long time) {
        Brain<?> brain = villager.getBrain();
        villager.releaseTicketFor(this.destination);
        brain.forget(this.destination);
        brain.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
    }

    private boolean shouldGiveUp(ServerWorld world, VillagerEntity villager) {
        Optional<Long> optional = villager.getBrain().getOptionalMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        if (optional.isPresent()) {
            return world.getTime() - optional.get() > (long)this.maxRunTime;
        } else {
            return false;
        }
    }

    private boolean exceedsMaxRange(VillagerEntity villager, GlobalPos pos) {
        return pos.getPos().getManhattanDistance(villager.getBlockPos()) > this.maxRange;
    }

    private boolean dimensionMismatches(ServerWorld world, GlobalPos pos) {
        return pos.getDimension() != world.getRegistryKey();
    }

    private boolean reachedDestination(ServerWorld world, VillagerEntity villager, GlobalPos pos) {
        return pos.getDimension() == world.getRegistryKey() && pos.getPos().getManhattanDistance(villager.getBlockPos()) <= this.completionRange;
    }
}
