package daniking.vinery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.function.Supplier;

public class StrippableLogBlock extends PillarBlock {

    private final Supplier<Block> strippedBlock;

    public StrippableLogBlock(Settings settings, Supplier<Block> strippedBlock) {
        super(settings);
        this.strippedBlock = Objects.requireNonNull(strippedBlock);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final ItemStack heldStack = player.getStackInHand(hand);
        if(heldStack.isEmpty()) {
            return ActionResult.FAIL;
        }
        final Item held = heldStack.getItem();
        if(!(held instanceof MiningToolItem tool)) {
            return ActionResult.FAIL;
        }
        if(tool.getMiningSpeedMultiplier(heldStack, state) > 1.0F) {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if(!world.isClient) {
                BlockState target = this.strippedBlock.get().getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS));
                world.setBlockState(pos, target);
                heldStack.damage(1, player, consumedPlayer -> consumedPlayer.sendToolBreakStatus(hand));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
}
