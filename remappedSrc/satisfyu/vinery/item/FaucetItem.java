package satisfyu.vinery.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FaucetItem extends Item {
	
	public FaucetItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.vinery.faucet.tooltip.main").formatted(Formatting.ITALIC, Formatting.GRAY));

		if (Screen.hasShiftDown()) {
			tooltip.add(Text.translatable("item.vinery.faucet.tooltip.shift"));
		} else {
			tooltip.add(Text.translatable("item.vinery.faucet.tooltip"));
	}


}}

