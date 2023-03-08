package satisfyu.vinery.villager.task;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.VillagerEntity;
import satisfyu.vinery.villager.memory.ModMemoryModuleType;
import satisfyu.vinery.villager.poi.ModPointOfInterestTypes;

import java.util.Optional;

public class ModTaskListProvider {
    public ModTaskListProvider() {
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createVineryStandTasks(Brain<VillagerEntity> brain, float speed) {
        return ImmutableList.of(
                Pair.of(0, new ShopAtVineryStandTask(speed, 2, 150, 1200)),
                Pair.of(0, new ForgetCompletedPointOfInterestTask((registryEntry) -> registryEntry.matchesKey(ModPointOfInterestTypes.SHOP_KEY), ModMemoryModuleType.SHOP)),
                Pair.of(10, new FindPointOfInterestTask((registryEntry) -> registryEntry.matchesKey(ModPointOfInterestTypes.SHOP_KEY), ModMemoryModuleType.SHOP, false, Optional.of((byte)0))));
    }

    public static void init() {
        System.out.println("Init ModTaskListProvider"); //TODO LOGGER
    }
}
