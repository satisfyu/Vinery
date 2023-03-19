package satisfyu.vinery.block.stem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.GrapevineType;

import java.util.List;
import java.util.Objects;

public class PaleStemBlock extends StemBlock {

    private static final BooleanProperty HAS_GROWTH_LEAVES;
    private static final VoxelShape PALE_SHAPE = Block.createCuboidShape(6.0, 0,6.0, 10.0,  16.0, 10.0);
    public PaleStemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GRAPE, GrapevineType.NONE).with(AGE, 0).with(WATERLOGGED, false).with(HAS_GROWTH_LEAVES, false));
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
            dropGrapeSeeds(world, state, pos);
            world.setBlockState(pos, withAge(state, 0, GrapevineType.NONE), 3);
            world.playSound(player, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, SoundCategory.AMBIENT, 1.0F, 1.0F);
            return ActionResult.success(world.isClient);
        }

        final ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof GrapeBushSeedItem seed && hasTrunk(world, pos)) {
            if (age == 0) {
                if (seed.getType().isPaleType()) {
                    world.setBlockState(pos, withAge(state, 1, seed.getType()), 3);
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
            dropGrapeSeeds(world, state, pos);
            world.breakBlock(pos, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isMature(state) && hasTrunk(world, pos) && state.get(AGE) > 0) {
            final int i;
            if (world.getBaseLightLevel(pos, 0) >= 9 && (i = state.get(AGE)) < 4) {
                world.setBlockState(pos, this.withAge(state,i + 1, state.get(GRAPE)), Block.NOTIFY_LISTENERS);
            }
        }
        if (hasGrowthLeaves(state)) {
            return;
        }
        if (hasTrunk(world, pos) && state.get(AGE) > 2) {
            final BlockPos belowPos = pos.down();
            // Check four cardinal directions
            final int count = MathHelper.nextInt(world.random, 1, 4);
            for (int i = 0; i < count; i++) {
                final int dirId = MathHelper.nextInt(world.random, 3, 5);
                final Direction dir = Direction.byId(dirId);
                // This is not possible anyway
                if (dir == Direction.DOWN || dir == Direction.UP) {
                    continue;
                }
                final BlockPos offset = belowPos.offset(dir);
                if (world.getBlockState(offset).isAir()) {
                    world.setBlockState(offset, ObjectRegistry.GRAPEVINE_LEAVES.getDefaultState().with(Properties.PERSISTENT, true), Block.NOTIFY_LISTENERS);
                    world.setBlockState(pos, state.with(HAS_GROWTH_LEAVES, true));
                }
            }
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


    public boolean hasGrowthLeaves(BlockState state) {
        return state.get(HAS_GROWTH_LEAVES);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.stem.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HAS_GROWTH_LEAVES);
    }

    static {
        HAS_GROWTH_LEAVES = BooleanProperty.of("has_growth_leaves");
    }

    //TODO ausbreitende büsche richtig
}
