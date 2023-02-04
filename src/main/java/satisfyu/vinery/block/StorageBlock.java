package satisfyu.vinery.block;

import satisfyu.vinery.block.entity.StorageBlockEntity;
import satisfyu.vinery.client.render.block.StorageTypeRenderer;
import satisfyu.vinery.util.VineryUtils;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class StorageBlock extends FacingBlock implements BlockEntityProvider {

    public StorageBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof StorageBlockEntity shelfBlockEntity) {
            Optional<Pair<Float, Float>> optional = VineryUtils.getRelativeHitCoordinatesForBlockFace(hit, state.get(FACING), unAllowedDirections());
            if (optional.isEmpty()) {
                return ActionResult.PASS;
            } else {
                Pair<Float, Float> ff = optional.get();
                int i = getSection(ff.getLeft(), ff.getRight());
                if(i == Integer.MIN_VALUE) return ActionResult.PASS;
                if (!shelfBlockEntity.getInventory().get(i).isEmpty()) {
                    remove(world, pos, player, shelfBlockEntity, i);
                    return ActionResult.success(world.isClient);
                } else {
                    ItemStack stack = player.getStackInHand(hand);
                    if (!stack.isEmpty() && canInsertStack(stack)) {
                        add(world, pos, player, shelfBlockEntity, stack, i);
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



    public void add(World level, BlockPos blockPos, PlayerEntity player, StorageBlockEntity shelfBlockEntity, ItemStack itemStack, int i) {
        if (!level.isClient) {
            SoundEvent soundEvent = SoundEvents.BLOCK_WOOD_PLACE;
            shelfBlockEntity.setStack(i, itemStack.split(1));
            level.playSound(null, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (player.isCreative()) {
                itemStack.increment(1);
            }
            level.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
        }
    }

    public void remove(World level, BlockPos blockPos, PlayerEntity player, StorageBlockEntity shelfBlockEntity, int i) {
        if (!level.isClient) {
            ItemStack itemStack = shelfBlockEntity.removeStack(i);
            SoundEvent soundEvent = SoundEvents.BLOCK_WOOD_PLACE;
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
            if (blockEntity instanceof StorageBlockEntity shelf) {
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

    public abstract int size();

    public abstract Identifier type();

    public abstract Direction[] unAllowedDirections();

    public abstract boolean canInsertStack(ItemStack stack);

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StorageBlockEntity(pos, state, size());
    }
    public abstract int getSection(Float x, Float y);


    private static HashMap<Identifier, StorageTypeRenderer> STORAGE_TYPES = new HashMap<>();


    public static Identifier registerStorageType(Identifier name, StorageTypeRenderer renderer){
         STORAGE_TYPES.put(name, renderer);
         return name;
    }

    public static StorageTypeRenderer getRendererForId(Identifier name){
        return STORAGE_TYPES.get(name);
    }
}