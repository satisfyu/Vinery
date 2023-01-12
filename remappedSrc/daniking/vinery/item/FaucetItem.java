package daniking.vinery.item;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class FaucetItem extends Item {
	
	public FaucetItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable("item.vinery.faucet.tooltip.main").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

		if (Screen.hasShiftDown()) {
			tooltip.add(Component.translatable("item.vinery.faucet.tooltip.shift"));
		} else {
			tooltip.add(Component.translatable("item.vinery.faucet.tooltip"));
	}


}}

