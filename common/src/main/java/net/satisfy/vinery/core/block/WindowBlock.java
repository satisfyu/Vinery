package net.satisfy.vinery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WindowBlock extends IronBarsBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 3);

    public WindowBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClientSide()) {
            this.updateWindows(world, pos, state);
        }

    }

    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    private void updateWindows(LevelAccessor world, BlockPos pos, BlockState state) {
        BlockPos lowest = this.getLowestWindow(world, pos);
        BlockPos highest = this.getHighestWindow(world, pos);
        int height = 0;

        BlockPos current;
        for(current = lowest; current.compareTo(highest) <= 0; current = current.above()) {
            ++height;
        }

        if (height == 1) {
            world.setBlock(lowest, state.setValue(PART, 0), 3);
        } else if (height == 2) {
            world.setBlock(lowest, state.setValue(PART, 1), 3);
            world.setBlock(highest, state.setValue(PART, 3), 3);
        } else {
            world.setBlock(lowest, state.setValue(PART, 1), 3);

            for(current = lowest.above(); current.compareTo(highest) < 0; current = current.above()) {
                world.setBlock(current, state.setValue(PART, 2), 3);
            }

            world.setBlock(highest, state.setValue(PART, 3), 3);
        }

    }

    private BlockPos getLowestWindow(LevelAccessor world, BlockPos pos) {
        while(world.getBlockState(pos.below()).getBlock() == this) {
            pos = pos.below();
        }

        return pos;
    }

    private BlockPos getHighestWindow(LevelAccessor world, BlockPos pos) {
        while(world.getBlockState(pos.above()).getBlock() == this) {
            pos = pos.above();
        }

        return pos;
    }

    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (!world.isClientSide()) {
            this.updatePartOnNeighborChange(world, pos, state);
        }

    }

    private void updatePartOnNeighborChange(Level world, BlockPos pos, BlockState state) {
        boolean hasBelow = world.getBlockState(pos.below()).getBlock() == this;
        boolean hasAbove = world.getBlockState(pos.above()).getBlock() == this;
        if (!hasBelow && !hasAbove) {
            world.setBlock(pos, state.setValue(PART, 0), 3);
        } else if (!hasBelow) {
            world.setBlock(pos, state.setValue(PART, 1), 3);
        } else if (!hasAbove) {
            world.setBlock(pos, state.setValue(PART, 3), 3);
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}

