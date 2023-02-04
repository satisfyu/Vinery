package satisfyu.vinery.block;

import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
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

public class PieBlock extends FacingBlock {

    private static final VoxelShape SHAPE = Block.createCuboidShape(6, 0, 6, 12, 4, 12);
    public static final IntProperty CUTS = IntProperty.of("cuts", 0, 3);
    private final Item slice;

    public PieBlock(Settings settings, Item slice) {
        super(settings);
        this.slice = slice;
        this.setDefaultState(this.getDefaultState().with(CUTS, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CUTS);

    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (world.isClient) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }
            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return tryEat(world, pos, state, player);
    }

    private ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1, 1);
        PieBlock.dropStack((World) world, pos, Direction.UP, new ItemStack(slice));
        player.incrementStat(Stats.EAT_CAKE_SLICE);
        int i = state.get(CUTS);
        world.emitGameEvent(player, GameEvent.EAT, pos);
        if (i < 3) {
            world.setBlockState(pos, state.with(CUTS, i + 1), Block.NOTIFY_ALL);
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
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.breadblock.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("block.vinery.pie.tooltip1.shift_1"));
            tooltip.add(Text.translatable("block.vinery.pie.tooltip2.shift_2"));
            tooltip.add(Text.translatable("block.vinery.pie.tooltip3.shift_3"));

        } else {
            tooltip.add(Text.translatable(  "item.vinery.ingredient.tooltip"));
        }
    }
}
