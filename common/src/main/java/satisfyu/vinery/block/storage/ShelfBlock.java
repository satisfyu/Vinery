package satisfyu.vinery.block.storage;

import de.cristelknight.doapi.common.block.StorageBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.registry.StorageTypeRegistry;
import satisfyu.vinery.registry.TagRegistry;
import satisfyu.vinery.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ShelfBlock extends StorageBlock {

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.25, 0.5, 1, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.1875, 0.625, 0.1875, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.8125, 0.1875, 0.625, 0.875, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.0625, 0.9375, 0.1875, 0.25, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.8125, 0.0625, 0.9375, 0.875, 0.25, 1), BooleanOp.OR);

        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = net.minecraft.Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            map.put(direction, Util.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public ShelfBlock(Properties settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState;
        Direction side = ctx.getClickedFace();
        if (side != Direction.DOWN && side != Direction.UP) {
            blockState = this.defaultBlockState().setValue(FACING, ctx.getClickedFace());
        } else {
            blockState = this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
        }

        if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
            return blockState;
        }
        return null;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN};
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return !(stack.getItem() instanceof BlockItem) || stack.is(TagRegistry.IGNORE_BLOCK_ITEM);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public int size(){
        return 9;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.SHELF;
    }

    @Override
    public int getSection(Float f, Float y) {
        int nSection;
        float oneS = (float) 1 / 9;

        if (f < oneS) {
            nSection = 0;
        }
        else if(f < oneS*2){
            nSection = 1;
        }
        else if(f < oneS*3){
            nSection = 2;
        }
        else if(f < oneS*4){
            nSection = 3;
        }
        else if(f < oneS*5){
            nSection = 4;
        }
        else if(f < oneS*6){
            nSection = 5;
        }
        else if(f < oneS*7){
            nSection = 6;
        }
        else if(f < oneS*8){
            nSection = 7;
        }
        else nSection = 8;

        return 8 - nSection;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        VoxelShape shape;
        Direction direction;
        switch (state.getValue(FACING).getOpposite()) {
            case EAST -> {
                shape = world.getBlockState(pos.east()).getShape(world, pos.east());
                direction = Direction.WEST;
            }

            case SOUTH -> {
                shape = world.getBlockState(pos.south()).getShape(world, pos.south());
                direction = Direction.NORTH;
            }
            case WEST -> {
                shape = world.getBlockState(pos.west()).getShape(world, pos.west());
                direction = Direction.EAST;
            }
            default -> {
                shape = world.getBlockState(pos.north()).getShape(world, pos.north());
                direction = Direction.SOUTH;
            }
        }
        return Block.isFaceFull(shape, direction);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }
}
