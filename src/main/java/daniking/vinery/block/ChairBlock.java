package daniking.vinery.block;

import daniking.vinery.block.entity.chair.ChairEntity;
import daniking.vinery.block.entity.chair.ChairUtil;
import daniking.vinery.registry.VineryBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChairBlock extends Block {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    private static final VoxelShape[] SHAPE = {Block.createCuboidShape(3, 10, 11, 13, 22, 13),
            Block.createCuboidShape(11, 10, 3, 13, 22, 13),
            Block.createCuboidShape(3, 10, 3, 13, 22, 5),

            Block.createCuboidShape(3, 10, 3, 5, 22, 13)
    };

    public ChairBlock(Settings settings) {
        super(settings);
    }

    protected static VoxelShape SINGLE_SHAPE(){
        VoxelShape top = Block.createCuboidShape(3.0, 9.0, 3.0, 13.0, 10.0, 13.0);

        VoxelShape leg1 = Block.createCuboidShape(3.0, 0.0, 3.0, 5.0, 9.0, 5.0);
        VoxelShape leg2 = Block.createCuboidShape(3.0, 0.0, 11.0, 5.0, 9.0, 13.0);
        VoxelShape leg3 = Block.createCuboidShape(11.0, 0.0, 11.0, 13.0, 9.0, 13.0);
        VoxelShape leg4 = Block.createCuboidShape(11.0, 0.0, 3.0, 13.0, 9.0, 5.0);

        return VoxelShapes.union(top, leg1, leg2, leg3, leg4);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape s = SINGLE_SHAPE();

        switch (state.get(FACING)) {
            default: {
                return VoxelShapes.union(s, SHAPE[0]);
            }
            case WEST: {
                return VoxelShapes.union(s, SHAPE[1]);
            }
            case SOUTH: {
                return VoxelShapes.union(s, SHAPE[2]);
            }
            case EAST:
        }
        return VoxelShapes.union(s, SHAPE[3]);

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient) return ActionResult.PASS;
        if(player.isSneaking()) return ActionResult.PASS;
        if(ChairUtil.isPlayerSitting(player)) return ActionResult.PASS;
        if(hit.getSide() == Direction.DOWN) return ActionResult.PASS;
        BlockPos hitPos = hit.getBlockPos();
        if(!ChairUtil.isOccupied(world, hitPos) && player.getStackInHand(hand).isEmpty()) {
            ChairEntity chair = VineryBlockEntityTypes.CHAIR.create(world);
            float yaw = state.get(FACING).asRotation();
            chair.refreshPositionAndAngles(hitPos.getX() + 0.5D, hitPos.getY() + 0.25D, hitPos.getZ() + 0.5D, yaw, 0);
            if(ChairUtil.addChairEntity(world, hitPos, chair, player.getBlockPos())) {
                world.spawnEntity(chair);
                player.startRiding(chair);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(!world.isClient) {
            ChairEntity entity = ChairUtil.getChairEntity(world, pos);
            if(entity != null) {
                ChairUtil.removeChairEntity(world, pos);
                entity.removeAllPassengers();
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }


    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
}

