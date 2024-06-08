package satisfyu.vinery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.Objects;

public class CherryLeaves extends LeavesBlock {
    public static final BooleanProperty VARIANT = BooleanProperty.create("can_have_cherries");
    public static final BooleanProperty HAS_CHERRIES = BooleanProperty.create("has_cherries");

    public CherryLeaves(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(PERSISTENT, false).setValue(DISTANCE, 7).setValue(VARIANT, false).setValue(HAS_CHERRIES, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(VARIANT) && state.getValue(HAS_CHERRIES)) {
            if (!world.isClientSide()) {
                int dropCount = world.getRandom().nextBoolean() ? Mth.nextInt(world.getRandom(), 1, 3) : 1;
                ItemStack dropStack = new ItemStack(ObjectRegistry.CHERRY.get(), dropCount);
                if (world.getRandom().nextInt(8) == 0) {
                    dropStack = new ItemStack(ObjectRegistry.ROTTEN_CHERRY.get(), dropCount);
                }
                CherryLeaves.popResourceFromFace(world, pos, hit.getDirection(), dropStack);
                world.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1F, 1F);
                world.setBlockAndUpdate(pos, state.setValue(HAS_CHERRIES, false));
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(VARIANT) && !state.getValue(HAS_CHERRIES) || super.isRandomlyTicking(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Player p = ctx.getPlayer();
        boolean b = p != null && p.isShiftKeyDown();
        return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(VARIANT, ctx.getPlayer().getAbilities().instabuild && b);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT, HAS_CHERRIES);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(VARIANT) && !state.getValue(HAS_CHERRIES) && random.nextFloat() < 0.1f) {
            world.setBlockAndUpdate(pos, state.setValue(HAS_CHERRIES, true));
        }
        super.randomTick(state, world, pos, random);
    }
}
