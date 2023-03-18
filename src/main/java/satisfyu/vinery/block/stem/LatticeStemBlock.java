package satisfyu.vinery.block.stem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.util.GrapevineType;

public class LatticeStemBlock extends StemBlock{

    private static final VoxelShape LATTICE_SHAPE_N = Block.createCuboidShape(6.0, 0,6.0, 10.0,  16.0, 10.0);
    private static final VoxelShape LATTICE_SHAPE_E = Block.createCuboidShape(6.0, 0,6.0, 10.0,  16.0, 10.0);
    private static final VoxelShape LATTICE_SHAPE_S = Block.createCuboidShape(6.0, 0,6.0, 10.0,  16.0, 10.0);
    private static final VoxelShape LATTICE_SHAPE_W = Block.createCuboidShape(6.0, 0,6.0, 10.0,  16.0, 10.0);
    public static final DirectionProperty FACING;

    public LatticeStemBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case EAST -> LATTICE_SHAPE_E;
            case SOUTH -> LATTICE_SHAPE_S;
            case WEST -> LATTICE_SHAPE_W;
            default -> LATTICE_SHAPE_N;
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState;
        blockState = this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);

        if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return blockState;
        }
        return null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hand == Hand.OFF_HAND) {
            return super.onUse(state, world, pos, player, hand, hit);
        }

        final int age = state.get(AGE);
        if (age > 0 && player.getStackInHand(hand).getItem() == Items.SHEARS) {
            if (age > 2) {
                dropGrapes(world, state, pos);
            }
            world.setBlockState(pos, withAge(0, GrapevineType.NONE), 3);
            world.playSound(player, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, SoundCategory.AMBIENT, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }

        final ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof GrapeBushSeedItem seed) {
            if (age == 0) {
                if (!seed.getType().isPaleType()) {
                    world.setBlockState(pos, withAge(1, seed.getType()), 3);
                    if (!player.isCreative()) {
                        stack.decrement(1);
                    }
                    world.playSound(player, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, SoundCategory.AMBIENT, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }
}
