package satisfyu.vinery.block;

import net.minecraft.state.property.DirectionProperty;
import satisfyu.vinery.registry.DamageSourceRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


import java.util.List;

public class StackableLogBlock extends SlabBlock implements Waterloggable {
    public static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;
    public static final BooleanProperty FIRED = BooleanProperty.of("fired");
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public StackableLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(FIRED, false).with(WATERLOGGED, false).with(FACING, Direction.NORTH));
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final ItemStack stack = player.getStackInHand(hand);
        final SlabType stackSize = state.get(TYPE);
        if (stack.isOf(Items.FLINT_AND_STEEL) && stackSize == SlabType.DOUBLE) {
            world.setBlockState(pos, state.with(FIRED, true), Block.NOTIFY_ALL);
            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        } else if (stack.getItem() instanceof ShovelItem && stackSize == SlabType.DOUBLE && state.get(FIRED)) {
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
            return blockState.with(TYPE, SlabType.DOUBLE).with(FIRED, false).with(WATERLOGGED, false).with(FACING, ctx.getPlayerFacing().getOpposite());
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            Direction direction = ctx.getSide();
            return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().getY() - (double)blockPos.getY() > 0.5)) ? blockState2 : blockState2.with(TYPE, SlabType.TOP);
        }
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FIRED, FACING, Properties.WATERLOGGED);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if ((state.get(FIRED))) displayTickLikeCampfire(state, world, pos, random, world.getBlockState(pos.down()).isOf(Blocks.HAY_BLOCK));
    }

    public static void displayTickLikeCampfire(BlockState state, World world, BlockPos pos, Random random, boolean isSignal){
        if (random.nextFloat() < 0.80f) {
            for (int i = 0; i < random.nextInt(5) + 3; ++i) {
                CampfireBlock.spawnSmokeParticle(world, pos, isSignal, true);

            }

        }
        if (random.nextInt(10) == 0) {
            world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5f + random.nextFloat(), random.nextFloat() * 0.7f + 0.6f, false);
        }
        if (random.nextInt(5) == 0) {
            for (int i = 0; i < random.nextInt(4) + 3; ++i) {
                world.addParticle(ParticleTypes.LAVA, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, random.nextFloat() / 2.0f, 5.0E-5, random.nextFloat() / 2.0f);
            }
        }
    }




    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.log.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("block.vinery.log.tooltip.shift_1"));
            tooltip.add(Text.translatable("block.vinery.log.tooltip.shift_2"));
        } else {
            tooltip.add(Text.translatable("block.vinery.log.tooltip.tooltip_shift"));
        }
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        boolean isLit = world.getBlockState(pos).get(FIRED);
        if (isLit && !entity.isFireImmune() && entity instanceof LivingEntity livingEntity &&
                !EnchantmentHelper.hasFrostWalker(livingEntity)) {
            entity.damage(DamageSourceRegistry.STOVE_BLOCK, 1.f);
        }

        super.onSteppedOn(world, pos, state, entity);
    }
}
