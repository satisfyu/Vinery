package daniking.vinery.block;

import daniking.vinery.block.entity.CookingPotEntity;
import daniking.vinery.registry.ObjectRegistry;
import daniking.vinery.registry.VinerySoundEvents;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CookingPotBlock extends Block implements BlockEntityProvider {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_CHERRIES_INSIDE = BooleanProperty.of("has_cherries");

    public CookingPotBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(HAS_CHERRIES_INSIDE, false));
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, Direction.NORTH);
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
//        if (state.get(COOKING)) {
//            double d = (double)pos.getX() + 0.5;
//            double e = pos.getY();
//            double f = (double)pos.getZ() + 1.0;
//            if (random.nextDouble() < 0.3) {
//                world.playSound(d, e, f, VinerySoundEvents.BLOCK_COOKING_POT_JUICE_BOILING, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
//            }
//            Direction direction = state.get(FACING);
//            Direction.Axis axis = direction.getAxis();
//            double h = random.nextDouble() * 0.6 - 0.3;
//            double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
//            double j = random.nextDouble() * 6.0 / 16.0;
//            double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
//            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
//            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
//        }
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_CHERRIES_INSIDE);
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
