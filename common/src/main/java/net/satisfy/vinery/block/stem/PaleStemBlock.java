package net.satisfy.vinery.block.stem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.vinery.config.VineryConfig;
import net.satisfy.vinery.item.GrapeBushSeedItem;
import net.satisfy.vinery.registry.GrapeTypeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class PaleStemBlock extends StemBlock {
    private static final VoxelShape PALE_SHAPE = Block.box(6.0, 0,6.0, 10.0,  16.0, 10.0);
    public PaleStemBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(GRAPE, GrapeTypeRegistry.NONE).setValue(AGE, 0));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return PALE_SHAPE;
    }

    @Nullable
    @Override
    @SuppressWarnings("unused")
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState;
        blockState = this.defaultBlockState();
        Level world = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
            return blockState;
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        if(livingEntity instanceof Player player){
            if (itemStack != null && (player.isCreative() || itemStack.getCount() >= 2) && level.getBlockState(blockPos.below()).getBlock() != this && blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced()) {
                level.setBlock(blockPos.above(), this.defaultBlockState(), 3);
                itemStack.shrink(1);
            }
        }
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionHand hand = player.getUsedItemHand();
        if (hand == InteractionHand.OFF_HAND) {
            return super.useWithoutItem(state, world, pos, player, hit);
        }

        final int age = state.getValue(AGE);
        if (age > 0 && player.getItemInHand(hand).getItem() == Items.SHEARS) {
            if (age > 2) {
                dropGrapes(world, state, pos, hit.getDirection());
            }
            dropGrapeSeeds(world, state, pos, hit.getDirection());
            world.setBlock(pos, withAge(state, 0, GrapeTypeRegistry.NONE), 3);
            world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_BREAK, SoundSource.AMBIENT, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        final ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof GrapeBushSeedItem seed && hasTrunk(world, pos)) {
            if (age == 0) {
                if (!seed.getType().isLattice()) {
                    world.setBlock(pos, withAge(state, 1, seed.getType()), 3);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PLACE, SoundSource.AMBIENT, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            if (state.getValue(AGE) > 0) {
                dropGrapeSeeds(world, state, pos, null);
            }
            if (state.getValue(AGE) > 2) {
                dropGrapes(world, state, pos, null);
            }
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        Random rand = new Random();
        if (!((rand.nextInt(100) + 1) <= VineryConfig.DEFAULT.getConfig().grapeGrowthSpeed())) return;
        if (!isMature(state) && hasTrunk(world, pos) && state.getValue(AGE) > 0) {
            final int i;
            if (world.getRawBrightness(pos, 0) >= 9 && (i = state.getValue(AGE)) < 4) {
                world.setBlock(pos, this.withAge(state,i + 1, state.getValue(GRAPE)), Block.UPDATE_CLIENTS);
            }
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).isRedstoneConductor(world, pos) || world.getBlockState(pos.below()).getBlock() == this;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }
}