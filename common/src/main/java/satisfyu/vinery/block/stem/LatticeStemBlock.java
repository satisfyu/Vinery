package satisfyu.vinery.block.stem;

import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.util.GrapevineType;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LatticeStemBlock extends StemBlock {

    private static final VoxelShape LATTICE_SHAPE_N = Block.box(0, 0,15.0, 16.0,  16.0, 16.0);
    private static final VoxelShape LATTICE_SHAPE_E = Block.box(0, 0,0, 1.0,  16.0, 16.0);
    private static final VoxelShape LATTICE_SHAPE_S = Block.box(0, 0,0, 16.0,  16.0, 1.0);
    private static final VoxelShape LATTICE_SHAPE_W = Block.box(15.0, 0,0, 16.0,  16.0, 16.0);
    public static final DirectionProperty FACING;

    public LatticeStemBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST -> LATTICE_SHAPE_E;
            case SOUTH -> LATTICE_SHAPE_S;
            case WEST -> LATTICE_SHAPE_W;
            default -> LATTICE_SHAPE_N;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState;
        Direction side = ctx.getClickedFace();
        if (side != Direction.DOWN && side != Direction.UP) {
            blockState = this.defaultBlockState().setValue(FACING, ctx.getClickedFace());
        } else {
            blockState = this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
        }


        if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
            return blockState;
        }
        return null;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (hand == InteractionHand.OFF_HAND) {
            return super.use(state, world, pos, player, hand, hit);
        }

        final int age = state.getValue(AGE);
        if (age > 0 && player.getItemInHand(hand).getItem() == Items.SHEARS) {
            if (age > 2) {
                dropGrapes(world, state, pos);
            }
            dropGrapeSeeds(world, state, pos);
            world.setBlock(pos, withAge(state,0, GrapevineType.NONE), 3);
            world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_BREAK, SoundSource.AMBIENT, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }

        final ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof GrapeBushSeedItem seed) {
            if (age == 0) {
                if (!seed.getType().isPaleType()) {
                    world.setBlock(pos, withAge(state,1, seed.getType()), 3);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PLACE, SoundSource.AMBIENT, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!isMature(state) && state.getValue(AGE) > 0) {
            final int i;
            if (world.getRawBrightness(pos, 0) >= 9 && (i = state.getValue(AGE)) < 4) {
                world.setBlock(pos, this.withAge(state,i + 1, state.getValue(GRAPE)), Block.UPDATE_CLIENTS);
            }
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            if (state.getValue(AGE) > 0) {
                dropGrapeSeeds(world, state, pos);
            }
            if (state.getValue(AGE) > 2) {
                dropGrapes(world, state, pos);
            }
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return switch (state.getValue(FACING).getOpposite()) {
            case EAST -> world.getBlockState(pos.east()).isRedstoneConductor(world, pos);
            case SOUTH -> world.getBlockState(pos.south()).isRedstoneConductor(world, pos);
            case WEST -> world.getBlockState(pos.west()).isRedstoneConductor(world, pos);
            default -> world.getBlockState(pos.north()).isRedstoneConductor(world, pos);
        };
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return !isMature(state) && state.getValue(AGE) > 0;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.lattice.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable( "item.vinery.lattice2.tooltip"));
        } else {
            tooltip.add(Component.translatable("item.vinery.faucet.tooltip"));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}
