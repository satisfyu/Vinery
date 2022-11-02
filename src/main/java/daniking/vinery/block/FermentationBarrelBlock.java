package daniking.vinery.block;

import daniking.vinery.block.entity.FermentationBarrelBlockEntity;
import daniking.vinery.util.VineryUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FermentationBarrelBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4375, 1.0625, 0.4375, 0.5625, 1.25, 0.5625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5, 0.1875, 1.125, 0.5625, 0.6875, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0.1875, 0, 0.9375, 1.0625, 1), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.75, 0.9375, 0.125, 0.9375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.125, 0.25), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.125, 0.75, 0.875, 0.1875, 0.9375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.125, 0.0625, 0.875, 0.1875, 0.25), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.0625, 0.25, 0.1875, 0.125, 0.75), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.0625, 0.25, 0.875, 0.125, 0.75), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4375, 0.3125, 1, 0.625, 0.5, 1.25), BooleanBiFunction.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Type.HORIZONTAL.stream().toList()) {
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public FermentationBarrelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof NamedScreenHandlerFactory factory) {
            player.openHandledScreen(factory);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            final BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FermentationBarrelBlockEntity) {
                if (world instanceof ServerWorld) {
                    ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                }
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof BlockEntityTicker<?>) {
                ((BlockEntityTicker) blockEntity).tick(world, pos, state1, blockEntity);
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FermentationBarrelBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
