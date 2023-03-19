package satisfyu.vinery.block.grape;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.GrapevineType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GrapeBush extends PlantBlock implements Fertilizable {

    public static final IntProperty AGE;
    private static final VoxelShape SHAPE;
    private final int chance;

    public final GrapevineType type;

    public GrapeBush(Settings settings, GrapevineType type) {
        this(settings, type, 5);
    }

    public GrapeBush(Settings settings, GrapevineType type, int chance) {
        super(settings);
        this.chance = chance;
        this.type = type;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public GrapevineType getType() {
        return type;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        boolean bl = i == 3;
        if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (i > 1) {
            int x = world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(getGrapeType().getItem(), x + (bl ? 1 : 0)));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlockState(pos, state.with(AGE, 1), 2);
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }


    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 3 && random.nextInt(chance) == 0 && canGrowPlace(world, pos, state)) {
            BlockState blockState = state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean canGrowPlace(WorldView world, BlockPos blockPos, BlockState blockState) {
        return world.getBaseLightLevel(blockPos, 0) > 9;
    }

    @Override
    public boolean canPlaceAt(BlockState blockState, WorldView world, BlockPos blockPos) {
        return canGrowPlace(world, blockPos, blockState) && this.canPlantOnTop(world.getBlockState(blockPos.down()), world, blockPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOpaqueFullCube(world, pos);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return switch (this.type) {
            case NONE, RED -> new ItemStack(ObjectRegistry.RED_GRAPE_SEEDS);
            case WHITE -> new ItemStack(ObjectRegistry.WHITE_GRAPE_SEEDS);
            case JUNGLE_RED -> new ItemStack(ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS);
            case JUNGLE_WHITE -> new ItemStack(ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS);
            case TAIGA_RED -> new ItemStack(ObjectRegistry.TAIGA_RED_GRAPE_SEEDS);
            case TAIGA_WHITE -> new ItemStack(ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS);
            case SAVANNA_RED -> new ItemStack(ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS);
            case SAVANNA_WHITE -> new ItemStack(ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS);
        };
    }

    public ItemStack getGrapeType() {
        return switch (this.type) {
            case NONE, RED -> new ItemStack(ObjectRegistry.RED_GRAPE);
            case WHITE -> new ItemStack(ObjectRegistry.WHITE_GRAPE);
            case JUNGLE_RED -> new ItemStack(ObjectRegistry.JUNGLE_RED_GRAPE);
            case JUNGLE_WHITE -> new ItemStack(ObjectRegistry.JUNGLE_WHITE_GRAPE);
            case TAIGA_RED -> new ItemStack(ObjectRegistry.TAIGA_RED_GRAPE);
            case TAIGA_WHITE -> new ItemStack(ObjectRegistry.TAIGA_WHITE_GRAPE);
            case SAVANNA_RED -> new ItemStack(ObjectRegistry.SAVANNA_RED_GRAPE);
            case SAVANNA_WHITE -> new ItemStack(ObjectRegistry.SAVANNA_WHITE_GRAPE);
        };
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 3;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(3, state.get(AGE) + 1);
        world.setBlockState(pos, state.with(AGE, i), 2);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    static {
        AGE = Properties.AGE_3;
        SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    }


}

