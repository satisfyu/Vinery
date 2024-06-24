package net.satisfy.vinery.item;

import de.cristelknight.doapi.common.item.CustomArmorItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class WinemakerChestItem extends CustomArmorItem {
    public WinemakerChestItem(ArmorMaterial material, Properties settings) {
        super(material, Type.CHESTPLATE, settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if(world != null && world.isClientSide()){
            ArmorRegistry.appendtooltip(tooltip);
        }
    }


}