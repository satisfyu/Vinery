package satisfyu.vinery.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.cristelknight.doapi.common.block.FacingBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.util.WineYears;

import java.util.List;
import java.util.Map;

public class CalendarBlock extends FacingBlock {
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(1, 1, 15.0, 15, 15, 16.0),
            Direction.SOUTH, Block.box(1, 1, 0.0, 15, 15, 1.0),
            Direction.WEST, Block.box(15.0, 1, 1, 16.0, 15, 15),
            Direction.EAST, Block.box(0.0, 1, 1, 1.0, 15, 15)
    ));

    public CalendarBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.decoration.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

        if (world instanceof Level) {
            Level level = (Level) world;
            long hour = (level.getDayTime() / 1000) % 24;
            long day = (level.getDayTime() / 24000) % VineryConfig.DEFAULT.getConfig().yearLengthInDays();
            tooltip.add(Component.literal(hour + "h / " + day + "d / " + WineYears.getYear(level) + "y").withStyle(ChatFormatting.YELLOW));
        }
    }
}
