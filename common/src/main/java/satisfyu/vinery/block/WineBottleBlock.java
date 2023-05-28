package satisfyu.vinery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.block.entity.WineBottleBlockEntity;
import satisfyu.vinery.util.GeneralUtil;

public class WineBottleBlock extends Block  implements EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Shapes.box(0.125, 0, 0.125, 0.875, 0.875, 0.875);
    public static final IntegerProperty COUNT = IntegerProperty.create("count", 0, 3);
    private final int maxCount;

    public WineBottleBlock(Properties settings, int maxCount) {
        super(settings);
        this.maxCount = maxCount;
        registerDefaultState(this.defaultBlockState().setValue(COUNT, 1).setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        final ItemStack stack = player.getItemInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (stack.getItem() == this.asItem()) {
            if (blockEntity instanceof WineBottleBlockEntity wineEntity && !wineEntity.isFull()) {
                wineEntity.addWine();
                world.playSound(player, pos, SoundEvents.BOTTLE_FILL, SoundSource.AMBIENT, 1.0F, 1.0F);
                world.setBlock(pos, state.setValue(COUNT, state.getValue(COUNT) + 1), Block.UPDATE_ALL);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        } else if (stack.isEmpty()) {
            if (blockEntity instanceof WineBottleBlockEntity wineEntity && wineEntity.getCount() >= 1) {
                wineEntity.removeWine();
                world.playSound(player, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.AMBIENT, 1.0F, 1.0F);
                player.addItem(this.asItem().getDefaultInstance());
                if (wineEntity.getCount() == 0) {
                    world.destroyBlock(pos, false);
                }
                else {
                    world.setBlock(pos, state.setValue(COUNT, state.getValue(COUNT) - 1), Block.UPDATE_ALL);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return GeneralUtil.isSolid(levelReader, blockPos);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, blockPos)) {
            levelAccessor.destroyBlock(blockPos, true);
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(COUNT, 1).setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WineBottleBlockEntity wineEntity) {
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this, wineEntity.getCount()));
                world.updateNeighbourForOutputSignal(pos,this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COUNT, FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WineBottleBlockEntity(pos, state, maxCount);
    }
}
