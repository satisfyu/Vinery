package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class RockBlock extends Block {

    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);

    public RockBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        final double n = Math.random();
        final BlockState toPlace;
        if (n < 0.33) {
            toPlace = this.getDefaultState();
        } else if (n < 0.66) {
            toPlace = ObjectRegistry.ROCKS_VARIANT_B.getDefaultState();
        } else {
            toPlace = ObjectRegistry.ROCKS_VARIANT_C.getDefaultState();
        }
        return toPlace;
    }

}
