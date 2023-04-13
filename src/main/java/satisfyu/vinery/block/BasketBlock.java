package satisfyu.vinery.block;

import satisfyu.vinery.registry.ObjectRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BasketBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty STAGE = IntProperty.of("stage", 0, 1);

    private final int MAX_STAGE;

    public BasketBlock(Settings settings, int max_stage) {
        super(settings);
        MAX_STAGE = max_stage;
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(STAGE, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Vinery.LOGGER.info("Used Basket Block!");

        if (world.isClient)
            return ActionResult.SUCCESS;
        final ItemStack stack = player.getStackInHand(hand);
        if (player.isSneaking() && state.get(STAGE) > 0) {
            world.setBlockState(pos, state.with(STAGE, state.get(STAGE) - 1), 3);
            player.giveItemStack(new ItemStack(ObjectRegistry.RED_GRAPE));
        } else if (stack.getItem() == ObjectRegistry.RED_GRAPE.asItem() && state.get(STAGE) < MAX_STAGE) {
            world.setBlockState(pos, state.with(STAGE, state.get(STAGE) + 1), 3);
            if (!player.isCreative())
                stack.decrement(1);
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        VoxelShape shape = VoxelShapes.empty();

        if (state.get(STAGE) == 0)
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.25, 0.25, 0.3125, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.3125, 0.4375, 0.1875, 0.5625, 0.5625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.3125, 0.4375, 0.8125, 0.5625, 0.5625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.5625, 0.4375, 0.8125, 0.5625, 0.5625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0, 0.25, 0.8125, 0.3125, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.75, 0.75, 0.3125, 0.8125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.1875, 0.75, 0.3125, 0.25));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.24925, -0.00075, 0.24925, 0.75075, 0.06175, 0.75075));
        }
        else if (state.get(STAGE) == 1)
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.25, 0.25, 0.3125, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.3125, 0.4375, 0.1875, 0.5625, 0.5625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.3125, 0.4375, 0.8125, 0.5625, 0.5625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.5625, 0.4375, 0.8125, 0.5625, 0.5625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0, 0.25, 0.8125, 0.3125, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.75, 0.75, 0.3125, 0.8125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.1875, 0.75, 0.3125, 0.25));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.24925, -0.00075, 0.24925, 0.75075, 0.25075, 0.75075));
        }
        else
        {
            shape = VoxelShapes.fullCube();
        }

        return shape;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(this.asItem()));

        int amount = MathHelper.clamp(state.get(STAGE), 0, MAX_STAGE);

        if(amount > 0) list.add(new ItemStack(ObjectRegistry.RED_GRAPE, amount));
        return list;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, STAGE);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.basket.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}
