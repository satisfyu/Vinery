package daniking.vinery.block;

import daniking.vinery.GrapeBushSeedItem;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.util.GrapevineType;
import net.minecraft.ChatFormatting;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class GrapevineStemBlock extends Block implements SimpleWaterloggedBlock, BonemealableBlock {
    private static final VoxelShape SHAPE = Block.box(6.0, 0,6.0, 10.0,  16.0, 10.0);
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final int MAX_AGE = 4;
    private static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);
    private static final EnumProperty<GrapevineType> TYPE = EnumProperty.create("type", GrapevineType.class);
    private static final BooleanProperty HAS_GROWTH_LEAVES = BooleanProperty.create("has_growth_leaves");

    public GrapevineStemBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(AGE, 0).setValue(HAS_GROWTH_LEAVES, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        final int age = state.getValue(AGE);
        if (hasTrunk(world, pos)) {
            final ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof GrapeBushSeedItem seed) {
                if (age == 0) {
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    world.setBlock(pos, withAge(age + 1, seed.getType() == GrapevineType.RED ? GrapevineType.RED : GrapevineType.WHITE), 3);
                    world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PLACE, SoundSource.AMBIENT, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            } else if (!stack.is(Items.BONE_MEAL) && age > 2) {
                dropGrapes(world, state, pos);
                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.PASS;
    }

    private void dropGrapes(Level world, BlockState state, BlockPos pos) {
        final int x = 1 + world.random.nextInt(this.isMature(state) ? 2 : 1);
        final int bonus = this.isMature(state) ? 2 : 1;
        popResource(world, pos, new ItemStack(state.getValue(TYPE) == GrapevineType.RED ? ObjectRegistry.RED_GRAPE : ObjectRegistry.WHITE_GRAPE, x + bonus));
        world.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
        world.setBlock(pos, state.setValue(AGE, 2), 2);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!isMature(state) && hasTrunk(world, pos) && state.getValue(AGE) > 0) {
           final int i;
           if (world.getRawBrightness(pos, 0) >= 9 && (i = state.getValue(AGE)) < MAX_AGE) {
               world.setBlock(pos, this.withAge(i + 1, state.getValue(TYPE)), Block.UPDATE_CLIENTS);
           }
       }
        if (hasGrowthLeaves(state)) return;
        if (hasTrunk(world, pos) && state.getValue(AGE) > 2) {
            final BlockPos belowPos = pos.below();
            // Check four cardinal directions
            final int count = Mth.nextInt(world.random, 1, 4);
            for (int i = 0; i < count; i++) {
                final int dirId = Mth.nextInt(world.random, 3, 5);
                final Direction dir = Direction.from3DDataValue(dirId);
                // This is not possible anyway
                if (dir == Direction.DOWN || dir == Direction.UP) {
                    continue;
                }
                final BlockPos offset = belowPos.relative(dir);
                if (world.getBlockState(offset).isAir()) {
                    world.setBlock(offset, ObjectRegistry.GRAPEVINE_LEAVES.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true), Block.UPDATE_CLIENTS);
                    world.setBlockAndUpdate(pos, state.setValue(HAS_GROWTH_LEAVES, true));
                }
            }
        }
    }
    public boolean hasGrowthLeaves(BlockState state) {
        return state.getValue(HAS_GROWTH_LEAVES);
    }
    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(AGE) > 2) {
            dropGrapes(world, state, pos);
        }
        final BlockPos abovePos = pos.above();
        final BlockState aboveState = world.getBlockState(abovePos);
        if (aboveState.getBlock() == this) {
            if (aboveState.getValue(AGE) > 2) {
                dropGrapes(world, aboveState, pos);
            }
            world.removeBlock(abovePos, false);
            world.addFreshEntity(new ItemEntity(world, pos.getX(), abovePos.getY(), pos.getZ(), new ItemStack(this)));
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    public boolean hasTrunk(Level world, BlockPos pos) {
        return world.getBlockState(pos.below()).getBlock() == this;
    }

    private void boneMealGrow(Level world, BlockState state, BlockPos pos) {
        int j;
        int age = state.getValue(AGE) + Mth.nextInt(world.getRandom(), 1, 2);
        if (age > (j = MAX_AGE)) {
            age = j;
        }
        world.setBlock(pos, this.withAge(age, state.getValue(TYPE)), Block.UPDATE_CLIENTS);
    }

    public BlockState withAge(int age, GrapevineType type) {
        return this.defaultBlockState().setValue(AGE, age).setValue(TYPE, type);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(BlockStateProperties.WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER).setValue(AGE ,0).setValue(HAS_GROWTH_LEAVES, false);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AGE, TYPE, HAS_GROWTH_LEAVES);
    }

    public boolean isMature(BlockState state) {
        return state.getValue(AGE) >= MAX_AGE;
    }

    // Fertilizable Implementation
    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return !isMature(state) && world.getBlockState(pos.below()).getBlock() == this && state.getValue(AGE) > 0;
    }
    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        boneMealGrow(world, state, pos);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.stem.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
