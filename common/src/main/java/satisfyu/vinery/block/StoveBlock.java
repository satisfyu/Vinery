package satisfyu.vinery.block;

import de.cristelknight.doapi.common.block.FacingBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;

import java.util.List;

public class StoveBlock extends FacingBlock {
    public StoveBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.stove.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        tooltip.add(Component.translatable("block.vinery.deprecated.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

    }
}
