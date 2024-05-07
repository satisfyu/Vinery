package satisfyu.vinery.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.cristelknight.doapi.common.block.FacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.item.Calendar;

import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class CalendarBlock extends FacingBlock {
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(1, 1, 15.0, 15, 15, 16.0),
            Direction.SOUTH, Block.box(1, 1, 0.0, 15, 15, 1.0),
            Direction.WEST, Block.box(15.0, 1, 1, 16.0, 15, 15),
            Direction.EAST, Block.box(0.0, 1, 1, 1.0, 15, 15)
    ));

    public static final IntegerProperty MONTH = IntegerProperty.create("month", 0, 11);


    public CalendarBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(MONTH, 0));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getBoundingShape(state);
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockPos = pos.relative(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isFaceSturdy(world, blockPos, direction);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState();
        Level worldView = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        for (Direction direction : ctx.getNearestLookingDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = blockState.setValue(FACING, direction.getOpposite())).canSurvive(worldView, blockPos)) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isDiscrete()) return InteractionResult.PASS;
        player.getItemInHand(hand);
        if (world.isClientSide) {
            if (switchPages(world, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
        }
        return switchPages(world, pos, state, player);
    }


    private InteractionResult switchPages(LevelAccessor world, BlockPos pos, BlockState state, Player player) {
        world.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 0.5f, world.getRandom().nextFloat() * 0.1f + 0.9f);
        int currentMonth = state.getValue(MONTH);
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

        int newMonth = (currentMonth % 11) + 1;
        world.setBlock(pos, state.setValue(MONTH, newMonth), Block.UPDATE_ALL);

        return InteractionResult.SUCCESS;

    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MONTH);

    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        if (world instanceof Level level) {
            tooltip.add(Calendar.getTime(level));
        }
    }
}