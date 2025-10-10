package net.satisfy.vinery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class DarkCherryLeavesBlock extends LeavesBlock implements BonemealableBlock {
    public static final BooleanProperty CAN_GROW_CHERRIES = BooleanProperty.create("can_grow_cherries");
    public static final BooleanProperty HAS_CHERRIES = BooleanProperty.create("has_cherries");
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    public DarkCherryLeavesBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PERSISTENT, false)
                .setValue(DISTANCE, 7)
                .setValue(CAN_GROW_CHERRIES, false)
                .setValue(HAS_CHERRIES, false)
                .setValue(AGE, 0)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (state.getValue(HAS_CHERRIES) && state.getValue(AGE) == 3) {
            if (!world.isClientSide()) {
                int dropCount = world.getRandom().nextBoolean() ? world.getRandom().nextInt(1, 4) : 1;
                ItemStack dropStack = new ItemStack(ObjectRegistry.CHERRY.get(), dropCount);
                DarkCherryLeavesBlock.popResourceFromFace(world, pos, hit.getDirection(), dropStack);
                world.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1F, 1F);
                world.setBlock(pos, state.setValue(HAS_CHERRIES, false).setValue(AGE, 1), 2);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        boolean canGrow = state.getValue(CAN_GROW_CHERRIES);
        boolean has = state.getValue(HAS_CHERRIES);
        int age = state.getValue(AGE);
        return (canGrow && !has && age < 2) || (has && age == 2) || super.isRandomlyTicking(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean canGrowCherries = ctx.getLevel().random.nextFloat() < 0.3f;
        return updateDistance(this.defaultBlockState()
                .setValue(PERSISTENT, true)
                .setValue(CAN_GROW_CHERRIES, canGrowCherries)
                .setValue(AGE, 0)
                .setValue(HAS_CHERRIES, false)
                .setValue(WATERLOGGED, false), ctx.getLevel(), ctx.getClickedPos());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CAN_GROW_CHERRIES, HAS_CHERRIES, AGE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);

        boolean canGrow = state.getValue(CAN_GROW_CHERRIES);
        boolean has = state.getValue(HAS_CHERRIES);
        int age = state.getValue(AGE);

        double chance = PlatformHelper.getAppleGrowthChance();
        if (canGrow && !has && age < 2 && random.nextDouble() < chance && canGrowPlace(world, pos)) {
            BlockState newState = age == 0 ? state.setValue(AGE, 1) : state.setValue(AGE, 2).setValue(HAS_CHERRIES, true);
            world.setBlock(pos, newState, 2);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        } else if (has && age == 2 && random.nextDouble() < chance && canGrowPlace(world, pos)) {
            BlockState newState = state.setValue(AGE, 3);
            world.setBlock(pos, newState, 2);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        }
    }

    private boolean canGrowPlace(LevelReader world, BlockPos pos) {
        return world.getRawBrightness(pos, 0) > 9;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        boolean has = state.getValue(HAS_CHERRIES);
        return (age < 2 && !has) || (age == 2 && has);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockState s = state;
        if (!s.getValue(CAN_GROW_CHERRIES)) {
            s = s.setValue(CAN_GROW_CHERRIES, true);
        }
        int age = s.getValue(AGE);
        boolean has = s.getValue(HAS_CHERRIES);

        if (!has && age < 2 && canGrowPlace(level, pos)) {
            BlockState newState = (age == 0)
                    ? s.setValue(AGE, 1)
                    : s.setValue(AGE, 2).setValue(HAS_CHERRIES, true);
            level.setBlock(pos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        } else if (has && age == 2 && canGrowPlace(level, pos)) {
            BlockState newState = s.setValue(AGE, 3);
            level.setBlock(pos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return updateDistance(state, world, pos);
    }

    private static int getDistanceAt(BlockState neighborState) {
        return neighborState.getBlock() instanceof LeavesBlock ? neighborState.getValue(DISTANCE) : (neighborState.is(BlockTags.LOGS) ? 0 : 7);
    }

    private BlockState updateDistance(BlockState state, LevelAccessor world, BlockPos pos) {
        int minDistance = 7;
        for (Direction dir : Direction.values()) {
            BlockState neighbor = world.getBlockState(pos.relative(dir));
            minDistance = Math.min(minDistance, getDistanceAt(neighbor) + 1);
            if (minDistance == 1) break;
        }
        return state.setValue(DISTANCE, minDistance);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 1;
    }
}
