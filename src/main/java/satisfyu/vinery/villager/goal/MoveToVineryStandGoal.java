package satisfyu.vinery.villager.goal;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.PointOfInterestTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.block.VineryStand;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoveToVineryStandGoal extends Goal {
    private final VillagerEntity villager;
    private BlockPos vineryStandPos;
    private List<BlockPos> possibleVineryStands;
    private int tick;
    private int ticksLeftToMove = 600;
    private int ticksLeftToFind = 200;
    @Nullable
    private Path path;

    public MoveToVineryStandGoal(VillagerEntity villager) {
        this.villager = villager;
    }

    @Override
    public boolean canStart() {
        return !villager.hasPositionTarget() && ticksLeftToMove == 0 && !this.isCloseEnough(vineryStandPos) && villager.world.getBlockState(vineryStandPos).isIn(BlockTags.BEEHIVES);
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {
        if (vineryStandPos == null) {
            findVineryStand();
        }
    }
    @Override
    public void tick() {
        ticksLeftToFind--;
        ticksLeftToMove--;
    }

    private void findVineryStand() {
        ticksLeftToFind = 200;
        List<BlockPos> list = this.getNearbyVineryStands();
        if (!list.isEmpty()) {
            Iterator var2 = list.iterator();

            BlockPos blockPos;
            do {
                if (!var2.hasNext()) {
                    possibleVineryStands.clear();
                    vineryStandPos = (BlockPos)list.get(0);
                    return;
                }
                blockPos = (BlockPos)var2.next();
            } while(false); //isPossibleVineryStand(blockPos));
            vineryStandPos = blockPos;
        }
    }

    private List<BlockPos> getNearbyVineryStands() {
        BlockPos blockPos = villager.getBlockPos();
        PointOfInterestStorage pointOfInterestStorage = ((ServerWorld)villager.world).getPointOfInterestStorage();
        Stream<PointOfInterest> stream = pointOfInterestStorage.getInCircle((poiType) -> poiType.isIn(PointOfInterestTypeTags.BEE_HOME), blockPos, 20, PointOfInterestStorage.OccupationStatus.ANY);
        return (List)stream.map(PointOfInterest::getPos).sorted(Comparator.comparingDouble((blockPos2) -> {
            return blockPos2.getSquaredDistance(blockPos);
        })).collect(Collectors.toList());
    }

    private boolean isCloseEnough(BlockPos pos) {
        if (isWithinDistance(pos, 2)) {
            return true;
        } else {
            Path path = villager.getNavigation().getCurrentPath();
            return path != null && path.getTarget().equals(vineryStandPos) && path.reachesTarget() && path.isFinished();
        }
    }

    boolean isWithinDistance(BlockPos pos, int distance) {
        return pos.isWithinDistance(vineryStandPos, (double)distance);
    }
}
