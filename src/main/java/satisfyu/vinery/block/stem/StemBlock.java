package satisfyu.vinery.block.stem;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.GrapevineType;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.List;

public abstract class StemBlock extends Block implements Waterloggable, Fertilizable {
    private static final int MAX_AGE = 4;
    private static final EnumProperty<GrapevineType> GRAPE;
    public static final IntProperty AGE;
    private static final BooleanProperty HAS_GROWTH_LEAVES;
    private static final BooleanProperty WATERLOGGED;

    public StemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GRAPE, GrapevineType.NONE).with(AGE, 0).with(WATERLOGGED, false).with(HAS_GROWTH_LEAVES, false));
    }

    public void dropGrapes(World world, BlockState state, BlockPos pos) {
        final int x = 1 + world.random.nextInt(this.isMature(state) ? 2 : 1);
        final int bonus = this.isMature(state) ? 2 : 1;
        Item grape = switch (state.get(GRAPE)) {
            case RED -> ObjectRegistry.RED_GRAPE;
            case WHITE -> ObjectRegistry.WHITE_GRAPE;
            case JUNGLE_RED -> ObjectRegistry.JUNGLE_RED_GRAPE;
            case JUNGLE_WHITE -> ObjectRegistry.JUNGLE_WHITE_GRAPE;
            case TAIGA_RED -> ObjectRegistry.TAIGA_RED_GRAPE;
            case TAIGA_WHITE -> ObjectRegistry.TAIGA_WHITE_GRAPE;
            case SAVANNA_RED -> ObjectRegistry.SAVANNA_RED_GRAPE;
            case SAVANNA_WHITE -> ObjectRegistry.SAVANNA_WHITE_GRAPE;
            default -> null;
        };
        dropStack(world, pos, new ItemStack(grape, x + bonus));
        world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
        world.setBlockState(pos, state.with(AGE, 2), 2);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final int age = state.get(AGE);
        final ItemStack stack = player.getStackInHand(hand);
        if (!stack.isOf(Items.BONE_MEAL) && age > 2) {
            dropGrapes(world, state, pos);
            return ActionResult.success(world.isClient);
        }
        else if (age > 3) {
            dropGrapes(world, state, pos);
            return ActionResult.success(world.isClient);
        }
        else {
            return ActionResult.PASS;
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isMature(state) && hasTrunk(world, pos) && state.get(AGE) > 0) {
           final int i;
           if (world.getBaseLightLevel(pos, 0) >= 9 && (i = state.get(AGE)) < MAX_AGE) {
               world.setBlockState(pos, this.withAge(i + 1, state.get(GRAPE)), Block.NOTIFY_LISTENERS);
           }
       }
        if (hasGrowthLeaves(state)) {
            return;
        }
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

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(AGE) > 2) {
            dropGrapes(world, state, pos);
        }
        super.onBreak(world, pos, state, player);
    }

    public boolean hasGrowthLeaves(BlockState state) {
        return state.get(HAS_GROWTH_LEAVES);
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
        world.setBlockState(pos, this.withAge(age, state.get(GRAPE)), Block.NOTIFY_LISTENERS);
    }

    public BlockState withAge(int age, GrapevineType type) {
        return this.getDefaultState().with(AGE, age).with(GRAPE, type);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AGE, GRAPE, HAS_GROWTH_LEAVES);
    }

    public boolean isMature(BlockState state) {
        return state.get(AGE) >= MAX_AGE;
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

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.stem.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    static {
        GRAPE = EnumProperty.of("grape", GrapevineType.class);
        AGE = Properties.AGE_4;
        HAS_GROWTH_LEAVES = BooleanProperty.of("has_growth_leaves");
        WATERLOGGED = Properties.WATERLOGGED;
    }
}
