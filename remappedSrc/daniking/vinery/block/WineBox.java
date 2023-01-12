package daniking.vinery.block;

import daniking.vinery.Vinery;
import daniking.vinery.item.DrinkBlockItem;
import daniking.vinery.registry.StorageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.List;

public class WineBox extends StorageBlock {

    private static final VoxelShape SHAPE_S = makeShapeS();
    private static final VoxelShape SHAPE_E = makeShapeE();

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public WineBox(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case WEST, EAST -> SHAPE_E;
            default -> SHAPE_S;
        };
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && stack.isEmpty()) {
            if(!world.isClientSide()){
                world.setBlock(pos, state.setValue(OPEN, !state.getValue(OPEN)), Block.UPDATE_ALL);
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        else if(state.getValue(OPEN)){
            return super.use(state, world, pos, player, hand, hit);
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.getItem() instanceof DrinkBlockItem;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH};
    }

    @Override
    public int size(){
        return 1;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypes.WINE_BOX;
    }

    @Override
    public int getSection(Float x, Float y) {
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    private static VoxelShape makeShapeS() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.3125, 0.09375, 0.3125, 0.6875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.90625, 0, 0.3125, 0.9375, 0.3125, 0.6875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0, 0.3125, 0.90625, 0.3125, 0.34375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0.03125, 0.34375, 0.90625, 0.09375, 0.65625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0, 0.65625, 0.90625, 0.3125, 0.6875), BooleanOp.OR);

        return shape;
    }

    private static VoxelShape makeShapeE() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.0625, 0.6875, 0.3125, 0.09375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.90625, 0.6875, 0.3125, 0.9375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.65625, 0, 0.09375, 0.6875, 0.3125, 0.90625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.34375, 0.03125, 0.09375, 0.65625, 0.09375, 0.90625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.09375, 0.34375, 0.3125, 0.90625), BooleanOp.OR);

        return shape;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.canbeplaced.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("block.vinery.winebox.tooltip.shift_1"));
            tooltip.add(Component.translatable("block.vinery.winebox.tooltip.shift_2"));
            tooltip.add(Component.translatable("block.vinery.winebox.tooltip.shift_3"));

        } else {
            tooltip.add(Component.translatable("block.vinery.breadblock.tooltip.tooltip_shift"));
        }
    }
}
