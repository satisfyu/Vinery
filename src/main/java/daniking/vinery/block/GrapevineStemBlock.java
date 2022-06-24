package daniking.vinery.block;

import daniking.vinery.GrapeBushSeedItem;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

public class GrapevineStemBlock extends Block implements Waterloggable, Fertilizable {
    private static final VoxelShape SHAPE = Block.createCuboidShape(6.0, 0,6.0, 10.0,  16.0, 10.0);
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final int MAX_AGE = 4;
    private static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

    private enum GrapevineType implements StringIdentifiable {
        RED,
        WHITE,
        EMPTY;

        public String toString() {
            return this.asString();
        }
        @Override
        public String asString() {
            return this == RED ? "red" : this == WHITE ? "white" : "empty";
        }
    }

    private static final EnumProperty<GrapevineType> TYPE = EnumProperty.of("type", GrapevineType.class);

    public GrapevineStemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(AGE, 0).with(TYPE, GrapevineType.EMPTY));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hasTrunk(world, pos)) {
            final ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof GrapeBushSeedItem seed) {
                if (state.get(AGE) == 0) {
                    if (!player.isCreative()) {
                        stack.decrement(1);
                    }
                    int age = state.get(AGE);
                    if (age < MAX_AGE) {
                        world.setBlockState(pos, withAge(age + 1, seed.getType() == GrapeBush.Type.RED ? GrapevineType.RED : GrapevineType.WHITE), 3);
                        world.playSound(player, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, SoundCategory.AMBIENT, 1.0F, 1.0F);
                        return ActionResult.SUCCESS;
                    }
                }
            }

        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isMature(state)) return;
        if (hasTrunk(world, pos) && state.get(AGE) > 0) {
           final int i;
           if (world.getBaseLightLevel(pos, 0) >= 9 && (i = state.get(AGE)) < MAX_AGE) {
               world.setBlockState(pos, this.withAge(i + 1, state.get(TYPE)), Block.NOTIFY_LISTENERS);
           }
       }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        final BlockPos abovePos = pos.up();
        final Block aboveBlock = world.getBlockState(abovePos).getBlock();
        if (aboveBlock == this) {
            world.removeBlock(abovePos, false);
            world.spawnEntity(new ItemEntity(world, pos.getX(), abovePos.getY(), pos.getZ(), new ItemStack(this)));
        }
        super.onBreak(world, pos, state, player);
    }

    public boolean hasTrunk(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock() == this;
    }

    private void boneMealGrow(World world, BlockState state, BlockPos pos) {
        int j;
        int age = state.get(AGE) + MathHelper.nextInt(world.getRandom(), 1, 2);
        if (age > (j = MAX_AGE)) {
            age = j;
        }
        System.out.println(state.get(TYPE));
        world.setBlockState(pos, this.withAge(age, state.get(TYPE)), Block.NOTIFY_LISTENERS);
    }

    public BlockState withAge(int age, GrapevineType type) {
        return this.getDefaultState().with(AGE, age).with(TYPE, type);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER).with(AGE ,0).with(TYPE, GrapevineType.EMPTY);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AGE, TYPE);
    }

    public boolean isMature(BlockState state) {
        return state.get(AGE) >= MAX_AGE;
    }

    // Fertilizable Implementation
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
}
