package daniking.vinery.block;

import daniking.vinery.block.entity.CookingPotEntity;
import daniking.vinery.block.entity.ShelfBlockEntity;
import daniking.vinery.util.VineryUtils;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ShelfBlock extends FacingBlock implements BlockEntityProvider {

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.25, 0.5, 1, 0.3125, 1), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.1875, 0.625, 0.1875, 0.25, 0.9375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.1875, 0.625, 0.875, 0.25, 0.9375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.0625, 0.9375, 0.1875, 0.25, 1), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.0625, 0.9375, 0.875, 0.25, 1), BooleanBiFunction.OR);

        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public ShelfBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity var8 = world.getBlockEntity(pos);
        if (var8 instanceof ShelfBlockEntity shelfBlockEntity) {
            Optional<Vec2f> optional = VineryUtils.getRelativeHitCoordinatesForBlockFace(hit, state.get(ShelfBlock.FACING));
            if (optional.isEmpty()) {
                return ActionResult.PASS;
            } else {
                int i = getSection(optional.get().x);
                if (!shelfBlockEntity.getInventory().get(i).isEmpty()) {
                    removeBook(world, pos, player, shelfBlockEntity, i);
                    return ActionResult.success(world.isClient);
                } else {
                    ItemStack stack = player.getStackInHand(hand);
                    if (!stack.isEmpty() && !(stack.getItem() instanceof BlockItem)) {
                        addBook(world, pos, player, shelfBlockEntity, stack, i);
                        return ActionResult.success(world.isClient);
                    } else {
                        return ActionResult.CONSUME;
                    }
                }
            }
        } else {
            return ActionResult.PASS;
        }
    }

    private static void addBook(World level, BlockPos blockPos, PlayerEntity player, ShelfBlockEntity shelfBlockEntity, ItemStack itemStack, int i) {
        if (!level.isClient) {
            SoundEvent soundEvent = SoundEvents.BLOCK_ANVIL_PLACE;
            shelfBlockEntity.setStack(i, itemStack.split(1));
            level.playSound(null, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (player.isCreative()) {
                itemStack.increment(1);
            }
            level.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
        }
    }

    private static void removeBook(World level, BlockPos blockPos, PlayerEntity player, ShelfBlockEntity shelfBlockEntity, int i) {
        if (!level.isClient) {
            ItemStack itemStack = shelfBlockEntity.removeStack(i);
            SoundEvent soundEvent = SoundEvents.BLOCK_ANVIL_BREAK;
            level.playSound(null, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.getInventory().insertStack(itemStack)) {
                player.dropItem(itemStack, false);
            }
            level.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ShelfBlockEntity shelf) {
                if (world instanceof ServerWorld) {
                    ItemScatterer.spawn(world, pos, shelf.getInventory());
                }
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfBlockEntity(pos, state);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.vinery.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    private static int getSection(float f) {
        int nSection;

        if (f < 0.11F) {
            nSection = 0;
        }
        else if(f < 0.22f){
            nSection = 1;
        }
        else if(f < 0.33f){
            nSection = 2;
        }
        else if(f < 0.44f){
            nSection = 3;
        }
        else if(f < 0.55f){
            nSection = 4;
        }
        else if(f < 0.66f){
            nSection = 5;
        }
        else if(f < 0.77f){
            nSection = 6;
        }
        else if(f < 0.88f){
            nSection = 7;
        }
        else nSection = 8;

        return 8 - nSection;
    }

}