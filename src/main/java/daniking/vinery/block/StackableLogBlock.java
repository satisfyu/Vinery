package daniking.vinery.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class StackableLogBlock extends SlabBlock implements Waterloggable {
    public static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;
    public static final BooleanProperty FIRED = BooleanProperty.of("fired");

    public StackableLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(FIRED, false).with(WATERLOGGED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final ItemStack stack = player.getStackInHand(hand);
        final SlabType stackSize = state.get(TYPE);
        if (stack.isOf(Items.FLINT_AND_STEEL) && stackSize == SlabType.DOUBLE) {
            world.setBlockState(pos, state.with(FIRED, true), Block.NOTIFY_ALL);
            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        } else if (stack.getItem() instanceof ShovelItem && stackSize == SlabType.DOUBLE) {
            world.setBlockState(pos, state.with(FIRED, false));
            world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            final boolean clientSide = world.isClient();
            if (clientSide) {
                for (int i = 0; i < 20; ++i) {
                    CampfireBlock.spawnSmokeParticle(world, pos, false, false);
                }
            }
            return ActionResult.success(clientSide);
        } else {
            return ActionResult.PASS;
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            return blockState.with(TYPE, SlabType.DOUBLE).with(FIRED, false).with(WATERLOGGED, false);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            Direction direction = ctx.getSide();
            return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().getY() - (double)blockPos.getY() > 0.5)) ? blockState2 : blockState2.with(TYPE, SlabType.TOP);
        }
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FIRED, Properties.WATERLOGGED);
    }
}
