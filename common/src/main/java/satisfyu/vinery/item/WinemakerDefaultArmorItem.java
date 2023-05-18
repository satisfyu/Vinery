package satisfyu.vinery.item;

import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class WinemakerDefaultArmorItem extends ArmorItem implements WineMakerArmorItem{
	public WinemakerDefaultArmorItem(ArmorMaterial material, EquipmentSlot type, Properties settings) {
		super(material, type, settings);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
		tooltip(tooltip);
	}
}