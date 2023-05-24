package satisfyu.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToastItem extends Item {
	public ToastItem(Properties settings) {
		super(settings);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(new TranslatableComponent("item.vinery.toast_line1.tooltip"));
			tooltip.add(new TranslatableComponent("item.vinery.toast_line2.tooltip"));
			tooltip.add(new TranslatableComponent("item.vinery.oven.tooltip"));
		}
		else {
			tooltip.add(new TranslatableComponent("item.vinery.ingredient.tooltip").withStyle(ChatFormatting.GRAY,
					ChatFormatting.ITALIC));
		}
		super.appendHoverText(stack, world, tooltip, context);
	}
}