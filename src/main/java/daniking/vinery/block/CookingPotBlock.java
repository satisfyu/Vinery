package daniking.vinery.block;

import daniking.vinery.block.entity.CookingPotEntity;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VinerySoundEvents;
import daniking.vinery.util.VineryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CookingPotBlock extends Block implements BlockEntityProvider {
    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.00625, 0.203125, 0.796875, 0.084375, 0.75), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.1875, 0.8125, 0.7421875, 0.265625), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.625, 0.265625), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0, 0.734375, 0.8125, 0.625, 0.8125), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.5859375, 0.2265625, 0.96875, 0.703125, 0.3046875), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.03125, 0.5859375, 0.2265625, 0.1875, 0.703125, 0.3046875), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.5859375, 0.6953125, 0.96875, 0.703125, 0.7734375), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.03125, 0.5859375, 0.6953125, 0.1875, 0.703125, 0.7734375), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.734375, 0.625, 0.265625, 0.8125, 0.7421875, 0.734375), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.734375, 0, 0.265625, 0.8125, 0.625, 0.734375), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0, 0.265625, 0.265625, 0.625, 0.734375), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.890625, 0.5859375, 0.3046875, 0.96875, 0.703125, 0.6953125), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.03125, 0.5859375, 0.3046875, 0.109375, 0.703125, 0.6953125), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.265625, 0.265625, 0.7421875, 0.734375), BooleanBiFunction.OR);
        //shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.734375, 0.8125, 0.7421875, 0.8125), BooleanBiFunction.OR);

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.0625, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.1875, 0.8125, 0.75, 0.25));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.75, 0.8125, 0.75, 0.8125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.625, 0.25));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.75, 0.8125, 0.625, 0.8125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.25, 0.25, 0.625, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0, 0.25, 0.8125, 0.625, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.25, 0.25, 0.75, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0.625, 0.25, 0.8125, 0.75, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.25, 0.25, 0.75, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.6875, 0.1875, 0.6875, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.5625, 0.25, 0.9375, 0.6875, 0.3125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.25, 0.1875, 0.6875, 0.3125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.5625, 0.6875, 0.9375, 0.6875, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.3125, 0.125, 0.6875, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.5625, 0.3125, 0.9375, 0.6875, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.64800625, 0.00614625, 0.59375, 0.71050625, 0.94364625, 0.65625));

        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Stream.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST).toList()) {
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_CHERRIES_INSIDE = BooleanProperty.of("has_cherries");
    private static final Property<Boolean> COOKING = BooleanProperty.of("has_cherries");

    public CookingPotBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(HAS_CHERRIES_INSIDE, false));
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof NamedScreenHandlerFactory factory) {
            player.openHandledScreen(factory);
            return ActionResult.SUCCESS;
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }


    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CookingPotEntity pot) {
                if (world instanceof ServerWorld) {
                    ItemScatterer.spawn(world, pos, pot);
                }
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(COOKING)) {
            double d = (double)pos.getX() + 0.5;
            double e = pos.getY();
            double f = (double)pos.getZ() + 1.0;
            if (random.nextDouble() < 0.3) {
                world.playSound(d, e, f, VinerySoundEvents.BLOCK_COOKING_POT_JUICE_BOILING, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
            Direction direction = state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double h = random.nextDouble() * 0.6 - 0.3;
            double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
            double j = random.nextDouble() * 6.0 / 16.0;
            double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, d + i, e + j, f + k, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.BUBBLE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.BUBBLE_POP, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        }
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_CHERRIES_INSIDE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();

        if (!state.get(HAS_CHERRIES_INSIDE))
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.0625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.1875, 0.8125, 0.75, 0.25));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.75, 0.8125, 0.75, 0.8125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.625, 0.25));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.75, 0.8125, 0.625, 0.8125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.25, 0.25, 0.625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0, 0.25, 0.8125, 0.625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.25, 0.25, 0.75, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0.625, 0.25, 0.8125, 0.75, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.25, 0.25, 0.75, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.6875, 0.1875, 0.6875, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.5625, 0.25, 0.9375, 0.6875, 0.3125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.25, 0.1875, 0.6875, 0.3125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.5625, 0.6875, 0.9375, 0.6875, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.3125, 0.125, 0.6875, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.5625, 0.3125, 0.9375, 0.6875, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.64800625, 0.00614625, 0.59375, 0.71050625, 0.94364625, 0.65625));
        }
        else
        {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.1875, 0.8125, 0.75, 0.25));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.75, 0.8125, 0.75, 0.8125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.625, 0.25));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.75, 0.8125, 0.625, 0.8125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.25, 0.25, 0.625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0, 0.25, 0.8125, 0.625, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.25, 0.25, 0.75, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0.625, 0.25, 0.8125, 0.75, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.625, 0.25, 0.25, 0.75, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.6875, 0.1875, 0.6875, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.5625, 0.25, 0.9375, 0.6875, 0.3125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.25, 0.1875, 0.6875, 0.3125));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0.5625, 0.6875, 0.9375, 0.6875, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.5625, 0.3125, 0.125, 0.6875, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.5625, 0.3125, 0.9375, 0.6875, 0.6875));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.64800625, 0.00614625, 0.59375, 0.71050625, 0.94364625, 0.65625));
        }

        return shape;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof BlockEntityTicker<?>) {
                ((BlockEntityTicker) blockEntity).tick(world1, pos, state1, blockEntity);
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CookingPotEntity(pos, state);
    }
}
