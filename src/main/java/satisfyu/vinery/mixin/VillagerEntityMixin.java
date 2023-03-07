package satisfyu.vinery.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.villager.memory.ModMemoryModuleType;
import satisfyu.vinery.villager.task.ModTaskListProvider;
@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
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

    @Inject(method = "initBrain", at = @At("TAIL"))
    private void injectMethod(Brain<VillagerEntity> brain, CallbackInfo ci) {
        brain.setTaskList(Activity.CORE, ModTaskListProvider.createVineryStandTasks(brain, 0.5f));
    }
}
