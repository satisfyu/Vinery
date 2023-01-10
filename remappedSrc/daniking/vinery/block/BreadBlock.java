package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.util.VineryTags;
import net.minecraft.ChatFormatting;
import net.minecraft.block.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.List;

public class BreadBlock extends FacingBlock {
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 3);
    public static final BooleanProperty JAM = BooleanProperty.create("jam");
    protected static final VoxelShape VOXEL_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public BreadBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(BITES, 0).setValue(FACING, Direction.NORTH).setValue(JAM, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return VOXEL_SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (world.isClientSide) {
            if (tryEat(world, pos, state, player, itemStack, hand).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            if (itemStack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }
        return tryEat(world, pos, state, player, itemStack, hand);
    }


    private static InteractionResult tryEat(LevelAccessor world, BlockPos pos, BlockState state, Player player, ItemStack stack, InteractionHand hand) {
        if (!player.canEat(false) && !(stack.is(VineryTags.JAMS))) {
            return InteractionResult.PASS;
        }
        if(stack.is(VineryTags.JAMS)){
            BreadBlock.popResourceFromFace((Level) world, pos, Direction.UP, new ItemStack(ObjectRegistry.BREAD_SLICE));
            stack.shrink(1);
            player.addItem(new ItemStack(ObjectRegistry.CHERRY_JAR));
        }
        else{
            player.getFoodData().eat(6, 0.6f);
            player.awardStat(Stats.EAT_CAKE_SLICE);
        }
        int i = state.getValue(BITES);
        world.gameEvent(player, GameEvent.EAT, pos);
        world.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (i < 3) {
            world.setBlock(pos, state.setValue(BITES, i + 1), Block.UPDATE_ALL);
        } else {
            world.removeBlock(pos, false);
            world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).getMaterial().isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES, JAM, FACING);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return net.minecraft.world.level.block.CakeBlock.getOutputSignal(state.getValue(BITES));
    }

    public static int getComparatorOutput(int bites) {
        return (7 - bites) * 2;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.breadblock.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("block.vinery.breadblock.tooltip.shift_1"));
            tooltip.add(Component.translatable("block.vinery.breadblock.tooltip.shift_2"));
        } else {
            tooltip.add(Component.translatable("block.vinery.breadblock.tooltip.tooltip_shift"));
        }
    }
}

