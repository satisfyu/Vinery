package satisfyu.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FaucetItem extends Item {
	
	public FaucetItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable("block.vinery.deprecated.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

		}
}

