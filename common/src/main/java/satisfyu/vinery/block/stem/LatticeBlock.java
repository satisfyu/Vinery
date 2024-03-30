package satisfyu.vinery.block.stem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.util.ConnectingProperties;
import satisfyu.vinery.util.LineConnectingType;

import java.util.Random;

public class LatticeBlock extends StemBlock {
    public static final BooleanProperty SUPPORT = BooleanProperty.create("support");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

    protected static final VoxelShape EAST = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH = Block.box(0.0D, 0.0D, 0.01D, 16.0D, 16.0D, 2.0D);
    protected static final VoxelShape NORTH = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape FLOOR = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);


    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<LineConnectingType> TYPE = ConnectingProperties.VINERY_LINE_CONNECTING_TYPE;

    private static final SoundEvent BREAK_SOUND_EVENT = SoundEvents.SWEET_BERRY_BUSH_BREAK;
    private static final SoundEvent PLACE_SOUND_EVENT = SoundEvents.SWEET_BERRY_BUSH_PLACE;

    public LatticeBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(SUPPORT, true)
                .setValue(BOTTOM, false)
                .setValue(TYPE, LineConnectingType.NONE));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        Direction facing = context.getHorizontalDirection().getOpposite();

        BlockPos clickedPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockPos clickedFacingPos = clickedPos.relative(clickedFace.getOpposite());
        BlockState clickedFacingState = level.getBlockState(clickedFacingPos);

        if (context.getPlayer() != null && !context.getPlayer().isCrouching() && clickedFacingState.getBlock() instanceof LatticeBlock) {
            Direction clickedFacingFace = clickedFacingState.getValue(FACING);
            if (clickedFacingFace != clickedFace && clickedFacingFace.getOpposite() != clickedFace) facing = clickedFacingFace;
        }
        boolean bottom = clickedFace == Direction.DOWN || clickedFace == Direction.UP;
        BlockState state = this.defaultBlockState().setValue(FACING, facing).setValue(BOTTOM, bottom);
        if (!bottom) {
            state = getConnection(this.defaultBlockState().setValue(FACING, facing), level, clickedPos);
        }
        return state;
    }


    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (Boolean.TRUE.equals(state.getValue(BOTTOM))) return FLOOR;
        return switch (state.getValue(FACING)) {
            case WEST -> WEST;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            default -> NORTH;
        };
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide && player.getItemInHand(hand).getItem() instanceof AxeItem) {
            BlockState newState = state.setValue(SUPPORT, !state.getValue(SUPPORT));
            BlockState updateState = getConnection(newState, world, pos);
            world.setBlock(pos, updateState, 3);
            return InteractionResult.SUCCESS;
        }
        final ItemStack stack = player.getItemInHand(hand);
        final int age = state.getValue(AGE);

        if (hand == InteractionHand.OFF_HAND) {
            return super.use(state, world, pos, player, hand, hit);
        }

        if (age > 0 && stack.getItem() == Items.SHEARS) {
            stack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(player.getUsedItemHand()));
            if (age > 2) {
                dropGrapes(world, state, pos);
            }
            dropGrapeSeeds(world, state, pos);
            world.setBlock(pos, state.setValue(AGE, 0), 3);
            world.playSound(player, pos, BREAK_SOUND_EVENT, SoundSource.AMBIENT, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        else if (stack.getItem() instanceof GrapeBushSeedItem seed && seed.getType().isLattice() && age == 0) {
            world.setBlock(pos, withAge(state, 1, seed.getType()), 3);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            world.playSound(player, pos, PLACE_SOUND_EVENT, SoundSource.AMBIENT, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        else if (age > 2) {
            stack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(player.getUsedItemHand()));
            dropGrapes(world, state, pos);
            world.setBlock(pos, state.setValue(AGE, 1), 3);
            world.playSound(player, pos, BREAK_SOUND_EVENT, SoundSource.AMBIENT, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        Random rand = new Random();
        if (!((rand.nextInt(100) + 1) <= VineryConfig.DEFAULT.getConfig().grapeGrowthSpeed() || isMature(state))) return;
        int age = state.getValue(AGE);
        BlockState newState = this.withAge(state, age + 1, state.getValue(GRAPE));
        world.setBlock(pos, newState, Block.UPDATE_CLIENTS);
        super.randomTick(state, world, pos, random);
    }


    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            if (state.getValue(AGE) > 0) {
                dropGrapeSeeds(world, state, pos);
            }
            if (state.getValue(AGE) > 2) {
                dropGrapes(world, state, pos);
            }
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {

        int age = state.getValue(AGE);


        return (!isMature(state) && age > 0 && age < 4);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return getConnection(state, world, pos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState state, boolean bl) {
        return !isMature(state) && state.getValue(AGE) > 0;
    }

    public BlockState getConnection(BlockState state, LevelAccessor level, BlockPos currentPos) {
        Direction facing = state.getValue(FACING);

        BlockState stateL = level.getBlockState(currentPos.relative(facing.getClockWise()));
        BlockState stateR = level.getBlockState(currentPos.relative(facing.getCounterClockWise()));

        boolean sideL = (stateL.getBlock() instanceof LatticeBlock && (stateL.getValue(FACING) == facing || stateL.getValue(FACING) == facing.getClockWise()));
        boolean sideR = (stateR.getBlock() instanceof LatticeBlock && (stateR.getValue(FACING) == facing || stateR.getValue(FACING) == facing.getCounterClockWise()));
        LineConnectingType type = sideL && sideR ?
                LineConnectingType.MIDDLE
                : (sideR ? LineConnectingType.LEFT
                : (sideL ? LineConnectingType.RIGHT
                : LineConnectingType.NONE));

        return state.setValue(TYPE, type);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, TYPE, SUPPORT, BOTTOM);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
