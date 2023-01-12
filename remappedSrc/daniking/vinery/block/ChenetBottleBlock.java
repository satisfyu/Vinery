package daniking.vinery.block;

import daniking.vinery.util.VineryUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChenetBottleBlock extends ChenetBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static VoxelShape SHAPE = Shapes.box(0.125, 0, 0.125, 0.875, 0.875, 0.875);

    private static final Supplier<VoxelShape> ONE_STACK_SHAPE_SUPPLIER = () -> Shapes.or(
            Shapes.box(0.44375, 0.45, 0.44375, 0.55625, 0.675, 0.55625),
            Shapes.box(0.44375, 0.7875, 0.44375, 0.55625, 0.84375, 0.55625),
            Shapes.box(0.33125, 0, 0.33125, 0.66875, 0.45, 0.66875),
            Shapes.box(0.275, 0.05625, 0.275, 0.725, 0.39375, 0.725),
            Shapes.box(0.2924375, 0.0961875, 0.2924375, 0.7075625, 0.3088125, 0.7075625),
            Shapes.box(0.28625, 0.0675, 0.28625, 0.71375, 0.3825, 0.71375),
            Shapes.box(0.3875, 0.675, 0.3875, 0.6125, 0.7875, 0.6125)
    );

    private static final Supplier<VoxelShape> TWO_STACK_SHAPE_SUPPLIER = () -> Shapes.or(
            Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.875, 0.8125)


    );

    public static final Map<Direction, VoxelShape> ONE_STACK_SHAPE = Util.make(new HashMap<>(), map ->
    {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList())
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, ONE_STACK_SHAPE_SUPPLIER.get()));
    });

    public static final Map<Direction, VoxelShape> TWO_STACK_SHAPE = Util.make(new HashMap<>(), map ->
    {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList())
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, TWO_STACK_SHAPE_SUPPLIER.get()));
    });

    public ChenetBottleBlock(Properties settings) {
        super(settings);
        registerDefaultState(this.defaultBlockState().setValue(STACK, 1).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, STACK);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options)
    {
        if (Screen.hasShiftDown())
        {
            // Add tooltip JSON key here
            tooltip.add(Component.translatable(""));
        }
        else
        {
            // Add tooltip JSON key here
            tooltip.add(Component.translatable(""));
        }

        super.appendHoverText(stack, world, tooltip, options);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {

        if (state.getValue(STACK) == 1)
        {
            return ONE_STACK_SHAPE.get(state.getValue(FACING));
        }
        else if (state.getValue(STACK) == 2)
        {
            return TWO_STACK_SHAPE.get(state.getValue(FACING));
        }

        return Shapes.block();
    }
}
