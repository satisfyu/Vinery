package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BasketBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 1);

    private final int MAX_STAGE;

    public BasketBlock(Properties settings, int max_stage) {
        super(settings);
        MAX_STAGE = max_stage;
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(STAGE, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        //Vinery.LOGGER.info("Used Basket Block!");

        if (world.isClientSide)
            return InteractionResult.SUCCESS;
        final ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && state.getValue(STAGE) > 0) {
            world.setBlock(pos, state.setValue(STAGE, state.getValue(STAGE) - 1), 3);
            player.addItem(new ItemStack(ObjectRegistry.RED_GRAPE));
        } else if (stack.getItem() == ObjectRegistry.RED_GRAPE.asItem() && state.getValue(STAGE) < MAX_STAGE) {
            world.setBlock(pos, state.setValue(STAGE, state.getValue(STAGE) + 1), 3);
            if (!player.isCreative())
                stack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        VoxelShape shape = Shapes.empty();

        if (state.getValue(STAGE) == 0)
        {
            shape = Shapes.or(shape, Shapes.box(0.1875, 0, 0.25, 0.25, 0.3125, 0.75));
            shape = Shapes.or(shape, Shapes.box(0.1875, 0.3125, 0.4375, 0.1875, 0.5625, 0.5625));
            shape = Shapes.or(shape, Shapes.box(0.8125, 0.3125, 0.4375, 0.8125, 0.5625, 0.5625));
            shape = Shapes.or(shape, Shapes.box(0.1875, 0.5625, 0.4375, 0.8125, 0.5625, 0.5625));
            shape = Shapes.or(shape, Shapes.box(0.75, 0, 0.25, 0.8125, 0.3125, 0.75));
            shape = Shapes.or(shape, Shapes.box(0.25, 0, 0.75, 0.75, 0.3125, 0.8125));
            shape = Shapes.or(shape, Shapes.box(0.25, 0, 0.1875, 0.75, 0.3125, 0.25));
            shape = Shapes.or(shape, Shapes.box(0.24925, -0.00075, 0.24925, 0.75075, 0.06175, 0.75075));
        }
        else if (state.getValue(STAGE) == 1)
        {
            shape = Shapes.or(shape, Shapes.box(0.1875, 0, 0.25, 0.25, 0.3125, 0.75));
            shape = Shapes.or(shape, Shapes.box(0.1875, 0.3125, 0.4375, 0.1875, 0.5625, 0.5625));
            shape = Shapes.or(shape, Shapes.box(0.8125, 0.3125, 0.4375, 0.8125, 0.5625, 0.5625));
            shape = Shapes.or(shape, Shapes.box(0.1875, 0.5625, 0.4375, 0.8125, 0.5625, 0.5625));
            shape = Shapes.or(shape, Shapes.box(0.75, 0, 0.25, 0.8125, 0.3125, 0.75));
            shape = Shapes.or(shape, Shapes.box(0.25, 0, 0.75, 0.75, 0.3125, 0.8125));
            shape = Shapes.or(shape, Shapes.box(0.25, 0, 0.1875, 0.75, 0.3125, 0.25));
            shape = Shapes.or(shape, Shapes.box(0.24925, -0.00075, 0.24925, 0.75075, 0.25075, 0.75075));
        }
        else
        {
            shape = Shapes.block();
        }

        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(this.asItem()));

        int amount = Mth.clamp(state.getValue(STAGE), 0, MAX_STAGE);

        if(amount > 0) list.add(new ItemStack(ObjectRegistry.RED_GRAPE, amount));
        return list;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, STAGE);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.basket.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
