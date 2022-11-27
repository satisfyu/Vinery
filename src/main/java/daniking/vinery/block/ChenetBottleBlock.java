package daniking.vinery.block;

import daniking.vinery.util.VineryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ChenetBottleBlock extends ChenetBlock {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static VoxelShape SHAPE = VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.875, 0.875);

    private static final Supplier<VoxelShape> ONE_STACK_SHAPE_SUPPLIER = () -> VoxelShapes.union(
            VoxelShapes.cuboid(0.44375, 0.45, 0.44375, 0.55625, 0.675, 0.55625),
            VoxelShapes.cuboid(0.44375, 0.7875, 0.44375, 0.55625, 0.84375, 0.55625),
            VoxelShapes.cuboid(0.33125, 0, 0.33125, 0.66875, 0.45, 0.66875),
            VoxelShapes.cuboid(0.275, 0.05625, 0.275, 0.725, 0.39375, 0.725),
            VoxelShapes.cuboid(0.2924375, 0.0961875, 0.2924375, 0.7075625, 0.3088125, 0.7075625),
            VoxelShapes.cuboid(0.28625, 0.0675, 0.28625, 0.71375, 0.3825, 0.71375),
            VoxelShapes.cuboid(0.3875, 0.675, 0.3875, 0.6125, 0.7875, 0.6125)
    );

    private static final Supplier<VoxelShape> TWO_STACK_SHAPE_SUPPLIER = () -> VoxelShapes.union(
            VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.875, 0.8125)


    );

    public static final Map<Direction, VoxelShape> ONE_STACK_SHAPE = Util.make(new HashMap<>(), map ->
    {
        for (Direction direction : Direction.Type.HORIZONTAL.stream().toList())
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, ONE_STACK_SHAPE_SUPPLIER.get()));
    });

    public static final Map<Direction, VoxelShape> TWO_STACK_SHAPE = Util.make(new HashMap<>(), map ->
    {
        for (Direction direction : Direction.Type.HORIZONTAL.stream().toList())
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, TWO_STACK_SHAPE_SUPPLIER.get()));
    });

    public ChenetBottleBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(STACK, 1).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, STACK);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options)
    {
        if (Screen.hasShiftDown())
        {
            // Add tooltip JSON key here
            tooltip.add(Text.translatable(""));
        }
        else
        {
            // Add tooltip JSON key here
            tooltip.add(Text.translatable(""));
        }

        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        if (state.get(STACK) == 1)
        {
            return ONE_STACK_SHAPE.get(state.get(FACING));
        }
        else if (state.get(STACK) == 2)
        {
            return TWO_STACK_SHAPE.get(state.get(FACING));
        }

        return VoxelShapes.fullCube();
    }
}
