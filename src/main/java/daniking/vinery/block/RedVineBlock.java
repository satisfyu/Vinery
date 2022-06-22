package daniking.vinery.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class RedVineBlock extends VineBlock implements Fertilizable {

    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = Properties.AGE_3;

    public enum Variant {
        Empty,
        A,
        B,
        C,
    }

    private final Variant variant;

    public RedVineBlock(Settings settings, Variant variant) {
        super(settings);
        this.variant = variant;
        this.setDefaultState(this.getDefaultState().with(AGE, 0).with(UP, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int a = state.get(AGE);
        if (a < MAX_AGE) {
            world.setBlockState(pos, withAge(a + 1), 3);
        }
        return ActionResult.SUCCESS;
    }

    public Variant getVariant() {
        return variant;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

    public BlockState withAge(int age) {
        return this.getDefaultState().with(AGE, age);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int j;
        int i = state.get(AGE) + this.getGrowthAmount(world);
        if (i > (j = MAX_AGE)) {
            i = j;
        }
        world.setBlockState(pos, this.withAge(i), Block.NOTIFY_LISTENERS);
    }

    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 1, 2);
    }
}
