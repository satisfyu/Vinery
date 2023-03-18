package satisfyu.vinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import satisfyu.vinery.block.entity.WineBottleBlockEntity;

public class StackableBlock extends Block{
    private final VoxelShape SHAPE = VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.875, 0.8125);
    public static final IntProperty STACK = IntProperty.of("stack", 1, 3);

    public StackableBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(STACK, 1));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == this.asItem()) {
            if (state.getBlock() instanceof StackableBlock && state.get(STACK) < 3) {
                world.setBlockState(pos, state.with(STACK, state.get(STACK) + 1), Block.NOTIFY_ALL);
                if (!player.isCreative()) {
                    stack.decrement(1);
                }
                return ActionResult.SUCCESS;
            }
        } else if (stack.isEmpty()) {
            if (state.get(STACK) > 1) {
                world.setBlockState(pos, state.with(STACK, state.get(STACK) - 1), Block.NOTIFY_ALL);
            } else if (state.get(STACK) == 1) {
                world.breakBlock(pos, false);
            }
            player.giveItemStack(this.asItem().getDefaultStack());
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }


    
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STACK);
    }
}
