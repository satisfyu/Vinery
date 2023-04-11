package satisfyu.vinery.block.stem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.util.GrapevineType;

import java.util.List;

public class LatticeStemBlock extends StemBlock {

    private static final VoxelShape LATTICE_SHAPE_N = Block.createCuboidShape(0, 0,15.0, 16.0,  16.0, 16.0);
    private static final VoxelShape LATTICE_SHAPE_E = Block.createCuboidShape(0, 0,0, 1.0,  16.0, 16.0);
    private static final VoxelShape LATTICE_SHAPE_S = Block.createCuboidShape(0, 0,0, 16.0,  16.0, 1.0);
    private static final VoxelShape LATTICE_SHAPE_W = Block.createCuboidShape(15.0, 0,0, 16.0,  16.0, 16.0);
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
        Direction side = ctx.getSide();
        if (side != Direction.DOWN && side != Direction.UP) {
            blockState = this.getDefaultState().with(FACING, ctx.getSide());
        } else {
            blockState = this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
        }


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
            dropGrapeSeeds(world, state, pos);
            world.setBlockState(pos, withAge(state,0, GrapevineType.NONE), 3);
            world.playSound(player, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, SoundCategory.AMBIENT, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }

        final ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof GrapeBushSeedItem seed) {
            if (age == 0) {
                if (!seed.getType().isPaleType()) {
                    world.setBlockState(pos, withAge(state,1, seed.getType()), 3);
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
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isMature(state) && state.get(AGE) > 0) {
            final int i;
            if (world.getBaseLightLevel(pos, 0) >= 9 && (i = state.get(AGE)) < 4) {
                world.setBlockState(pos, this.withAge(state,i + 1, state.get(GRAPE)), Block.NOTIFY_LISTENERS);
            }
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            if (state.get(AGE) > 2) {
                dropGrapes(world, state, pos);
            }
            dropGrapeSeeds(world, state, pos);
            world.breakBlock(pos, true);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return switch (state.get(FACING).getOpposite()) {
            case EAST -> world.getBlockState(pos.east()).isSolidBlock(world, pos);
            case SOUTH -> world.getBlockState(pos.south()).isSolidBlock(world, pos);
            case WEST -> world.getBlockState(pos.west()).isSolidBlock(world, pos);
            default -> world.getBlockState(pos.north()).isSolidBlock(world, pos);
        };
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return !isMature(state) && state.get(AGE) > 0;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.lattice.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable( "item.vinery.lattice2.tooltip"));
        } else {
            tooltip.add(Text.translatable("item.vinery.faucet.tooltip"));
        }
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
