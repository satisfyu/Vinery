package net.satisfy.vinery.item;

import de.cristelknight.doapi.common.item.CustomArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.satisfy.vinery.registry.ArmorRegistry;

import java.util.List;

@SuppressWarnings("deprecation")
public class WinemakerBootsItem extends CustomArmorItem {
    public WinemakerBootsItem(Holder<ArmorMaterial> material, Properties settings) {
        super(material, Type.BOOTS, settings);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        ArmorRegistry.appendtooltip(list);
    }
}
