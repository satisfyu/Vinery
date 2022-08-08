package daniking.vinery.block;

import daniking.vinery.util.WineType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SmallDripleafBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class WineBottleBlock extends StackableBlock {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    private final WineType type;
    public WineBottleBlock(Settings settings, WineType type) {
        super(settings);
        this.type = type;
        setDefaultState(this.getDefaultState().with(STACK, 1).with(FACING, Direction.NORTH));
    }

    public WineType getType() {
        return type;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, STACK);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }
}
