package net.satisfy.vinery.block;

import de.cristelknight.doapi.common.block.CabinetBlock;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StoragePotBlock extends CabinetBlock {
    public StoragePotBlock(Properties settings, Supplier<SoundEvent> openSound, Supplier<SoundEvent> closeSound) {
        super(settings, openSound, closeSound);
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0, 1, 0.5, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0, 0.0625, 0.5, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0, 0.9375, 0.625, 0.0625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.625, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.5, 0, 0.0625, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.5, 0, 1, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.5, 0.625, 0.0625, 0.625, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.5, 0.625, 1, 0.625, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.5625, 0.375, 0.0625, 0.625, 0.625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.5625, 0.375, 1, 0.625, 0.625), BooleanOp.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = net.minecraft.Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }
}
