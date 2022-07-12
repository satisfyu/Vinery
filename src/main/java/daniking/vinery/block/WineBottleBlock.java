package daniking.vinery.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WineBottleBlock extends GlassBlock {

    public static final VoxelShape SHAPE = VoxelShapes.union(
            createCuboidShape(6, 0, 6, 10, 9, 10),
            createCuboidShape(6.75, 9, 6.75, 9.25, 12.5, 9.25),
            createCuboidShape(6.35, 12.25, 6.35,9.65, 13.55, 9.65),
            createCuboidShape(7.25, 10.25, 7.25, 8.75, 11.75, 8.75)
    );
    public WineBottleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
