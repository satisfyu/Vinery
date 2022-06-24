package daniking.vinery.block;

import daniking.vinery.ObjectRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GrapeBush extends SweetBerryBushBlock {

    public enum Type {
        RED,
        WHITE
    }
    private final Type type;

    public GrapeBush(Settings settings, Type type) {
        super(settings);
        this.type = type;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        boolean bl = i == 3;
        if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (i > 1) {
            int x = world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(this.type == Type.RED ? ObjectRegistry.RED_GRAPE : ObjectRegistry.WHITE_GRAPE, x + (bl ? 1 : 0)));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlockState(pos, state.with(AGE, 1), 2);
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return switch (this.type) {
            case RED -> new ItemStack(ObjectRegistry.RED_GRAPE);
            case WHITE -> new ItemStack(ObjectRegistry.WHITE_GRAPE);
        };
    }
    public Type getType() {
        return type;
    }
}
