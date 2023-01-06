package daniking.vinery.block;

import daniking.vinery.Vinery;
import daniking.vinery.item.DrinkBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class WineBox extends StorageBlock {

    private static final VoxelShape SHAPE_S = makeShapeS();
    private static final VoxelShape SHAPE_E = makeShapeE();

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;

    public WineBox(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case WEST, EAST -> SHAPE_E;
            default -> SHAPE_S;
        };
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.isSneaking() && stack.isEmpty()) {
            if(!world.isClient()){
                world.setBlockState(pos, state.with(OPEN, !state.get(OPEN)), Block.NOTIFY_ALL);
            }
            return ActionResult.success(world.isClient());
        }
        else if(state.get(OPEN)){
            return super.onUse(state, world, pos, player, hand, hit);
        }
        return ActionResult.PASS;
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
    public StorageType type() {
        return StorageType.WINE_BOX;
    }

    @Override
    public int getSection(Pair<Float, Float> ff) {
        return 0;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(OPEN);
    }

    private static VoxelShape makeShapeS() {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.0625, 0, 0.3125, 0.09375, 0.3125, 0.6875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.90625, 0, 0.3125, 0.9375, 0.3125, 0.6875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0, 0.3125, 0.90625, 0.3125, 0.34375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0.03125, 0.34375, 0.90625, 0.09375, 0.65625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.09375, 0, 0.65625, 0.90625, 0.3125, 0.6875), BooleanBiFunction.OR);

        return shape;
    }

    private static VoxelShape makeShapeE() {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0, 0.0625, 0.6875, 0.3125, 0.09375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0, 0.90625, 0.6875, 0.3125, 0.9375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.65625, 0, 0.09375, 0.6875, 0.3125, 0.90625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.34375, 0.03125, 0.09375, 0.65625, 0.09375, 0.90625), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0, 0.09375, 0.34375, 0.3125, 0.90625), BooleanBiFunction.OR);

        return shape;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("block.vinery.winebox.tooltip.shift_1"));
            tooltip.add(Text.translatable("block.vinery.winebox.tooltip.shift_2"));
            tooltip.add(Text.translatable("block.vinery.winebox.tooltip.shift_3"));

        } else {
            tooltip.add(Text.translatable("block.vinery.breadblock.tooltip.tooltip_shift"));
        }
    }
}
