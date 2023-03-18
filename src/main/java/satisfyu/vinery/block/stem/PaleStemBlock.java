package satisfyu.vinery.block.stem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.util.GrapevineType;

import java.util.Objects;

public class PaleStemBlock extends StemBlock{

    private static final VoxelShape PALE_SHAPE = Block.createCuboidShape(6.0, 0,6.0, 10.0,  16.0, 10.0);
    public PaleStemBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return PALE_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState;
        blockState = this.getDefaultState().with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            ItemStack placeStack = Objects.requireNonNull(ctx.getPlayer()).getStackInHand(ctx.getHand());
            if (placeStack != null && (ctx.getPlayer().isCreative() || placeStack.getCount() >= 2) && world.getBlockState(blockPos.down()).getBlock() != this && blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
                world.setBlockState(blockPos.up(), this.getDefaultState().with(Properties.WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER), 3);
                placeStack.decrement(1);
            }
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
            return ActionResult.success(world.isClient);
        }

        final ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof GrapeBushSeedItem seed && hasTrunk(world, pos)) {
            if (age == 0) {
                if (seed.getType().isPaleType()) {
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
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            if (state.get(AGE) > 2) {
                dropGrapes(world, state, pos);
            }
            world.breakBlock(pos, true);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolidBlock(world, pos) || world.getBlockState(pos.down()).getBlock() == this;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    //TODO ausbreitende büsche richtig
}
