package satisfyu.vinery.block;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.VineryTags;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class BreadBlock extends FacingBlock {
    public static final IntProperty BITES = IntProperty.of("bites", 0, 3);
    public static final BooleanProperty JAM = BooleanProperty.of("jam");
    protected static final VoxelShape VOXEL_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public BreadBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(BITES, 0).with(FACING, Direction.NORTH).with(JAM, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (world.isClient) {
            if (tryEat(world, pos, state, player, itemStack, hand).isAccepted()) {
                return ActionResult.SUCCESS;
            }
            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return tryEat(world, pos, state, player, itemStack, hand);
    }


    private static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player, ItemStack stack, Hand hand) {
        if (!player.canConsume(false) && !(stack.isIn(VineryTags.JAMS))) {
            return ActionResult.PASS;
        }
        if(stack.isIn(VineryTags.JAMS)){
            BreadBlock.dropStack((World) world, pos, Direction.UP, new ItemStack(ObjectRegistry.BREAD_SLICE));
            stack.decrement(1);
            player.giveItemStack(new ItemStack(ObjectRegistry.CHERRY_JAR));
        }
        else{
            player.getHungerManager().add(6, 0.6f);
            player.incrementStat(Stats.EAT_CAKE_SLICE);
        }
        int i = state.get(BITES);
        world.emitGameEvent(player, GameEvent.EAT, pos);
        world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
        if (i < 3) {
            world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_ALL);
        } else {
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BITES, JAM, FACING);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return net.minecraft.block.CakeBlock.getComparatorOutput(state.get(BITES));
    }

    public static int getComparatorOutput(int bites) {
        return (7 - bites) * 2;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.breadblock.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("block.vinery.breadblock.tooltip.shift_1"));
            tooltip.add(Text.translatable("block.vinery.breadblock.tooltip.shift_2"));
        } else {
            tooltip.add(Text.translatable("block.vinery.breadblock.tooltip.tooltip_shift"));
        }
    }
}

