package satisfyu.vinery.block;

import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.block.entity.WineBottleBlockEntity;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StackableBlock extends Block{
    private final VoxelShape SHAPE = Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.875, 0.8125);
    public static final IntegerProperty STACK = IntegerProperty.create("stack", 1, 3);

    public StackableBlock(Properties settings) {
        super(settings);
        registerDefaultState(this.defaultBlockState().setValue(STACK, 1));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        final ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == this.asItem()) {
            if (state.getBlock() instanceof StackableBlock && state.getValue(STACK) < 3) {
                world.setBlock(pos, state.setValue(STACK, state.getValue(STACK) + 1), Block.UPDATE_ALL);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        } else if (stack.isEmpty()) {
            if (state.getValue(STACK) > 1) {
                world.setBlock(pos, state.setValue(STACK, state.getValue(STACK) - 1), Block.UPDATE_ALL);
            } else if (state.getValue(STACK) == 1) {
                world.destroyBlock(pos, false);
            }
            player.addItem(this.asItem().getDefaultInstance());
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }


    
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.is(this) || super.skipRendering(state, stateFrom, direction);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STACK);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("item.vinery.juice.tooltip." + this.getDescriptionId()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

}
