package satisfyu.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.registry.ItemRegistry;

import java.util.List;

public interface WineMakerArmorItem {
	default void tooltip(List<Component> tooltip) {
		Player player = VineryClient.getClientPlayer();
		if (player == null) { return; }
		ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
		ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
		ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
		ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
		boolean helmetB = helmet != null && helmet.getItem() instanceof WineMakerArmorItem;
		boolean chestplateB = chestplate != null && chestplate.getItem() instanceof WineMakerArmorItem;
		boolean leggingsB = leggings != null && leggings.getItem() instanceof WineMakerArmorItem;
		boolean bootsB = boots != null && boots.getItem() instanceof WineMakerArmorItem;
		tooltip.add(Component.nullToEmpty(""));
		tooltip.add(Component.nullToEmpty(ChatFormatting.AQUA + I18n.get("vinery.tooltip.winemaker_armor")));
		tooltip.add(Component.nullToEmpty(
				(helmetB ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- ["
						+ ItemRegistry.STRAW_HAT.get().getDescription().getString() + "]"));
		tooltip.add(Component.nullToEmpty(
				(chestplateB ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- ["
						+ ItemRegistry.VINEMAKER_APRON.get().getDescription().getString() + "]"));
		tooltip.add(Component.nullToEmpty(
				(leggingsB ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- ["
						+ ItemRegistry.VINEMAKER_LEGGINGS.get().getDescription().getString() + "]"));
		tooltip.add(Component.nullToEmpty(
				(bootsB ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- ["
						+ ItemRegistry.VINEMAKER_BOOTS.get().getDescription().getString() + "]"));
		tooltip.add(Component.nullToEmpty(""));
		tooltip.add(Component.nullToEmpty(ChatFormatting.GRAY + I18n.get("vinery.tooltip.winemaker_armor2")));
		tooltip.add(Component.nullToEmpty(
				((helmetB && chestplateB && leggingsB && bootsB) ? ChatFormatting.DARK_GREEN.toString()
						: ChatFormatting.GRAY.toString()) + I18n.get("vinery.tooltip.winemaker_armor3")));
	}
}
