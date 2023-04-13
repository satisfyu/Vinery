package satisfyu.vinery.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WinemakerDefaultArmorItem extends ArmorItem implements WineMakerArmorItem{
	public WinemakerDefaultArmorItem(ArmorMaterial material, Type type, Settings settings) {
		super(material, type, settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
		tooltip(tooltip);
	}
}