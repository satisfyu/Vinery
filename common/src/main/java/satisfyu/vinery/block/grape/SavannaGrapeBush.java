package satisfyu.vinery.block.grape;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import satisfyu.vinery.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SavannaGrapeBush extends GrapeBush {

    public SavannaGrapeBush(Properties settings, GrapeType type) {
        super(settings, type, 3);
    }

    @Override
    public boolean canGrowPlace(LevelReader world, BlockPos blockPos, BlockState blockState) {
        return world.getRawBrightness(blockPos, 0) >= 14;
    }


    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.3125, 0.4375, 0.125, 0.8125, 0.5625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.875, 0.3125, 0.4375, 0.875, 0.8125, 0.5625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.8125, 0.4375, 0.875, 0.8125, 0.5625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0, 0.1875, 0.875, 0.4375, 0.8125), BooleanOp.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = net.minecraft.Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, Util.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(null);
    }
}
