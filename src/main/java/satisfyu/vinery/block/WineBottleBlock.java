package satisfyu.vinery.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.block.entity.WineBottleBlockEntity;

public class WineBottleBlock extends Block  implements BlockEntityProvider {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.875, 0.875);
    public static final IntProperty COUNT = IntProperty.of("count", 0, 3);
    private final int maxCount;

    public WineBottleBlock(Settings settings, int maxCount) {
        super(settings);
        this.maxCount = maxCount;
        setDefaultState(this.getDefaultState().with(COUNT, 1).with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final ItemStack stack = player.getStackInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (stack.getItem() == this.asItem()) {
            if (blockEntity instanceof WineBottleBlockEntity wineEntity && !wineEntity.isFull()) {
                wineEntity.addWine();
                world.setBlockState(pos, state.with(COUNT, state.get(COUNT) + 1), Block.NOTIFY_ALL);
                if (!player.isCreative()) {
                    stack.decrement(1);
                }
                return ActionResult.SUCCESS;
            }
        } else if (stack.isEmpty()) {
            if (blockEntity instanceof WineBottleBlockEntity wineEntity && wineEntity.getCount() >= 1) {
                wineEntity.removeWine();
                player.giveItemStack(this.asItem().getDefaultStack());
                if (wineEntity.getCount() == 0) {
                    world.breakBlock(pos, false);
                }
                else {
                    world.setBlockState(pos, state.with(COUNT, state.get(COUNT) - 1), Block.NOTIFY_ALL);
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(COUNT, 1).with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WineBottleBlockEntity wineEntity) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this, wineEntity.getCount()));
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COUNT, FACING);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WineBottleBlockEntity(pos, state, maxCount);
    }
}
