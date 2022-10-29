package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VinerySoundEvents;
import daniking.vinery.util.GrapevineType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GrapevinePotBlock extends Block {

    private static final VoxelShape FILLING_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(15.0, 0.0, 0.0,  16.0, 10.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 10.0,  16.0),
            Block.createCuboidShape(1.0, 0.0, 0.0, 15.0, 10.0, 1.0),
            Block.createCuboidShape(1.0, 0.0, 15.0, 15.0, 10.0, 16.0),
            Block.createCuboidShape(0, 0, 0, 16.0, 6, 0.0),
            Block.createCuboidShape(0, 0, 16, 16, 6,16),
            Block.createCuboidShape(16, 0, 0, 16, 6, 16),
            Block.createCuboidShape(0, 0, 0, 0, 5, 16),
            Block.createCuboidShape(1, 1, 1, 15, 1, 15)
    );
    
    

    private static final VoxelShape SMASHING_SHAPE = VoxelShapes.union(
            FILLING_SHAPE,
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0)
    );
    private static final int MAX_STAGE = 6;
    private static final int MAX_STORAGE = 9;
    private static final IntProperty STAGE = IntProperty.of("stage", 0, MAX_STAGE);
    private static final IntProperty STORAGE = IntProperty.of("storage", 0, MAX_STORAGE);
    private static final int DECREMENT_PER_WINE_BOTTLE = 3;
    private static final EnumProperty<GrapevineType> GRAPEVINE_TYPE = EnumProperty.of("type", GrapevineType.class);

    public GrapevinePotBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(STAGE, 0).with(STORAGE, 0));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(STAGE) < 3) {
            return super.getCollisionShape(state, world, pos, context);
        } else {
            return SMASHING_SHAPE;
        }
    }
    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance);
        if (entity instanceof LivingEntity) {
            final int activeStage = state.get(STAGE);
            if (activeStage >= 3) {
                if (activeStage < MAX_STAGE) {
                    world.setBlockState(pos, state.with(STAGE, activeStage + 1), Block.NOTIFY_ALL);
                }
                world.playSound(null, pos, VinerySoundEvents.BLOCK_GRAPEVINE_POT_SQUEEZE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    private boolean canTakeWine(BlockState state, ItemStack stackInHand) {
        final int storage = state.get(STORAGE);
        final int stage = state.get(STAGE);
        if (canTakeWine(storage) && stage == MAX_STAGE) {
            return stackInHand.isOf(ObjectRegistry.WINE_BOTTLE.asItem());
        } else {
            return false;
        }
    }
    private boolean canTakeWine(int storage) {
        return switch (storage) {
            case 3, 6, 9 -> true;
            default -> false;
        };
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final ItemStack stack = player.getStackInHand(hand);
        if (state.get(STAGE) > 3 || state.get(STORAGE) >= MAX_STORAGE) {
            if (stack.getItem() instanceof GrapeItem) {
                return ActionResult.PASS;
            }
        }
        if (stack.getItem() instanceof GrapeItem grape) {
            if (!player.isCreative()) stack.decrement(1);
            final int stage = state.get(STAGE);
            final int storage = state.get(STORAGE);
            boolean playSound = false;
            // Go to stage 1
            if (stage == 0) {
                world.setBlockState(pos, this.getDefaultState().with(STAGE, 1).with(STORAGE, 1).with(GRAPEVINE_TYPE, grape.getType()), Block.NOTIFY_ALL);
                playSound = true;
            }
            // Fill storage
            if (!isFilled(state)) {
                final BlockState newState = world.getBlockState(pos); // Possibly new state
                world.setBlockState(pos, newState.with(STORAGE, storage + 1), Block.NOTIFY_ALL);
                playSound = true;
            }
            // Try to update stage
            final BlockState newState = world.getBlockState(pos); // Updated state
            final int newStage = newState.get(STAGE);
            final int newStorage = newState.get(STORAGE);
            switch (newStorage) {
                case 3, 6, 9 -> {
                    if (newStage < 3) {
                        world.setBlockState(pos, newState.with(STAGE, newStage + 1), Block.NOTIFY_ALL);
                    }
                }
            }
            if (playSound) {
                world.playSound(player, pos, SoundEvents.BLOCK_CORAL_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        } else if (stack.isOf(ObjectRegistry.WINE_BOTTLE.asItem())) {
            if (canTakeWine(state, stack)) {
                final ItemStack output = switch (state.get(GRAPEVINE_TYPE)) {
                    case RED -> new ItemStack(ObjectRegistry.RED_GRAPEJUICE_WINE_BOTTLE);
                    case WHITE -> new ItemStack(ObjectRegistry.WHITE_GRAPEJUICE_WINE_BOTTLE);
                };
                int storage = state.get(STORAGE);
                int newStorage = (storage - DECREMENT_PER_WINE_BOTTLE);
                if (newStorage == 0) {
                    world.setBlockState(pos, world.getBlockState(pos).with(STORAGE,0).with(STAGE, 0), Block.NOTIFY_ALL);
                } else {
                    world.setBlockState(pos, world.getBlockState(pos).with(STORAGE, newStorage), Block.NOTIFY_ALL);
                }
                if (!player.isCreative()) stack.decrement(1);
                if (!player.getInventory().insertStack(output)) {
                    player.dropItem(output, false, false);
                }
                return ActionResult.SUCCESS;
            }


        }
        return ActionResult.PASS;
    }

    private boolean isFilled(BlockState state) {
        return state.get(STORAGE) >= MAX_STORAGE;
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FILLING_SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE, STORAGE, GRAPEVINE_TYPE);
    }
}
