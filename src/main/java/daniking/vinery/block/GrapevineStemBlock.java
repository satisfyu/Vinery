package daniking.vinery.block;

import daniking.vinery.GrapeBushSeedItem;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.util.GrapevineType;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GrapevineStemBlock extends Block implements Waterloggable, Fertilizable {
    private static final VoxelShape SHAPE = Block.createCuboidShape(6.0, 0,6.0, 10.0,  16.0, 10.0);
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final int MAX_AGE = 4;
    private static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
    private static final EnumProperty<GrapevineType> TYPE = EnumProperty.of("type", GrapevineType.class);
    private static final BooleanProperty HAS_GROWTH_LEAVES = BooleanProperty.of("has_growth_leaves");

    public GrapevineStemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(AGE, 0).with(HAS_GROWTH_LEAVES, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final int age = state.get(AGE);
        if (hasTrunk(world, pos)) {
            final ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof GrapeBushSeedItem seed) {
                if (age == 0) {
                    if (!player.isCreative()) {
                        stack.decrement(1);
                    }
                    world.setBlockState(pos, withAge(age + 1, seed.getType() == GrapevineType.RED ? GrapevineType.RED : GrapevineType.WHITE), 3);
                    world.playSound(player, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, SoundCategory.AMBIENT, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            } else if (!stack.isOf(Items.BONE_MEAL) && age > 2) {
                dropGrapes(world, state, pos);
                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }

    private void dropGrapes(World world, BlockState state, BlockPos pos) {
        final int x = 1 + world.random.nextInt(this.isMature(state) ? 2 : 1);
        final int bonus = this.isMature(state) ? 2 : 1;
        dropStack(world, pos, new ItemStack(state.get(TYPE) == GrapevineType.RED ? ObjectRegistry.RED_GRAPE : ObjectRegistry.WHITE_GRAPE, x + bonus));
        world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
        world.setBlockState(pos, state.with(AGE, 2), 2);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isMature(state) && hasTrunk(world, pos) && state.get(AGE) > 0) {
           final int i;
           if (world.getBaseLightLevel(pos, 0) >= 9 && (i = state.get(AGE)) < MAX_AGE) {
               world.setBlockState(pos, this.withAge(i + 1, state.get(TYPE)), Block.NOTIFY_LISTENERS);
           }
       }
        if (hasGrowthLeaves(state)) return;
        if (hasTrunk(world, pos) && state.get(AGE) > 2) {
            final BlockPos belowPos = pos.down();
            // Check four cardinal directions
            final int count = MathHelper.nextInt(world.random, 1, 4);
            for (int i = 0; i < count; i++) {
                final int dirId = MathHelper.nextInt(world.random, 3, 5);
                final Direction dir = Direction.byId(dirId);
                // This is not possible anyway
                if (dir == Direction.DOWN || dir == Direction.UP) {
                    continue;
                }
                final BlockPos offset = belowPos.offset(dir);
                if (world.getBlockState(offset).isAir()) {
                    world.setBlockState(offset, ObjectRegistry.GRAPEVINE_LEAVES.getDefaultState().with(Properties.PERSISTENT, true), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos, state.with(HAS_GROWTH_LEAVES, true));
                }
            }
        }
    }
    public boolean hasGrowthLeaves(BlockState state) {
        return state.get(HAS_GROWTH_LEAVES);
    }
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(AGE) > 2) {
            dropGrapes(world, state, pos);
        }
        final BlockPos abovePos = pos.up();
        final BlockState aboveState = world.getBlockState(abovePos);
        if (aboveState.getBlock() == this) {
            if (aboveState.get(AGE) > 2) {
                dropGrapes(world, aboveState, pos);
            }
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
        world.setBlockState(pos, this.withAge(age, state.get(TYPE)), Block.NOTIFY_LISTENERS);
    }

    public BlockState withAge(int age, GrapevineType type) {
        return this.getDefaultState().with(AGE, age).with(TYPE, type);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER).with(AGE ,0).with(HAS_GROWTH_LEAVES, false);
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
        builder.add(WATERLOGGED, AGE, TYPE, HAS_GROWTH_LEAVES);
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
