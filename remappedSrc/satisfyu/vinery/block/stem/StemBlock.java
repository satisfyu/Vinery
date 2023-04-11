package satisfyu.vinery.block.stem;

import net.minecraft.item.Item;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import satisfyu.vinery.util.GrapevineType;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class StemBlock extends Block implements Fertilizable {
    public static final EnumProperty<GrapevineType> GRAPE;
    public static final IntProperty AGE;

    public StemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GRAPE, GrapevineType.NONE).with(AGE, 0));
    }

    public void dropGrapes(World world, BlockState state, BlockPos pos) {
        final int x = 1 + world.random.nextInt(this.isMature(state) ? 2 : 1);
        final int bonus = this.isMature(state) ? 2 : 1;
        Item grape = state.get(GRAPE).getFruit();
        dropStack(world, pos, new ItemStack(grape, x + bonus));
        world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
    }

    public void dropGrapeSeeds(World world, BlockState state, BlockPos pos) {
        Item grape = state.get(GRAPE).getSeeds();
        dropStack(world, pos, new ItemStack(grape));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final int age = state.get(AGE);
        final ItemStack stack = player.getStackInHand(hand);
        if (age > 3) {
            dropGrapes(world, state, pos);
            world.setBlockState(pos, state.with(AGE, 2), 2);
            return ActionResult.success(world.isClient);
        }
        else {
            return ActionResult.PASS;
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(AGE) > 2) {
            dropGrapes(world, state, pos);
        }
        super.onBreak(world, pos, state, player);
    }

    public boolean hasTrunk(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock() == this;
    }

    private void boneMealGrow(World world, BlockState state, BlockPos pos) {
        int j;
        int age = state.get(AGE) + MathHelper.nextInt(world.getRandom(), 1, 2);
        if (age > (j = 4)) {
            age = j;
        }
        world.setBlockState(pos, this.withAge(state, age, state.get(GRAPE)), Block.NOTIFY_LISTENERS);
    }

    public BlockState withAge(BlockState state, int age, GrapevineType type) {
        return state.with(AGE, age).with(GRAPE, type);
    }



    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, GRAPE);
    }

    public boolean isMature(BlockState state) {
        return state.get(AGE) >= 4;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !isMature(state) && world.getBlockState(pos.down()).getBlock() == this && state.get(AGE) > 0;
    }
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        boneMealGrow(world, state, pos);
    }

    static {
        GRAPE = EnumProperty.of("grape", GrapevineType.class);
        AGE = Properties.AGE_4;
    }
}
