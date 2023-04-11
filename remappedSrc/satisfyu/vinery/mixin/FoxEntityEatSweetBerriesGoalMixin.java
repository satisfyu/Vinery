package satisfyu.vinery.mixin;

import satisfyu.vinery.block.grape.GrapeBush;
import satisfyu.vinery.util.GrapevineType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxEntity.EatBerriesGoal.class)
public abstract class FoxEntityEatSweetBerriesGoalMixin extends MoveToTargetPosGoal {
    @Final
    @Shadow
    FoxEntity field_17975;   // Synthetic field

    public FoxEntityEatSweetBerriesGoalMixin(PathAwareEntity mob, double speed, int range) {
        super(mob, speed, range);
    }

    @Inject(method = "isTargetPos", at = @At("HEAD"), cancellable = true)
    private void isTargetPos(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof GrapeBush) {
            cir.setReturnValue(state.get(GrapeBush.AGE) >= 2);
        }
    }

    @Inject(method = "eatBerries", at = @At("TAIL"))
    private void eatGrapes(CallbackInfo ci) {
        final BlockState state = field_17975.world.getBlockState(this.targetPos);
        if (state.getBlock() instanceof GrapeBush bush) {
            pickGrapes(state, bush.getType());
        }
    }

    private void pickGrapes(BlockState state, GrapevineType type) {
        final int age = state.get(GrapeBush.AGE);
        state.with(GrapeBush.AGE, 1);
        int j = 1 + field_17975.world.random.nextInt(2) + (age == 3 ? 1 : 0);
        ItemStack itemStack = field_17975.getEquippedStack(EquipmentSlot.MAINHAND);
        ItemStack grape = getGrapeFor(type);
        if (itemStack.isEmpty()) {
            field_17975.equipStack(EquipmentSlot.MAINHAND, grape);
            --j;
        }
        if (j > 0) {
            Block.dropStack(field_17975.world, this.targetPos, new ItemStack(grape.getItem(), j));
        }
        field_17975.playSound(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
        field_17975.world.setBlockState(this.targetPos, state.with(GrapeBush.AGE, 1), 2);
    }

    private static ItemStack getGrapeFor(GrapevineType type) {
        return type.getFruit().getDefaultStack();
    }
}
